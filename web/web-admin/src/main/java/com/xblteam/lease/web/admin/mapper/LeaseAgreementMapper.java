package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.model.entity.LeaseAgreement;
import com.xblteam.lease.model.enums.LeaseSourceType;
import com.xblteam.lease.model.enums.LeaseStatus;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.xblteam.lease.web.admin.vo.agreement.AgreementVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.LeaseAgreement
 */
@Mapper
public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    /**
     * 根据公寓id查询入住房间数
     *
     * @param apartmentId 公寓id
     * @return 数量
     */
    @Select("""
            select
            (select count(*)
            from room_info
            where apartment_id = #{apartmentId}
              and is_deleted = 0)
            -
            (select count(*)
             from lease_agreement
             where apartment_id = #{apartmentId}
               and is_deleted = 0
               and status in (2, 5))
            """)
    Long countCheckInByApartmentId(@Param("apartmentId") Long apartmentId);

    /**
     * 根据房间id查询到期时间
     *
     * @param roomId
     * @return
     */
    @Select("select lease_end_date from lease_agreement where room_id = #{roomId} and is_deleted = 0;")
    Date queryLeaseEndDateByRoomId(@Param("roomId") Long roomId);

    /**
     * 根据房间id查询是否入住
     *
     * @param roomId
     * @return
     */
    @Select("""
            select
                ifnull(
                    (select status
                     from lease_agreement
                     where room_id = #{roomId}
                       and status in (2,5)
                       and is_deleted = 0
                     ) > 0,
                     0
                );
            """)
    Boolean queryCheckInStatusByRoomId(@Param("roomId") Long roomId);

    @Select("""
            <script>
            select
                lease_agreement.id,
                lease_agreement.phone,
                lease_agreement.name,
                lease_agreement.identification_number,
                lease_agreement.apartment_id,
                lease_agreement.room_id,
                lease_agreement.lease_start_date,
                lease_agreement.lease_end_date,
                lease_agreement.lease_term_id,
                lease_agreement.rent,
                lease_agreement.deposit,
                lease_agreement.payment_type_id,
                lease_agreement.status,
                lease_agreement.source_type,
                lease_agreement.additional_info,
                apartment_info.id apartment_info_id,
                apartment_info.name apartment_name,
                apartment_info.introduction,
                apartment_info.district_id,
                apartment_info.district_name,
                apartment_info.city_id,
                apartment_info.city_name,
                apartment_info.province_id,
                apartment_info.province_name,
                apartment_info.address_detail,
                apartment_info.latitude,
                apartment_info.longitude,
                apartment_info.phone apartment_phone,
                apartment_info.is_release apartment_release,
                room_info.id room_info_id,
                room_info.room_number,
                room_info.rent room_rent,
                room_info.apartment_id room_apartment_id,
                room_info.is_release room_release,
                payment_type.id payment_id,
                payment_type.name payment_type_name,
                payment_type.pay_month_count,
                payment_type.additional_info payment_type_additional_info,
                lease_term.id lease_term_info_id,
                lease_term.month_count,
                lease_term.unit
            from
                lease_agreement
            left join
                apartment_info
                on lease_agreement.apartment_id = apartment_info.id
                and apartment_info.is_deleted=0
            left join
                  room_info
                on lease_agreement.room_id = room_info.id
                and room_info.is_deleted=0
            left join
                  payment_type
                on lease_agreement.payment_type_id = payment_type.id
                and payment_type.is_deleted=0
            left join
                  lease_term
                on lease_agreement.lease_term_id = lease_term.id
                and lease_term.is_deleted=0
                <where>
                        lease_agreement.is_deleted = 0
                    and apartment_info.is_release = 1
                    and room_info.is_release = 1
                    <if test="queryVo.provinceId != null">
                        and apartment_info.province_id = #{queryVo.provinceId}
                    </if>
                    <if test="queryVo.cityId != null">
                        and apartment_info.city_id = #{queryVo.cityId}
                    </if>
                    <if test="queryVo.districtId != null">
                        and apartment_info.district_id = #{queryVo.districtId}
                    </if>
                    <if test="queryVo.apartmentId != null">
                        and lease_agreement.apartment_id = #{queryVo.apartmentId}
                    </if>
                    <if test="queryVo.roomNumber != null and queryVo.roomNumber != ''">
                        and room_info.room_number like concat('%',#{queryVo.roomNumber},'%')
                    </if>
                    <if test="queryVo.name != null and queryVo.name != ''">
                        and lease_agreement.name like concat('%',#{queryVo.name},'%')
                    </if>
                    <if test="queryVo.phone != null and queryVo.phone != ''">
                        and lease_agreement.phone like concat('%',#{queryVo.phone},'%')
                    </if>
                </where>
            </script>
            """)
    @Results(
            id = "agreementVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "phone", column = "phone", javaType = String.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "identificationNumber", column = "identification_number", javaType = String.class),
                    @Result(property = "apartmentId", column = "apartment_id", javaType = Long.class),
                    @Result(property = "roomId", column = "room_id", javaType = Long.class),
                    @Result(property = "leaseStartDate", column = "lease_start_date", javaType = Date.class),
                    @Result(property = "leaseEndDate", column = "lease_end_date", javaType = Date.class),
                    @Result(property = "leaseTermId", column = "lease_term_id", javaType = Long.class),
                    @Result(property = "rent", column = "rent", javaType = BigDecimal.class),
                    @Result(property = "deposit", column = "deposit", javaType = BigDecimal.class),
                    @Result(property = "paymentTypeId", column = "payment_type_id", javaType = Long.class),
                    @Result(property = "status", column = "status", javaType = LeaseStatus.class),
                    @Result(property = "sourceType", column = "source_type", javaType = LeaseSourceType.class),
                    @Result(property = "additionalInfo", column = "additional_info", javaType = String.class),
                    @Result(property = "apartmentInfo.id", column = "apartment_info_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.name", column = "apartment_name", javaType = String.class),
                    @Result(property = "apartmentInfo.introduction", column = "introduction", javaType = String.class),
                    @Result(property = "apartmentInfo.districtId", column = "district_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.districtName", column = "district_name", javaType = String.class),
                    @Result(property = "apartmentInfo.cityId", column = "city_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.cityName", column = "city_name", javaType = String.class),
                    @Result(property = "apartmentInfo.provinceId", column = "province_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.provinceName", column = "province_name", javaType = String.class),
                    @Result(property = "apartmentInfo.addressDetail", column = "address_detail", javaType = String.class),
                    @Result(property = "apartmentInfo.latitude", column = "latitude", javaType = String.class),
                    @Result(property = "apartmentInfo.longitude", column = "longitude", javaType = String.class),
                    @Result(property = "apartmentInfo.phone", column = "apartment_phone", javaType = String.class),
                    @Result(property = "apartmentInfo.isRelease", column = "apartment_release", javaType = ReleaseStatus.class),
                    @Result(property = "roomInfo.id", column = "room_info_id", javaType = Long.class),
                    @Result(property = "roomInfo.roomNumber", column = "room_number", javaType = String.class),
                    @Result(property = "roomInfo.rent", column = "room_rent", javaType = BigDecimal.class),
                    @Result(property = "roomInfo.apartmentId", column = "room_apartment_id", javaType = Long.class),
                    @Result(property = "roomInfo.isRelease", column = "room_release", javaType = ReleaseStatus.class),
                    @Result(property = "paymentType.id", column = "payment_id", javaType = Long.class),
                    @Result(property = "paymentType.name", column = "payment_type_name", javaType = String.class),
                    @Result(property = "paymentType.payMonthCount", column = "pay_month_count", javaType = String.class),
                    @Result(property = "paymentType.additionalInfo", column = "payment_type_additional_info", javaType = String.class),
                    @Result(property = "leaseTerm.id", column = "lease_term_info_id", javaType = Long.class),
                    @Result(property = "leaseTerm.monthCount", column = "pay_month_count", javaType = Integer.class),
                    @Result(property = "leaseTerm.unit", column = "unit", javaType = String.class)
            }
    )
    IPage<AgreementVo> queryPage(Page<AgreementVo> agreementVoPage, @Param("queryVo") AgreementQueryVo queryVo);
}




