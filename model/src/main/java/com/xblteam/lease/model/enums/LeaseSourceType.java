package com.xblteam.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum LeaseSourceType implements BaseEnum {

    NEW(1, "新签"),
    RENEW(2, "续约");

    @JsonValue
    @EnumValue
    private Integer code;
    private String name;

}
