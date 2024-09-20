package com.xblteam.lease.web.app.vo.agreement;

import com.xblteam.lease.model.entity.LeaseAgreement;
import com.xblteam.lease.model.enums.LeaseSourceType;
import com.xblteam.lease.model.enums.LeaseStatus;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "租约详细信息")
@NoArgsConstructor
@AllArgsConstructor
public class AgreementDetailVo extends LeaseAgreement {

    @Schema(description = "公寓名称")
    private String apartmentName;

    @Schema(description = "公寓图片列表")
    private List<GraphVo> apartmentGraphVoList;

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "房间图片列表")
    private List<GraphVo> roomGraphVoList;

    @Schema(description = "支付方式")
    private String paymentTypeName;

    @Schema(description = "租期月数")
    private Integer leaseTermMonthCount;

    @Schema(description = "租期单位")
    private String leaseTermUnit;

    public AgreementDetailVo(
            Long id,
            String phone,
            String name,
            String identificationNumber,
            Long apartmentId,
            Long roomId,
            Date leaseStartDate,
            Date leaseEndDate,
            Long leaseTermId,
            BigDecimal rent,
            BigDecimal deposit,
            Long paymentTypeId,
            LeaseStatus status,
            LeaseSourceType sourceType,
            String additionalInfo,
            String apartmentName,
            List<GraphVo> apartmentGraphVoList,
            String roomNumber,
            List<GraphVo> roomGraphVoList,
            String paymentTypeName,
            Integer leaseTermMonthCount,
            String leaseTermUnit
    ) {
        setId(id);
        setPhone(phone);
        setName(name);
        setIdentificationNumber(identificationNumber);
        setApartmentId(apartmentId);
        setRoomId(roomId);
        setLeaseStartDate(leaseStartDate);
        setLeaseEndDate(leaseEndDate);
        setLeaseTermId(leaseTermId);
        setRent(rent);
        setDeposit(deposit);
        setPaymentTypeId(paymentTypeId);
        setStatus(status);
        setSourceType(sourceType);
        setAdditionalInfo(additionalInfo);
        setApartmentName(apartmentName);
        setApartmentGraphVoList(apartmentGraphVoList);
        setRoomNumber(roomNumber);
        setRoomGraphVoList(roomGraphVoList);
        setPaymentTypeName(paymentTypeName);
        setLeaseTermMonthCount(leaseTermMonthCount);
        setLeaseTermUnit(leaseTermUnit);
    }

}