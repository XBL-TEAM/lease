package com.xblteam.lease.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Schema(description = "房间&配套关联表")
@TableName(value = "room_facility")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomFacility extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "房间id")
    @TableField(value = "room_id")
    private Long roomId;

    @Schema(description = "房间设施id")
    @TableField(value = "facility_id")
    private Long facilityId;

}