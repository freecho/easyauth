package com.easyauth.domain.DTO;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String token;
    private String oldPassword;
    private String newPassword;
}
