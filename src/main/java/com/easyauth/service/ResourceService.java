package com.easyauth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easyauth.domain.DTO.ResourcePageQueryDTO;
import com.easyauth.domain.entity.Resource;

public interface ResourceService extends IService<Resource> {
    Page<Resource> conditionSearch(ResourcePageQueryDTO dto);
}
