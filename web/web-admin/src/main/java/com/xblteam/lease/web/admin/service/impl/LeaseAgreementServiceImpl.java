package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.web.admin.mapper.*;
import com.xblteam.lease.web.admin.service.LeaseAgreementService;
import com.xblteam.lease.web.admin.service.PaymentTypeService;
import com.xblteam.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.xblteam.lease.web.admin.vo.agreement.AgreementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
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
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;


    @Override
    public IPage<AgreementVo> queryPage(long current, long size, AgreementQueryVo queryVo) {
        IPage<AgreementVo> iPage = leaseAgreementMapper.queryPage(new Page<AgreementVo>(current, size), queryVo);
        System.out.println("iPage = " + iPage);
        return iPage;
    }

    @Override
    public AgreementVo queryLeaseAgreement(Long id) {

        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return new AgreementVo();
        }

        Long apartmentId = leaseAgreement.getApartmentId();
        ApartmentInfo apartmentInfo = null;
        if (apartmentId != null) {
            apartmentInfo = apartmentInfoMapper.selectById(apartmentId);
        }

        Long roomId = leaseAgreement.getRoomId();
        RoomInfo roomInfo = null;
        if (roomId != null) {
            roomInfo = roomInfoMapper.selectById(roomId);
        }

        Long paymentTypeId = leaseAgreement.getPaymentTypeId();
        PaymentType paymentType = null;
        if (paymentTypeId != null) {
            paymentType = paymentTypeMapper.selectById(paymentTypeId);
        }

        Long leaseTermId = leaseAgreement.getLeaseTermId();
        LeaseTerm leaseTerm = null;
        if (leaseTermId != null) {
            leaseTerm = leaseTermMapper.selectById(leaseTermId);
        }

        return new AgreementVo(
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
                apartmentInfo,
                roomInfo,
                paymentType,
                leaseTerm
        );

    }
}




