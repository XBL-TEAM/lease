package com.xblteam.lease.web.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xblteam.lease.model.entity.RoomInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xblteam.lease.web.app.vo.room.RoomDetailVo;
import com.xblteam.lease.web.app.vo.room.RoomItemVo;
import com.xblteam.lease.web.app.vo.room.RoomQueryVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {

    IPage<RoomItemVo> pageItem(long current, long size, RoomQueryVo queryVo);

    RoomDetailVo getDetailById(Long id);

    IPage<RoomItemVo> pageItemByApartmentId(long current, long size, Long id);
}
