package com.xblteam.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AppointmentStatus implements BaseEnum {

    WAITING(1, "待看房"),
    CANCELED(2, "已取消"),
    VIEWED(3, "已看房");

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
