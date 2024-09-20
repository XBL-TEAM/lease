package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.common.constant.RedisConstant;
import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.admin.mapper.ApartmentInfoMapper;
import com.xblteam.lease.web.admin.mapper.RoomInfoMapper;
import com.xblteam.lease.web.admin.service.*;
import com.xblteam.lease.web.admin.vo.attr.AttrValueVo;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import com.xblteam.lease.web.admin.vo.room.RoomDetailVo;
import com.xblteam.lease.web.admin.vo.room.RoomItemVo;
import com.xblteam.lease.web.admin.vo.room.RoomQueryVo;
import com.xblteam.lease.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private RoomAttrValueService roomAttrValueService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;
    @Autowired
    private RoomLeaseTermService roomLeaseTermService;
    @Autowired
    private AttrValueService attrValueService;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private FacilityInfoService facilityInfoService;
    @Autowired
    private LabelInfoService labelInfoService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private LeaseTermService leaseTermService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override
    public void saveOrUpdateRoomSubmit(RoomSubmitVo roomSubmitVo) {

        super.saveOrUpdate(roomSubmitVo);
        Long roomId = roomSubmitVo.getId();
        boolean isUpdate = roomId != null;                         //房间id

        if (isUpdate) {

            //先全部删除
            deleteInfo(roomId);

            //删除redis缓存
            String key = RedisConstant.APP_ROOM_PREFIX + roomSubmitVo.getId();
            redisTemplate.delete(key);
        }

        //再全部插入

        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();              //图片列表
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();               //属性值列表
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();         //配套id列表
        List<Long> labelIds = roomSubmitVo.getLabelInfoIds();                   //标签id列表
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();           //支付方式列表
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();               //可选租期列表


        if (graphVoList != null) {
            List<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList)
                graphInfoList.add(graphVo.toGraphInfo(roomId, ItemType.ROOM));
            graphInfoService.saveBatch(graphInfoList);
        }

        if (attrValueIds != null) {
            List<RoomAttrValue> attrValueList = new ArrayList<>();
            for (Long attrValueId : attrValueIds)
                attrValueList.add(new RoomAttrValue(roomId, attrValueId));
            roomAttrValueService.saveBatch(attrValueList);
        }

        if (facilityInfoIds != null) {
            List<RoomFacility> facilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds)
                facilityList.add(new RoomFacility(roomId, facilityInfoId));
            roomFacilityService.saveBatch(facilityList);
        }

        if (labelIds != null) {
            List<RoomLabel> labelList = new ArrayList<>();
            for (Long labelId : labelIds)
                labelList.add(new RoomLabel(roomId, labelId));
            roomLabelService.saveBatch(labelList);
        }

        if (paymentTypeIds != null) {
            List<RoomPaymentType> paymentTypeList = new ArrayList<>();
            for (Long paymentTypeId : paymentTypeIds)
                paymentTypeList.add(new RoomPaymentType(roomId, paymentTypeId));
            roomPaymentTypeService.saveBatch(paymentTypeList);
        }

        if (leaseTermIds != null) {
            List<RoomLeaseTerm> roomLeaseTermList = new ArrayList<>();
            for (Long leaseTermId : leaseTermIds)
                roomLeaseTermList.add(new RoomLeaseTerm(roomId, leaseTermId));
            roomLeaseTermService.saveBatch(roomLeaseTermList);
        }
    }

    @Override
    public IPage<RoomItemVo> pageItem(IPage<RoomItemVo> page, RoomQueryVo roomQueryVo) {
        IPage<RoomItemVo> iPage = roomInfoMapper.pageItem(page, roomQueryVo);
        System.out.println("iPage = " + iPage);
        return iPage;
    }

    @Override
    public RoomDetailVo getDetailById(Long roomId) {

        //查询相关信息
        RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
        if (roomInfo == null){
            return new RoomDetailVo();
        }
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());
        List<GraphVo> graphVoList = graphInfoService.queryByRoomId(roomId, ItemType.ROOM);
        List<AttrValueVo> attrValueVoList = attrValueService.queryByRoomId(roomId);
        List<FacilityInfo> facilityInfoList = facilityInfoService.queryByRoomId(roomId);
        List<LabelInfo> labelInfoList = labelInfoService.queryByRoomId(roomId);
        List<PaymentType> paymentTypeList = paymentTypeService.queryByRoomId(roomId);
        List<LeaseTerm> leaseTermList = leaseTermService.queryByRoomId(roomId);

        //进行封装
        return new RoomDetailVo(
                roomInfo.getId(),
                roomInfo.getRoomNumber(),
                roomInfo.getRent(),
                roomInfo.getApartmentId(),
                roomInfo.getIsRelease(),
                apartmentInfo,
                graphVoList,
                attrValueVoList,
                facilityInfoList,
                labelInfoList,
                paymentTypeList,
                leaseTermList
        );
    }

    @Transactional
    @Override
    public void removeByRoomId(Long id) {

        super.removeById(id);
        deleteInfo(id);

        //删除redis缓存
        String key = RedisConstant.APP_ROOM_PREFIX + id;
        redisTemplate.delete(key);
    }

    private void deleteInfo(Long roomId) {
        graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>()
                .eq(GraphInfo::getItemType, ItemType.ROOM)
                .eq(GraphInfo::getItemId, roomId));
        roomAttrValueService.remove(new LambdaQueryWrapper<RoomAttrValue>()
                .eq(RoomAttrValue::getRoomId, roomId));
        roomFacilityService.remove(new LambdaQueryWrapper<RoomFacility>()
                .eq(RoomFacility::getRoomId, roomId));
        roomLabelService.remove(new LambdaQueryWrapper<RoomLabel>()
                .eq(RoomLabel::getRoomId, roomId));
        roomPaymentTypeService.remove(new LambdaQueryWrapper<RoomPaymentType>()
                .eq(RoomPaymentType::getRoomId, roomId));
        roomLeaseTermService.remove(new LambdaQueryWrapper<RoomLeaseTerm>()
                .eq(RoomLeaseTerm::getRoomId, roomId));
    }
}




