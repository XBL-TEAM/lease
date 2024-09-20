package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.FacilityInfo;
import com.xblteam.lease.web.admin.service.FacilityInfoService;
import com.xblteam.lease.web.admin.mapper.FacilityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FacilityInfoServiceImpl extends ServiceImpl<FacilityInfoMapper, FacilityInfo>
    implements FacilityInfoService{

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Override
    public List<FacilityInfo> queryByApartmentId(Long apartmentId) {
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.queryByApartmentId(apartmentId);
        System.out.println("facilityInfoList = " + facilityInfoList);
        return facilityInfoList;
    }

    @Override
    public List<FacilityInfo> queryByRoomId(Long roomId) {
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.queryByRoomId(roomId);
        System.out.println("facilityInfoList = " + facilityInfoList);
        return facilityInfoList;
    }
}




