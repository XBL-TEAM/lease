package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.GraphInfo;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.admin.service.GraphInfoService;
import com.xblteam.lease.web.admin.mapper.GraphInfoMapper;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class GraphInfoServiceImpl extends ServiceImpl<GraphInfoMapper, GraphInfo>
    implements GraphInfoService{

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Override
    public List<GraphVo> queryByApartmentId(Long apartmentId, ItemType itemType) {
        List<GraphVo> graphVoList = graphInfoMapper.queryByApartmentId(apartmentId,itemType);
        System.out.println("graphVoList = " + graphVoList);
        return graphVoList;
    }

    @Override
    public List<GraphVo> queryByRoomId(Long roomId, ItemType itemType) {
        List<GraphVo> graphVoList = graphInfoMapper.queryByRoomId(roomId,itemType);
        System.out.println("graphVoList = " + graphVoList);
        return graphVoList;
    }
}




