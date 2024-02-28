package com.easyauth.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("resource")
public class Resource {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String httpMethod;
    private String path;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
