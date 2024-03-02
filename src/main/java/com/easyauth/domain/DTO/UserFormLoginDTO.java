package com.easyauth.domain.DTO;

import lombok.Data;

//TODO 引入验证码
@Data
public class UserFormLoginDTO {
    private String username;
    private String password;
}
