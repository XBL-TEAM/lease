package com.xblteam.lease.web.app.vo.room;

import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.app.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.app.vo.attr.AttrValueVo;
import com.xblteam.lease.web.app.vo.fee.FeeValueVo;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "APP房间详情")
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailVo extends RoomInfo {

    @Schema(description = "所属公寓信息")
    private ApartmentItemVo apartmentItemVo;

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

    @Schema(description = "杂费列表")
    private List<FeeValueVo> feeValueVoList;

    @Schema(description = "租期列表")
    private List<LeaseTerm> leaseTermList;

    public RoomDetailVo(
            Long id,
            String roomNumber,
            BigDecimal rent,
            Long apartmentId,
            ReleaseStatus isRelease,
            ApartmentItemVo apartmentItemVo,
            List<GraphVo> graphVoList,
            List<AttrValueVo> attrValueVoList,
            List<FacilityInfo> facilityInfoList,
            List<LabelInfo> labelInfoList,
            List<PaymentType> paymentTypeList,
            List<FeeValueVo> feeValueVoList,
            List<LeaseTerm> leaseTermList
    ) {
        setId(id);
        setRoomNumber(roomNumber);
        setRent(rent);
        setApartmentId(apartmentId);
        setIsRelease(isRelease);
        setApartmentItemVo(apartmentItemVo);
        setGraphVoList(graphVoList);
        setAttrValueVoList(attrValueVoList);
        setFacilityInfoList(facilityInfoList);
        setLabelInfoList(labelInfoList);
        setPaymentTypeList(paymentTypeList);
        setFeeValueVoList(feeValueVoList);
        setLeaseTermList(leaseTermList);
    }

}
