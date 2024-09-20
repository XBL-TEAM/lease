package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.ViewAppointment;
import com.xblteam.lease.model.enums.AppointmentStatus;
import com.xblteam.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.ViewAppointment
*/
@Mapper
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    @Select("""
            select
                view_appointment.id,
                view_appointment.apartment_id,
                view_appointment.appointment_time,
                view_appointment.appointment_status,
                apartment_info.name apartment_name
            from view_appointment
            left join apartment_info
                on view_appointment.apartment_id = apartment_info.id
                and apartment_info.is_deleted = 0
            where view_appointment.is_deleted = 0
              and view_appointment.user_id = #{userId}
            order by view_appointment.create_time
            desc
            """)
    @Results(
            id = "appointmentItemVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "apartmentName", column = "apartment_name", javaType = String.class),
                    @Result(property = "appointmentTime", column = "appointment_time", javaType = Date.class),
                    @Result(property = "appointmentStatus", column = "appointment_status", javaType = AppointmentStatus.class),
                    @Result(
                            property = "graphVoList",
                            column = "apartment_id",
                            javaType = List.class,
                            many = @Many(
                                    select = "com.xblteam.lease.web.app.mapper.GraphInfoMapper.queryById",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }

    )
    List<AppointmentItemVo> listItemByUserId(Long userId);
}




