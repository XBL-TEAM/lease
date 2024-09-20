package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.admin.mapper.ApartmentInfoMapper;
import com.xblteam.lease.web.admin.mapper.RoomInfoMapper;
import com.xblteam.lease.web.admin.service.*;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.xblteam.lease.web.admin.vo.fee.FeeValueVo;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;                    //公寓信息
    @Autowired
    private ApartmentLabelService apartmentLabelService;                //公寓标签
    @Autowired
    private ApartmentFacilityService apartmentFacilityService;          //公寓配套
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;          //公寓杂费值
    @Autowired
    private RoomInfoMapper roomInfoMapper;                            //公寓房间
    @Autowired
    private GraphInfoService graphInfoService;                          //图片
    @Autowired
    private LabelInfoService labelInfoService;                          //标签
    @Autowired
    private FacilityInfoService facilityInfoService;                    //配套
    @Autowired
    private FeeValueService feeValueService;                            //杂费

    @Transactional
    @Override
    public void saveOrUpdateApartmentSubmit(ApartmentSubmitVo apartmentSubmitVo) {

        super.saveOrUpdate(apartmentSubmitVo);
        Long apartmentId = apartmentSubmitVo.getId();
        boolean isUpdate = apartmentId != null;                         //公寓id

        if (isUpdate) {     //先全部删除
            apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>()
                    .eq(ApartmentLabel::getApartmentId, apartmentId));
            apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>()
                    .eq(ApartmentFacility::getApartmentId, apartmentId));
            apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>()
                    .eq(ApartmentFeeValue::getApartmentId, apartmentId));
            graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>()
                    .eq(GraphInfo::getItemType, ItemType.APARTMENT)
                    .eq(GraphInfo::getItemId, apartmentId));
        }

        //再全部插入

        List<Long> labelIds = apartmentSubmitVo.getLabelIds();                      //标签id
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();        //配套id
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();                //杂费值id
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();             //图片列表

        if (labelIds != null) {
            List<ApartmentLabel> apartmentLabelList = new ArrayList<>();
            for (Long labelId : labelIds)
                apartmentLabelList.add(new ApartmentLabel(apartmentId, labelId));
            apartmentLabelService.saveBatch(apartmentLabelList);
        }

        if (facilityInfoIds != null) {
            List<ApartmentFacility> apartmentFacilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds)
                apartmentFacilityList.add(new ApartmentFacility(apartmentId, facilityInfoId));
            apartmentFacilityService.saveBatch(apartmentFacilityList);
        }

        if (feeValueIds != null) {
            List<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIds)
                apartmentFeeValueList.add(new ApartmentFeeValue(apartmentId, feeValueId));
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }

        if (graphVoList != null) {
            List<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList)
                graphInfoList.add(graphVo.toGraphInfo(apartmentId, ItemType.APARTMENT));
            graphInfoService.saveBatch(graphInfoList);
        }

    }

    @Override
    public IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo apartmentQueryVo) {
        IPage<ApartmentItemVo> iPage = apartmentInfoMapper.pageItem(page, apartmentQueryVo);
        System.out.println("iPage = " + iPage);
        return iPage;
    }

    @Override
    public ApartmentDetailVo getDetailById(Long apartmentId) {

        //查询相关信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(apartmentId);
        if (apartmentInfo == null){
            return new ApartmentDetailVo();
        }
        List<GraphVo> graphVoList = graphInfoService.queryByApartmentId(apartmentId, ItemType.APARTMENT);
        List<LabelInfo> labelInfoList = labelInfoService.queryByApartmentId(apartmentId);
        List<FacilityInfo> facilityInfoList = facilityInfoService.queryByApartmentId(apartmentId);
        List<FeeValueVo> feeValueVoList = feeValueService.queryByApartmentId(apartmentId);

        //进行封装
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
                feeValueVoList);
    }

    @Transactional
    @Override
    public boolean removeInfoById(Long apartmentId) {
        long count = roomInfoMapper.selectCount(new LambdaQueryWrapper<RoomInfo>()
                .eq(RoomInfo::getApartmentId, apartmentId));
        boolean hasRoom = count > 0;
        //如果公寓下面有房间，终止删除操作
        if (hasRoom) {
            return false;
        }
        apartmentInfoMapper.deleteById(apartmentId);
        apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>()
                .eq(ApartmentLabel::getApartmentId, apartmentId));
        apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>()
                .eq(ApartmentFacility::getApartmentId, apartmentId));
        apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>()
                .eq(ApartmentFeeValue::getApartmentId, apartmentId));
        graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>()
                .eq(GraphInfo::getItemType, ItemType.APARTMENT)
                .eq(GraphInfo::getItemId, apartmentId));
        return true;
    }
}




