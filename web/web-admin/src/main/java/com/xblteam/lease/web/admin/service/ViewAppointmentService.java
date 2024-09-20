package com.xblteam.lease.web.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.model.entity.ViewAppointment;
import com.xblteam.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.xblteam.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {


    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}
