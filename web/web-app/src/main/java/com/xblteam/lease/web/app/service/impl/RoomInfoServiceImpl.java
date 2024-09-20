package com.xblteam.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.common.constant.RedisConstant;
import com.xblteam.lease.common.login.LoginUserHolder;
import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.app.mapper.*;
import com.xblteam.lease.web.app.service.ApartmentInfoService;
import com.xblteam.lease.web.app.service.BrowsingHistoryService;
import com.xblteam.lease.web.app.service.RoomInfoService;
import com.xblteam.lease.web.app.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.app.vo.attr.AttrValueVo;
import com.xblteam.lease.web.app.vo.fee.FeeValueVo;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import com.xblteam.lease.web.app.vo.room.RoomDetailVo;
import com.xblteam.lease.web.app.vo.room.RoomItemVo;
import com.xblteam.lease.web.app.vo.room.RoomQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    @Override
    public IPage<RoomItemVo> pageItem(long current, long size, RoomQueryVo queryVo) {
        IPage<RoomItemVo> iPage = roomInfoMapper.pageItem(new Page<>(current, size), queryVo);
        System.out.println("iPage = " + iPage);
        return iPage;
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {

        //查询redis缓存
        String key = RedisConstant.APP_ROOM_PREFIX + id;
        RoomDetailVo roomDetailVo = (RoomDetailVo) redisTemplate.opsForValue().get(key);

        //如果redis缓存未命中
        if (roomDetailVo == null) {

            //查询数据库
            RoomInfo roomInfo = roomInfoMapper.selectById(id);
            if (roomInfo == null) return new RoomDetailVo();
            List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);
            List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);
            List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);
            List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);
            List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);
            List<AttrValueVo> attrValueVoList = attrValueMapper.selectListByRoomId(id);
            List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(roomInfo.getApartmentId());
            ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(roomInfo.getApartmentId());

            //生成结果
            roomDetailVo = new RoomDetailVo(
                    roomInfo.getId(),
                    roomInfo.getRoomNumber(),
                    roomInfo.getRent(),
                    roomInfo.getApartmentId(),
                    roomInfo.getIsRelease(),
                    apartmentItemVo,
                    graphVoList,
                    attrValueVoList,
                    facilityInfoList,
                    labelInfoList,
                    paymentTypeList,
                    feeValueVoList,
                    leaseTermList);

            //存入redis缓存
            redisTemplate.opsForValue().set(key, roomDetailVo);
        }


        //保存浏览历史
        browsingHistoryService.saveHistory(
                LoginUserHolder.getLoginUser().getUserId(),
                id);

        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(long current, long size, Long id) {
        IPage<RoomItemVo> iPage = new Page<>(current, size);
        IPage<RoomItemVo> page = roomInfoMapper.pageItemByApartmentId(iPage, id);
        System.out.println("roomItemVo = " + page);
        return page;
    }
}




