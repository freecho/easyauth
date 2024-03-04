package com.easyauth.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    Integer id;

    String username;

    String password;

    String email;

    Integer status;

    List<Integer> roleIds;

}
