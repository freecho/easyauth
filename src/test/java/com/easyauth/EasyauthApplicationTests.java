package com.easyauth;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.controller.admin.ResourceController;
import com.easyauth.controller.admin.RoleController;
import com.easyauth.controller.admin.RolePermissionController;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.entity.*;
import com.easyauth.mapper.EmployeeMapper;
import com.easyauth.security.JwtAuthenticationToken;
import com.easyauth.service.RedisService;
import com.easyauth.service.ResourceService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

import javax.management.relation.RoleList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootTest
class EasyauthApplicationTests {
    //TODO 各表数据的修改/删除/更新 同步到 redis 可使用测试里的方法

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoleController roleController;

    @Autowired
    private RolePermissionController rolePermissionController;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void mytest3() {
        Map map = new HashMap();
        map.put("id", 88);
        map.put("identity", "admin");
        String token = jwtUtil.createJWT(map);
        System.out.println(token);
    }

    @Test
    void mytest2() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername("admin123");
        userDetail.setId(88);
        userDetail.setEmail("123@qq.com");
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        userDetail.setRolesId(list);

        redisService.set("admin" + ":" + userDetail.getId(), userDetail);

    }

    // 添加 角色id:资源id 数据到redis
    @Test
    void myTest() {
        Result<Page<Resource>> list = resourceController.getList(1, 100);
        List<Resource> records = list.getData().getRecords();
        List<Integer> resourceList = records.stream().map(Resource::getId).collect(Collectors.toList());

        Result<Page<Role>> rolePage = roleController.getList(1, 100);
        List<Integer> roleList = rolePage.getData().getRecords().stream().map(Role::getId).collect(Collectors.toList());


        List<RolePermission> permsList = rolePermissionController.getList(1, 100).getData().getRecords();


        for (Integer resourceId : resourceList) {
            for (Integer roleId : roleList) {
                boolean flag = false;
                for (RolePermission rolePermission : permsList) {
                    if (rolePermission.getRoleId().equals(roleId) && rolePermission.getResourceId().equals(resourceId)) {
                        flag = true;
                        break;

                    }
                }
                redisService.set(roleId + ":" + resourceId, flag);
            }
        }

    }

    // 添加资源数据到redis
    @Test
    void myTest0() {
        Result<Page<Resource>> list = resourceController.getList(1, 100);
        List<Resource> records = list.getData().getRecords();
        List<Integer> resourceList = records.stream().map(Resource::getId).collect(Collectors.toList());

        records.forEach(
                resource -> {
                    redisService.set(resource.getHttpMethod() + ":" + resource.getPath(), resource.getId());
                }
        );
    }
}
