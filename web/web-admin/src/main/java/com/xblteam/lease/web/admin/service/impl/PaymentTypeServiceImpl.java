package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.PaymentType;
import com.xblteam.lease.web.admin.service.PaymentTypeService;
import com.xblteam.lease.web.admin.mapper.PaymentTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Override
    public List<PaymentType> queryByRoomId(Long roomId) {
        List<PaymentType> paymentTypeList = paymentTypeMapper.queryByRoomId(roomId);
        System.out.println("paymentTypeList = " + paymentTypeList);
        return paymentTypeList;
    }
}




