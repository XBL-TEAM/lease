package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.PaymentType
 */
@Mapper
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    @Select("""
            select
                payment_type.id,
                payment_type.name,
                payment_type.pay_month_count,
                payment_type.additional_info
            from
                room_payment_type,
                payment_type
            where
                    room_payment_type.room_id = #{roomId}
                and room_payment_type.payment_type_id = payment_type.id
                and room_payment_type.is_deleted = 0
                and payment_type.is_deleted = 0;
            """)
    List<PaymentType> queryByRoomId(@Param("roomId") Long roomId);
}




