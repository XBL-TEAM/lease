package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.LabelInfo;
import com.xblteam.lease.web.admin.service.LabelInfoService;
import com.xblteam.lease.web.admin.mapper.LabelInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class LabelInfoServiceImpl extends ServiceImpl<LabelInfoMapper, LabelInfo>
    implements LabelInfoService{

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Override
    public List<LabelInfo> queryByApartmentId(Long apartmentId) {
        List<LabelInfo> labelInfoList = labelInfoMapper.queryByApartmentId(apartmentId);
        System.out.println("labelInfoList = " + labelInfoList);
        return labelInfoList;
    }

    @Override
    public List<LabelInfo> queryByRoomId(Long roomId) {
        List<LabelInfo> labelInfoList = labelInfoMapper.queryByRoomId(roomId);
        System.out.println("labelInfoList = " + labelInfoList);
        return labelInfoList;
    }
}




