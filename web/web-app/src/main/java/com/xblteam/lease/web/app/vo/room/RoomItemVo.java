package com.xblteam.lease.web.app.vo.room;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.LabelInfo;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "APP房间列表实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomItemVo {

    @Schema(description = "房间id")
    private Long id;

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "租金（元/月）")
    private BigDecimal rent;

    @Schema(description = "房间图片列表")
    private List<GraphVo> graphVoList;

    @Schema(description = "房间标签列表")
    private List<LabelInfo> labelInfoList;

    @Schema(description = "房间所属公寓信息")
    private ApartmentInfo apartmentInfo;

}
