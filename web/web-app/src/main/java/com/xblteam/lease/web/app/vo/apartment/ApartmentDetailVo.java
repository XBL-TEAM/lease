package com.xblteam.lease.web.app.vo.apartment;

import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.FacilityInfo;
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
@Schema(description = "APP端公寓信息详情")
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDetailVo extends ApartmentInfo {

    @Schema(description = "图片列表")
    private List<GraphVo> graphVoList;

    @Schema(description = "标签列表")
    private List<LabelInfo> labelInfoList;

    @Schema(description = "配套列表")
    private List<FacilityInfo> facilityInfoList;

    @Schema(description = "租金最小值")
    private BigDecimal minRent;

    public ApartmentDetailVo(
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
            List<GraphVo> graphVoList,
            List<LabelInfo> labelInfoList,
            List<FacilityInfo> facilityInfoList,
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
        setGraphVoList(graphVoList);
        setLabelInfoList(labelInfoList);
        setFacilityInfoList(facilityInfoList);
        setMinRent(minRent);
    }
}
