package com.xblteam.lease.web.admin.vo.apartment;


import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.FacilityInfo;
import com.xblteam.lease.model.entity.LabelInfo;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.vo.fee.FeeValueVo;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "公寓信息")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApartmentDetailVo extends ApartmentInfo {

    @Schema(description = "图片列表")
    private List<GraphVo> graphVoList;

    @Schema(description = "标签列表")
    private List<LabelInfo> labelInfoList;

    @Schema(description = "配套列表")
    private List<FacilityInfo> facilityInfoList;

    @Schema(description = "杂费列表")
    private List<FeeValueVo> feeValueVoList;

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
            List<FeeValueVo> feeValueVoList) {
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
        this.graphVoList = graphVoList;
        this.labelInfoList = labelInfoList;
        this.facilityInfoList = facilityInfoList;
        this.feeValueVoList = feeValueVoList;
    }
}
