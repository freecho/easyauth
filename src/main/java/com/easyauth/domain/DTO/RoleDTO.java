package com.easyauth.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer status;
    private List<Integer> resourceIds;
}
