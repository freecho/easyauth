package com.easyauth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("employee_role")
public class EmployeeRole {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer employeeId;
    private Integer roleId;
}
