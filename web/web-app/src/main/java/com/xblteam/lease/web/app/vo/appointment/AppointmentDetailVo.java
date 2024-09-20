package com.xblteam.lease.web.app.vo.appointment;

import com.xblteam.lease.model.entity.ViewAppointment;
import com.xblteam.lease.model.enums.AppointmentStatus;
import com.xblteam.lease.web.app.vo.apartment.ApartmentItemVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "APP端预约看房详情")
public class AppointmentDetailVo extends ViewAppointment {

    @Schema(description = "公寓基本信息")
    private ApartmentItemVo apartmentItemVo;

    public AppointmentDetailVo(
            Long id,
            Long userId,
            String name,
            String phone,
            Long apartmentId,
            Date appointmentTime,
            String additionalInfo,
            AppointmentStatus appointmentStatus,
            ApartmentItemVo apartmentItemVo
    ) {
        setId(id);
        setUserId(userId);
        setName(name);
        setPhone(phone);
        setApartmentId(apartmentId);
        setAppointmentTime(appointmentTime);
        setAdditionalInfo(additionalInfo);
        setAppointmentStatus(appointmentStatus);
        setApartmentItemVo(apartmentItemVo);
    }
}
