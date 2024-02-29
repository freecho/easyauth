package com.easyauth.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    Long id;

    String username;

    String password;

    String email;

    Long status;

    List<Long> roleIds;

}
