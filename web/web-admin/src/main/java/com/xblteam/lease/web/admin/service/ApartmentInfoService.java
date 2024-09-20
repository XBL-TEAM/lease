package com.xblteam.lease.web.admin.service;

import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    void saveOrUpdateApartmentSubmit(ApartmentSubmitVo apartmentSubmitVo);

    IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo apartmentQueryVo);

    ApartmentDetailVo getDetailById(Long apartmentId);

    boolean removeInfoById(Long id);

}
