package com.xblteam.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xblteam.lease.model.entity.RoomInfo;
import com.xblteam.lease.web.admin.vo.room.RoomDetailVo;
import com.xblteam.lease.web.admin.vo.room.RoomItemVo;
import com.xblteam.lease.web.admin.vo.room.RoomQueryVo;
import com.xblteam.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    void saveOrUpdateRoomSubmit(RoomSubmitVo roomSubmitVo);

    IPage<RoomItemVo> pageItem(IPage<RoomItemVo> page, RoomQueryVo roomQueryVo);

    RoomDetailVo getDetailById(Long id);

    void removeByRoomId(Long id);
}
