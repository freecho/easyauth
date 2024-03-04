package com.easyauth.domain.DTO;

import jdk.dynalink.linker.LinkerServices;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {

    Integer id;

    String username;

    String password;

    String email;

    Integer status;

    List<Integer> roleIds;

}
