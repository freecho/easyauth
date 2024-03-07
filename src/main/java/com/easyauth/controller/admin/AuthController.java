package com.easyauth.controller.admin;

import com.easyauth.common.result.Result;
import com.easyauth.domain.DTO.UserFormLoginDTO;
import com.easyauth.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminAuthController")
@Tag(name = "员工认证", description = "员工认证")
@RequestMapping("/admin/auth")
public class AuthController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/formLogin")
    @Operation(summary = "表单登录")
    public Result<String> formLogin(@RequestBody UserFormLoginDTO dto) {
        return Result.success(employeeService.formLogin(dto));
    }
}
