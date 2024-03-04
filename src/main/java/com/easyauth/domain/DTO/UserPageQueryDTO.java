package com.easyauth.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserPageQueryDTO {
    Integer current;
    Integer size = 10;
    String username;
    String email;
    List<Integer> roleIds;
}
