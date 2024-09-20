package com.xblteam.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum SystemUserType implements BaseEnum {

    ADMIN(0, "管理员"),
    COMMON(1, "普通用户");

    @EnumValue
    @JsonValue
    private Integer code;
    private String name;
}
