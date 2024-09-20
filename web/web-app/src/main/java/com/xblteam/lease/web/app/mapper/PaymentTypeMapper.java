package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.PaymentType
*/
@Mapper
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    @Select("""
            select id,
                   name,
                   pay_month_count,
                   additional_info
            from payment_type
            where is_deleted = 0
              and id in (
                select payment_type_id
                from room_payment_type
                where is_deleted = 0
                and room_id = #{id}
              )
            """)
    List<PaymentType> selectListByRoomId(@Param("id") Long id);
}




