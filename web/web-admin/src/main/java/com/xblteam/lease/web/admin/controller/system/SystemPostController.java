package com.xblteam.lease.web.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.common.result.Result;
import com.xblteam.lease.model.entity.SystemPost;
import com.xblteam.lease.model.enums.BaseStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xblteam.lease.web.admin.service.SystemPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "后台用户岗位管理")
@RequestMapping("/admin/system/post")
public class SystemPostController {

    @Autowired
    private SystemPostService systemPostService;

    @Operation(summary = "分页获取岗位信息")
    @GetMapping("page")
    private Result<IPage<SystemPost>> page(
            @RequestParam long current,
            @RequestParam long size) {
        Page<SystemPost> page = systemPostService.page(new Page<>(current, size));
        return Result.ok(page);
    }

    @Operation(summary = "保存或更新岗位信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(
            @RequestBody SystemPost systemPost) {
        systemPostService.saveOrUpdate(systemPost);
        return Result.ok();
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据id删除岗位")
    public Result removeById(
            @RequestParam Long id) {
        systemPostService.removeById(id);
        return Result.ok();
    }

    @GetMapping("getById")
    @Operation(summary = "根据id获取岗位信息")
    public Result<SystemPost> getById(
            @RequestParam Long id) {
        SystemPost systemPost = systemPostService.getById(id);
        return Result.ok(systemPost);
    }

    @Operation(summary = "获取全部岗位列表")
    @GetMapping("list")
    public Result<List<SystemPost>> list() {
        List<SystemPost> list = systemPostService.list();
        return Result.ok(list);
    }

    @Operation(summary = "根据岗位id修改状态")
    @PostMapping("updateStatusByPostId")
    public Result updateStatusByPostId(
            @RequestParam Long id,
            @RequestParam BaseStatus status) {
        systemPostService.update(new LambdaUpdateWrapper<SystemPost>()
                .eq(SystemPost::getId, id)
                .set(SystemPost::getStatus, status));
        return Result.ok();
    }
}
