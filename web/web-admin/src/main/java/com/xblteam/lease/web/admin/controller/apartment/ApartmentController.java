package com.xblteam.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.common.result.Result;
import com.xblteam.lease.common.result.ResultCodeEnum;
import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.service.ApartmentInfoService;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "公寓信息管理")
@RestController
@RequestMapping("/admin/apartment")
public class ApartmentController {

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Operation(summary = "保存或更新公寓信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ApartmentSubmitVo apartmentSubmitVo) {
        apartmentInfoService.saveOrUpdateApartmentSubmit(apartmentSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询公寓列表")
    @GetMapping("pageItem")
    public Result<IPage<ApartmentItemVo>> pageItem(
            @RequestParam long current,
            @RequestParam long size,
            ApartmentQueryVo apartmentQueryVo) {
        IPage<ApartmentItemVo> iPage = apartmentInfoService.pageItem(new Page<>(current, size), apartmentQueryVo);
        return Result.ok(iPage);
    }

    @Operation(summary = "根据ID获取公寓详细信息")
    @GetMapping("getDetailById")
    public Result<ApartmentDetailVo> getDetailById(@RequestParam Long id) {
        ApartmentDetailVo apartmentDetailVo = apartmentInfoService.getDetailById(id);
        return Result.ok(apartmentDetailVo);
    }

    @Operation(summary = "根据id删除公寓信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        boolean isSuccess = apartmentInfoService.removeInfoById(id);
        if (!isSuccess) {
            return Result.build(null, ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }
        return Result.ok();
    }

    @Operation(summary = "根据id修改公寓发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(
            @RequestParam Long id,
            @RequestParam ReleaseStatus status) {
        apartmentInfoService.update(new LambdaUpdateWrapper<ApartmentInfo>()
                .eq(ApartmentInfo::getId, id)
                .set(ApartmentInfo::getIsRelease,status));
        return Result.ok();
    }

    @Operation(summary = "根据区县id查询公寓信息列表")
    @GetMapping("listInfoByDistrictId")
    public Result<List<ApartmentInfo>> listInfoByDistrictId(@RequestParam Long id) {
        List<ApartmentInfo> list = apartmentInfoService.list(new LambdaQueryWrapper<ApartmentInfo>()
                .eq(ApartmentInfo::getDistrictId, id));
        return Result.ok(list);
    }
}














