package com.xblteam.lease.web.admin.vo.agreement;

import com.xblteam.lease.model.entity.*;
import com.xblteam.lease.model.enums.LeaseSourceType;
import com.xblteam.lease.model.enums.LeaseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "租约信息")
public class AgreementVo extends LeaseAgreement {

    @Schema(description = "签约公寓信息")
    private ApartmentInfo apartmentInfo;

    @Schema(description = "签约房间信息")
    private RoomInfo roomInfo;

    @Schema(description = "支付方式")
    private PaymentType paymentType;

    @Schema(description = "租期")
    private LeaseTerm leaseTerm;

    public AgreementVo(
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
            ApartmentInfo apartmentInfo,
            RoomInfo roomInfo,
            PaymentType paymentType,
            LeaseTerm leaseTerm) {
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
        setApartmentInfo(apartmentInfo);
        setRoomInfo(roomInfo);
        setPaymentType(paymentType);
        setLeaseTerm(leaseTerm);
    }
}
