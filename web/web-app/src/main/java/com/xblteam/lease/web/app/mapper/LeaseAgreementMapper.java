package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.LeaseAgreement;
import com.xblteam.lease.model.enums.LeaseSourceType;
import com.xblteam.lease.model.enums.LeaseStatus;
import com.xblteam.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.LeaseAgreement
*/
@Mapper
public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    @Select("""
            select
                lease_agreement.id,
                lease_agreement.room_id,
                lease_agreement.lease_start_date,
                lease_agreement.lease_end_date,
                lease_agreement.rent,
                lease_agreement.payment_type_id,
                lease_agreement.status lease_status,
                lease_agreement.source_type,
                apartment_info.name apartment_name,
                room_info.room_number
            from lease_agreement
             left join apartment_info
                on lease_agreement.apartment_id = apartment_info.id
                and apartment_info.is_deleted = 0
             left join room_info
                on lease_agreement.room_id = room_info.id
                and room_info.is_deleted = 0
            where lease_agreement.is_deleted = 0
              and lease_agreement.phone = #{phone}
            """)
    @Results(
            id = "agreementItemVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "apartmentName", column = "apartment_name", javaType = String.class),
                    @Result(property = "roomNumber", column = "room_number", javaType = String.class),
                    @Result(property = "leaseStatus", column = "lease_status", javaType = LeaseStatus.class),
                    @Result(property = "leaseStartDate", column = "lease_start_date", javaType = Date.class),
                    @Result(property = "leaseEndDate", column = "lease_end_date", javaType = Date.class),
                    @Result(property = "sourceType", column = "source_type", javaType = LeaseSourceType.class),
                    @Result(property = "rent", column = "rent", javaType = BigDecimal.class),
                    @Result(
                            property = "roomGraphVoList",
                            column = "room_id",
                            javaType = List.class,
                            many = @Many(
                                    select = "com.xblteam.lease.web.app.mapper.GraphInfoMapper.getGraphVo",
                                    fetchType = FetchType.LAZY
                            )
                    ),
            }
    )
    List<AgreementItemVo> listItemByPhone(@Param("phone") String phone);
}




