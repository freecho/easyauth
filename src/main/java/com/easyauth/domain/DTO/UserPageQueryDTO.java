package com.easyauth.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserPageQueryDTO {
    Long current;
    Long size = 10L;
    String username;
    String email;
    List<Long> roleIds;
}
