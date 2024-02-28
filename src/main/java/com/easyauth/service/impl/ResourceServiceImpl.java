package com.easyauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.domain.DTO.ResourcePageQueryDTO;
import com.easyauth.domain.entity.Resource;
import com.easyauth.mapper.ResourceMapper;
import com.easyauth.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Override
    public Page<Resource> conditionSearch(ResourcePageQueryDTO dto) {
        Page<Resource> page = new Page<>(dto.getCurrent(), dto.getSize());
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(dto.getName() != null, Resource::getName, dto.getName())
                .like(dto.getHttpMethod() != null, Resource::getHttpMethod, dto.getHttpMethod())
                .like(dto.getPath() != null, Resource::getPath, dto.getPath())
                .like(dto.getDescription() != null, Resource::getDescription, dto.getDescription());

        return this.page(page, wrapper);
    }
}
