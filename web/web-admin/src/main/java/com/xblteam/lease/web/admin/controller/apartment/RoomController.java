package com.xblteam.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.common.result.Result;
import com.xblteam.lease.model.entity.RoomInfo;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.service.RoomInfoService;
import com.xblteam.lease.web.admin.vo.room.RoomDetailVo;
import com.xblteam.lease.web.admin.vo.room.RoomItemVo;
import com.xblteam.lease.web.admin.vo.room.RoomQueryVo;
import com.xblteam.lease.web.admin.vo.room.RoomSubmitVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "房间信息管理")
@RestController
@RequestMapping("/admin/room")
public class RoomController {

    @Autowired
    private RoomInfoService roomInfoService;

    @Operation(summary = "保存或更新房间信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody RoomSubmitVo roomSubmitVo) {
        roomInfoService.saveOrUpdateRoomSubmit(roomSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(
            @RequestParam long current,
            @RequestParam long size,
            RoomQueryVo roomQueryVo) {
        System.out.println("roomQueryVo = " + roomQueryVo);
        IPage<RoomItemVo> iPage = roomInfoService.pageItem(new Page<>(current, size), roomQueryVo);
        return Result.ok(iPage);
    }

    @Operation(summary = "根据id获取房间详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        RoomDetailVo roomDetailVo = roomInfoService.getDetailById(id);
        return Result.ok(roomDetailVo);
    }

    @Operation(summary = "根据id删除房间信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        roomInfoService.removeByRoomId(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改房间发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(Long id, ReleaseStatus status) {
        roomInfoService.update(new LambdaUpdateWrapper<RoomInfo>()
                .eq(RoomInfo::getId, id)
                .set(RoomInfo::getIsRelease, status));
        return Result.ok();
    }

    @GetMapping("listBasicByApartmentId")
    @Operation(summary = "根据公寓id查询房间列表")
    public Result<List<RoomInfo>> listBasicByApartmentId(Long id) {
        List<RoomInfo> roomInfoList = roomInfoService.list(new LambdaQueryWrapper<RoomInfo>()
                .eq(RoomInfo::getApartmentId, id)
                .eq(RoomInfo::getIsRelease, ReleaseStatus.RELEASED));
        return Result.ok(roomInfoList);
    }

}


















