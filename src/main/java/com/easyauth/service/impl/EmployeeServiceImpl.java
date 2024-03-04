package com.easyauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.exception.InvalidDataException;
import com.easyauth.common.utils.PageUtil;
import com.easyauth.domain.DTO.EmployeeDTO;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.VO.EmployeeVO;
import com.easyauth.domain.entity.Employee;
import com.easyauth.domain.entity.EmployeeRole;
import com.easyauth.domain.entity.Role;
import com.easyauth.mapper.EmployeeMapper;
import com.easyauth.service.EmployeeRoleService;
import com.easyauth.service.EmployeeService;
import com.easyauth.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRoleService employeeRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public void add(EmployeeDTO employeeDTO) {
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

        Employee employee = new Employee();
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setEmail(employeeDTO.getEmail());
        this.save(employee);

        employeeDTO.getRoleIds().forEach(roleId -> {
            // 验证角色是否存在
            Role role = roleService.getById(roleId);
            if (role == null || role.getStatus() != 1) {
                throw new InvalidDataException(MessageConstant.VALIDATE_FAILED);
            }

            // 保存员工角色
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(roleId);
            employeeRoleService.save(employeeRole);
        });

    }

    @Override
    @Transactional
    public void edit(EmployeeDTO employeeDTO) {
        //  更新员工信息
        Employee employee = this.getById(employeeDTO.getId());

        if (employeeDTO.getUsername() != null) {
            employee.setUsername(employeeDTO.getUsername());
        }
        if (employeeDTO.getPassword() != null) {
            employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        }
        if (employeeDTO.getStatus() != null) {
            employee.setStatus(employeeDTO.getStatus());
        }
        if (employeeDTO.getEmail() != null) {
            employee.setEmail(employeeDTO.getEmail());
        }

        this.updateById(employee);

        //  处理角色数据

        // 1. 删除初始的角色绑定数据
        LambdaQueryWrapper<EmployeeRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeRole::getEmployeeId, employeeDTO.getId());
        employeeRoleService.remove(wrapper);
        wrapper.clear();

        // 2. 验证现在需要绑定的角色是否存在
        employeeDTO.getRoleIds().forEach(roleId -> {
            // 验证目标角色是否存在
            Role role = roleService.getById(roleId);
            if (role == null || role.getStatus() != 1) {
                throw new InvalidDataException(MessageConstant.VALIDATE_FAILED);
            }
            // 绑定角色
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employeeDTO.getId());
            employeeRole.setRoleId(roleId);
            employeeRoleService.save(employeeRole);
        });
    }

    /**
     * 员工动态条件查询(不包含角色条件)
     *
     * @param dto
     * @return
     */
    @Override
    public Page<EmployeeVO> conditionSearchWithOutRoleId(EmployeePageQueryDTO dto) {
        Page<Employee> page = new Page<>(dto.getCurrent(), dto.getSize());
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(dto.getUsername() != null, Employee::getUsername, dto.getUsername());
        wrapper.like(dto.getEmail() != null, Employee::getEmail, dto.getEmail());

        Page<Employee> pageResult = this.page(page, wrapper);

        List<EmployeeRole> roleList = employeeRoleService.list();

        //  添加角色数据
        pageResult.getRecords().forEach(employee -> {
            List<Integer> roles = new ArrayList<>();

            roleList.forEach(role -> {
                if (role.getEmployeeId().equals(employee.getId())) {
                    roles.add(role.getRoleId());
                }
            });

            employee.setRolesId(roles);
        });

        return PageUtil.convert(pageResult, EmployeeVO.class);
    }

    public Page<EmployeeVO> conditionSearchWithRoleId(EmployeePageQueryDTO queryDTO) {
        List<Employee> employeeList = employeeMapper.pageWithRolesId(queryDTO);
        int start = Math.min((int) ((queryDTO.getCurrent() - 1) * queryDTO.getSize()), employeeList.size());
        int end = Math.min((int) (start + queryDTO.getSize()), employeeList.size());
        employeeList.subList(start, end);

        Page<Employee> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        page.setRecords(employeeList);
        page.setTotal(employeeList.size());

        return PageUtil.convert(page, EmployeeVO.class);
    }

    @Override
    public Page<EmployeeVO> getList(Integer current, Integer size) {
        Page<Employee> page = new Page<>(current, size);
        Page<Employee> pageResult = this.page(page);

        List<EmployeeRole> roleList = employeeRoleService.list();

        pageResult.getRecords().forEach(employee -> {
            List<Integer> roles = new ArrayList<>();

            roleList.forEach(role -> {
                if (role.getEmployeeId().equals(employee.getId())) {
                    roles.add(role.getRoleId());
                }
            });

            employee.setRolesId(roles);
        });

        return PageUtil.convert(pageResult, EmployeeVO.class);
    }
}
