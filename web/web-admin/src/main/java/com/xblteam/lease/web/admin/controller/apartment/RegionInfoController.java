package com.xblteam.lease.web.admin.controller.apartment;


import ch.qos.logback.classic.spi.EventArgUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xblteam.lease.common.result.Result;
import com.xblteam.lease.model.entity.CityInfo;
import com.xblteam.lease.model.entity.DistrictInfo;
import com.xblteam.lease.model.entity.ProvinceInfo;
import com.xblteam.lease.web.admin.service.CityInfoService;
import com.xblteam.lease.web.admin.service.DistrictInfoService;
import com.xblteam.lease.web.admin.service.ProvinceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "地区信息管理")
@RestController
@RequestMapping("/admin/region")
public class RegionInfoController {

    @Autowired
    private ProvinceInfoService provinceInfoService;    //省份信息
    @Autowired
    private CityInfoService cityInfoService;            //城市信息
    @Autowired
    private DistrictInfoService districtInfoService;    //区县信息

    @Operation(summary = "查询省份信息列表")
    @GetMapping("province/list")
    public Result<List<ProvinceInfo>> listProvince() {
        List<ProvinceInfo> list = provinceInfoService.list();
        return Result.ok(list);
    }

    @Operation(summary = "根据省份id查询城市信息列表")
    @GetMapping("city/listByProvinceId")
    public Result<List<CityInfo>> listCityInfoByProvinceId(@RequestParam Long id) {
        List<CityInfo> list = cityInfoService.list(new LambdaQueryWrapper<CityInfo>()
                .eq(CityInfo::getProvinceId, id));
        return Result.ok(list);
    }

    @GetMapping("district/listByCityId")
    @Operation(summary = "根据城市id查询区县信息")
    public Result<List<DistrictInfo>> listDistrictInfoByCityId(@RequestParam Long id) {
        List<DistrictInfo> list = districtInfoService.list(new LambdaQueryWrapper<DistrictInfo>()
                .eq(DistrictInfo::getCityId, id));
        return Result.ok(list);
    }

}
