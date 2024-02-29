package com.easyauth.domain.DTO;

import lombok.Data;

@Data
public class EmployeePageQueryDTO {
    Long current;
    Long size = 10L;
    String username;
    String email;
}
