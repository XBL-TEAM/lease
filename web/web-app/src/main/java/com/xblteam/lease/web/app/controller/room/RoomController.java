package com.xblteam.lease.web.app.controller.room;


import com.xblteam.lease.common.result.Result;
import com.xblteam.lease.web.app.service.RoomInfoService;
import com.xblteam.lease.web.app.vo.room.RoomDetailVo;
import com.xblteam.lease.web.app.vo.room.RoomItemVo;
import com.xblteam.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "房间信息")
@RestController
@RequestMapping("/app/room")
public class RoomController {

    @Autowired
    private RoomInfoService roomInfoService;

    @Operation(summary = "分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(
            @RequestParam long current,
            @RequestParam long size,
            RoomQueryVo queryVo) {
        IPage<RoomItemVo> iPage = roomInfoService.pageItem(current,size,queryVo);
        return Result.ok(iPage);
    }

    @Operation(summary = "根据id获取房间的详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        RoomDetailVo roomDetailVo = roomInfoService.getDetailById(id);
        return Result.ok(roomDetailVo);
    }

    @Operation(summary = "根据公寓id分页查询房间列表")
    @GetMapping("pageItemByApartmentId")
    public Result<IPage<RoomItemVo>> pageItemByApartmentId(
            @RequestParam long current,
            @RequestParam long size,
            @RequestParam Long id) {
        IPage<RoomItemVo> iPage = roomInfoService.pageItemByApartmentId(current,size,id);
        return Result.ok(iPage);
    }
}
