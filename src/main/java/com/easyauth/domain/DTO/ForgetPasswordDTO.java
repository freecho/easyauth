package com.easyauth.domain.DTO;

import lombok.Data;

@Data
public class ForgetPasswordDTO {
    private String username;
    private String email;
    private String code;
    private String newPassword;
}
