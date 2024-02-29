package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.DTO.ResourcePageQueryDTO;
import com.easyauth.domain.entity.Resource;
import com.easyauth.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/resource")
@Tag(name = "资源管理")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Operation(summary = "添加资源")
    @PostMapping
    public Result<String> add(@RequestBody Resource resource) {
        resourceService.save(resource);
        return Result.success();
    }

    @Operation(summary = "修改资源")
    @PutMapping
    public Result<String> update(@RequestBody Resource resource) {
        resourceService.updateById(resource);
        return Result.success();
    }

    @Operation(summary = "删除资源")
    @DeleteMapping
    public Result<String> deleteById(long id) {
        resourceService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "分页条件查询",description = "current必须提供，size默认为10")
    @GetMapping("/conditionSearch")
    public Result<Page<Resource>> conditionSearch(ResourcePageQueryDTO dto) {
        return Result.success(resourceService.conditionSearch(dto));
    }

    @Operation(summary = "资源列表")
    @GetMapping("/list")
    public Result<Page<Resource>> getList(int current, int size) {
        Page<Resource> page = new Page<>(current, size);
        Page<Resource> pageResult = resourceService.page(page);
        return Result.success(pageResult);
    }

}
