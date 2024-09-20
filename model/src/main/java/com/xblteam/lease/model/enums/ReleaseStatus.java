package com.xblteam.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ReleaseStatus implements BaseEnum {

    RELEASED(1, "已发布"),
    NOT_RELEASED(0, "未发布");

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

}
