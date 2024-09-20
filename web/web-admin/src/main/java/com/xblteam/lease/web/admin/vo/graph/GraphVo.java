package com.xblteam.lease.web.admin.vo.graph;

import com.xblteam.lease.model.entity.GraphInfo;
import com.xblteam.lease.model.enums.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Schema(description = "图片信息")
@AllArgsConstructor
@NoArgsConstructor
public class GraphVo {


    @Schema(description = "图片名称")
    private String name;

    @Schema(description = "图片地址")
    private String url;

    public GraphInfo toGraphInfo(Long apartmentId, ItemType itemType) {
        GraphInfo graphInfo = new GraphInfo();
        graphInfo.setItemType(itemType);
        graphInfo.setItemId(apartmentId);
        if (this.name != null && this.url != null) {
            graphInfo.setName(this.name);
            graphInfo.setUrl(this.url);
        }
        return graphInfo;
    }

}
