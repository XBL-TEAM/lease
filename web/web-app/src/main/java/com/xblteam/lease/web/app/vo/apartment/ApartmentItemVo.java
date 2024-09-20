package com.xblteam.lease.web.app.vo.apartment;


import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.LabelInfo;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "App端公寓信息")
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentItemVo extends ApartmentInfo {

    private List<LabelInfo> labelInfoList;
    private List<GraphVo> graphVoList;
    private BigDecimal minRent;

    public ApartmentItemVo(
            Long id,
            String name,
            String introduction,
            Long districtId,
            String districtName,
            Long cityId,
            String cityName,
            Long provinceId,
            String provinceName,
            String addressDetail,
            String latitude,
            String longitude,
            String phone,
            ReleaseStatus isRelease,
            List<LabelInfo> labelInfoList,
            List<GraphVo> graphVoList,
            BigDecimal minRent
            ) {
        setId(id);
        setName(name);
        setIntroduction(introduction);
        setDistrictId(districtId);
        setDistrictName(districtName);
        setCityId(cityId);
        setCityName(cityName);
        setProvinceId(provinceId);
        setProvinceName(provinceName);
        setAddressDetail(addressDetail);
        setLatitude(latitude);
        setLongitude(longitude);
        setPhone(phone);
        setIsRelease(isRelease);
        setLabelInfoList(labelInfoList);
        setGraphVoList(graphVoList);
        setMinRent(minRent);
    }
}
