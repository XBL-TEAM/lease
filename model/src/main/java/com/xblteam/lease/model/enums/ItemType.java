package com.xblteam.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ItemType implements BaseEnum {

    APARTMENT(1, "公寓"),
    ROOM(2, "房间");

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;
}
