package com.xblteam.lease.web.app.service.impl;

import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.app.mapper.*;
import com.xblteam.lease.web.app.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.web.app.vo.agreement.AgreementDetailVo;
import com.xblteam.lease.web.app.vo.agreement.AgreementItemVo;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Override
    public List<AgreementItemVo> listItemByPhone(String phone) {
        return leaseAgreementMapper.listItemByPhone(phone);
    }

    @Override
    public AgreementDetailVo getDetailById(Long id) {

        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return null;
        }
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());
        List<GraphVo> roomGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, leaseAgreement.getRoomId());
        List<GraphVo> apartmentGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, leaseAgreement.getApartmentId());
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());

        return new AgreementDetailVo(
                leaseAgreement.getId(),
                leaseAgreement.getPhone(),
                leaseAgreement.getName(),
                leaseAgreement.getIdentificationNumber(),
                leaseAgreement.getApartmentId(),
                leaseAgreement.getRoomId(),
                leaseAgreement.getLeaseStartDate(),
                leaseAgreement.getLeaseEndDate(),
                leaseAgreement.getLeaseTermId(),
                leaseAgreement.getRent(),
                leaseAgreement.getDeposit(),
                leaseAgreement.getPaymentTypeId(),
                leaseAgreement.getStatus(),
                leaseAgreement.getSourceType(),
                leaseAgreement.getAdditionalInfo(),
                apartmentInfo.getName(),
                apartmentGraphVoList,
                roomInfo.getRoomNumber(),
                roomGraphVoList,
                paymentType.getName(),
                leaseTerm.getMonthCount(),
                leaseTerm.getUnit());
    }
}




