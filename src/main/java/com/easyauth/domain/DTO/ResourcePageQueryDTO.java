package com.easyauth.domain.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourcePageQueryDTO {

    long current;

    long size;

    String name;

    String httpMethod;

    String path;

    String description;

}
