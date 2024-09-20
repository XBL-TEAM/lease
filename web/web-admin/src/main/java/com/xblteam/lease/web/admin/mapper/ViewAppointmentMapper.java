package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xblteam.lease.model.entity.ViewAppointment;
import com.xblteam.lease.model.enums.AppointmentStatus;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.xblteam.lease.web.admin.vo.appointment.AppointmentVo;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.ViewAppointment
 */
@Mapper
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    @Select("""
            <script>
                select
                    va.id,
                    va.user_id,
                    va.name,
                    va.phone,
                    va.appointment_time,
                    va.additional_info,
                    va.appointment_status,
                    ai.id apartment_id,
                    ai.name apartment_name,
                    ai.introduction,
                    ai.district_id,
                    ai.district_name,
                    ai.city_id,
                    ai.city_name,
                    ai.province_id,
                    ai.province_name,
                    ai.address_detail,
                    ai.latitude,
                    ai.longitude,
                    ai.phone apartment_phone,
                    ai.is_release
                from view_appointment va,
                     apartment_info ai
                <where>
                    and va.apartment_id = ai.id
                    and ai.is_release = 1
                    and va.is_deleted = 0
                    and ai.is_deleted = 0
                    <if test = 'queryVo.provinceId != null'>
                        and ai.province_id = #{queryVo.provinceId}
                    </if>
                    <if test = 'queryVo.cityId != null'>
                        and ai.city_id = #{queryVo.cityId}
                    </if>
                    <if test = 'queryVo.districtId != null'>
                        and ai.district_id = #{queryVo.districtId}
                    </if>
                    <if test = 'queryVo.apartmentId != null'>
                        and va.apartment_id = #{queryVo.apartmentId}
                    </if>
                    <if test = 'queryVo.name != null and queryVo.name != ""' >
                        and va.name like concat('%',#{queryVo.name},'%' )
                    </if>
                    <if test = 'queryVo.phone != null and queryVo.phone != ""' >
                        and va.phone like concat('%',#{queryVo.phone},'%')
                    </if>
                </where>
            </script>
            """
    )
    @Results(
            id = "appointmentVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "userId", column = "user_id", javaType = Long.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "phone", column = "phone", javaType = String.class),
                    @Result(property = "apartmentId", column = "apartment_id", javaType = Long.class),
                    @Result(property = "appointmentTime", column = "appointment_time", javaType = Date.class),
                    @Result(property = "additionalInfo", column = "additional_info", javaType = String.class),
                    @Result(property = "appointmentStatus", column = "appointment_status", javaType = AppointmentStatus.class),
                    @Result(property = "apartmentInfo.id", column = "apartment_id", javaType = Long.class),
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
                    @Result(property = "apartmentInfo.isRelease", column = "is_release", javaType = ReleaseStatus.class),
            }
    )
    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, @Param("queryVo") AppointmentQueryVo queryVo);
}




