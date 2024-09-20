package com.xblteam.lease.web.admin.service;

import com.xblteam.lease.model.entity.GraphInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.admin.mapper.GraphInfoMapper;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface GraphInfoService extends IService<GraphInfo> {

    List<GraphVo> queryByApartmentId(Long apartmentId, ItemType itemType);

    List<GraphVo> queryByRoomId(Long roomId, ItemType itemType);

}
