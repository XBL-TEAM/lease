package com.xblteam.lease.web.admin.vo.attr;

import com.xblteam.lease.model.entity.AttrKey;
import com.xblteam.lease.model.entity.AttrValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.util.List;

@Data
public class AttrKeyVo extends AttrKey {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "属性value列表")
    private List<AttrValue> attrValueList;
}
