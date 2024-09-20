package com.xblteam.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum BaseStatus implements BaseEnum {

    ENABLE(1, "正常"),
    DISABLE(0, "禁用");

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
