package com.xblteam.lease.web.admin.vo.room;

import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.vo.attr.AttrValueVo;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Schema(description = "房间信息")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDetailVo extends RoomInfo {

    @Schema(description = "所属公寓信息")
    private ApartmentInfo apartmentInfo;

    @Schema(description = "图片列表")
    private List<GraphVo> graphVoList;

    @Schema(description = "属性信息列表")
    private List<AttrValueVo> attrValueVoList;

    @Schema(description = "配套信息列表")
    private List<FacilityInfo> facilityInfoList;

    @Schema(description = "标签信息列表")
    private List<LabelInfo> labelInfoList;

    @Schema(description = "支付方式列表")
    private List<PaymentType> paymentTypeList;

    @Schema(description = "可选租期列表")
    private List<LeaseTerm> leaseTermList;

    public RoomDetailVo(
            Long id,
            String roomNumber,
            BigDecimal rent,
            Long apartmentId,
            ReleaseStatus isRelease,
            ApartmentInfo apartmentInfo,
            List<GraphVo> graphVoList,
            List<AttrValueVo> attrValueVoList,
            List<FacilityInfo> facilityInfoList,
            List<LabelInfo> labelInfoList,
            List<PaymentType> paymentTypeList,
            List<LeaseTerm> leaseTermList
    ) {
        setId(id);
        setRoomNumber(roomNumber);
        setRent(rent);
        setApartmentId(apartmentId);
        setIsRelease(isRelease);
        setApartmentInfo(apartmentInfo);
        setGraphVoList(graphVoList);
        setAttrValueVoList(attrValueVoList);
        setFacilityInfoList(facilityInfoList);
        setLabelInfoList(labelInfoList);
        setPaymentTypeList(paymentTypeList);
        setLeaseTermList(leaseTermList);
    }
}
