package com.easyauth.domain.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourcePageQueryDTO {

    Integer current;

    Integer size = 10;

    String name;

    String httpMethod;

    String path;

    String description;

}
