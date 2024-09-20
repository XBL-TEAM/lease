package com.xblteam.lease.web.admin.vo.appointment;

import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.ViewAppointment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "预约看房信息")
public class AppointmentVo extends ViewAppointment {

    @Schema(description = "预约公寓信息")
    private ApartmentInfo apartmentInfo;

}
