package com.easyauth;

import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.entity.Employee;
import com.easyauth.mapper.EmployeeMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EasyauthApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Test
    void contextLoads() {
        EmployeePageQueryDTO dto = new EmployeePageQueryDTO();
        dto.setCurrent(1L);
        ArrayList<Long> list = new ArrayList<>();
        list.add(2L);
        list.add(3L);
        dto.setRoleIds(list);
        List<Employee> employees = employeeMapper.pageWithRolesId(dto);

        employees.forEach(System.out::println);
    }

}
