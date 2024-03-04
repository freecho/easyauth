package com.easyauth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 员工实体类
 * </p>
 */
@Data
@TableName("employee")
public class Employee {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime lastLogin;
    private LocalDateTime registerTime;
    private Integer status;

    @TableField(exist = false)
    private List<Integer> rolesId;
}
