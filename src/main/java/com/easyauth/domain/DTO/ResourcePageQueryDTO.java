package com.easyauth.domain.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourcePageQueryDTO {

    Long current;

    Long size = 10L;

    String name;

    String httpMethod;

    String path;

    String description;

}