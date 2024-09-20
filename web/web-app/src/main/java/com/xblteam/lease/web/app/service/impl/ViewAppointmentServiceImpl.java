package com.xblteam.lease.web.app.service.impl;

import com.xblteam.lease.model.entity.ViewAppointment;
import com.xblteam.lease.web.app.mapper.ViewAppointmentMapper;
import com.xblteam.lease.web.app.service.ApartmentInfoService;
import com.xblteam.lease.web.app.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.web.app.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.xblteam.lease.web.app.vo.appointment.AppointmentItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Override
    public List<AppointmentItemVo> listItemByUserId(Long userId) {
        return viewAppointmentMapper.listItemByUserId(userId);
    }

    @Override
    public AppointmentDetailVo getDetailById(Long id) {

        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(viewAppointment.getApartmentId());

        return new AppointmentDetailVo(
                viewAppointment.getId(),
                viewAppointment.getUserId(),
                viewAppointment.getName(),
                viewAppointment.getPhone(),
                viewAppointment.getApartmentId(),
                viewAppointment.getAppointmentTime(),
                apartmentItemVo.getAddressDetail(),
                viewAppointment.getAppointmentStatus(),
                apartmentItemVo);
    }
}




