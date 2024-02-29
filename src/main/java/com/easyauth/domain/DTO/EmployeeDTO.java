package com.easyauth.domain.DTO;

import jdk.dynalink.linker.LinkerServices;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {

    Long id;

    String username;

    String password;

    String email;

    Long status;

    List<Long> roleIds;

}
