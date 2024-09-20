package com.xblteam.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.FacilityInfo;
import com.xblteam.lease.model.entity.LabelInfo;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.app.mapper.*;
import com.xblteam.lease.web.app.service.ApartmentInfoService;
import com.xblteam.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.xblteam.lease.web.app.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo> implements ApartmentInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Override
    public ApartmentItemVo selectApartmentItemVoById(Long id) {

        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        return new ApartmentItemVo(
                apartmentInfo.getId(),
                apartmentInfo.getName(),
                apartmentInfo.getIntroduction(),
                apartmentInfo.getDistrictId(),
                apartmentInfo.getDistrictName(),
                apartmentInfo.getCityId(),
                apartmentInfo.getCityName(),
                apartmentInfo.getProvinceId(),
                apartmentInfo.getProvinceName(),
                apartmentInfo.getAddressDetail(),
                apartmentInfo.getLatitude(),
                apartmentInfo.getLongitude(),
                apartmentInfo.getPhone(),
                apartmentInfo.getIsRelease(),
                labelInfoList,
                graphVoList,
                minRent);
    }

    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {

        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByApartmentId(id);
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        return new ApartmentDetailVo(
                apartmentInfo.getId(),
                apartmentInfo.getName(),
                apartmentInfo.getIntroduction(),
                apartmentInfo.getDistrictId(),
                apartmentInfo.getDistrictName(),
                apartmentInfo.getCityId(),
                apartmentInfo.getCityName(),
                apartmentInfo.getProvinceId(),
                apartmentInfo.getProvinceName(),
                apartmentInfo.getAddressDetail(),
                apartmentInfo.getLatitude(),
                apartmentInfo.getLongitude(),
                apartmentInfo.getPhone(),
                apartmentInfo.getIsRelease(),
                graphVoList,
                labelInfoList,
                facilityInfoList,
                minRent);
    }

}




