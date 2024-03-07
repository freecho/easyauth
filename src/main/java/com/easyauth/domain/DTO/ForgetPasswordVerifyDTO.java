package com.easyauth.domain.DTO;

import lombok.Data;

@Data
public class ForgetPasswordVerifyDTO {
    private String username;
    private String email;
}
