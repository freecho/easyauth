package com.easyauth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户实体类
 * </p>
 */
@Data
@TableName("employee")
public class Employee {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime lastLogin;
    private LocalDateTime registerTime;
    private Integer status;
}
