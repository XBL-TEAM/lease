package com.xblteam.lease.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用实体类
 */
@Data
@JsonIgnoreProperties(value = "handler")  //忽略json序列化时添加的字段
public class BaseEntity implements Serializable {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "创建时间")
    @JsonIgnore
    @TableField(value = "create_time",fill = FieldFill.INSERT)  //fill:内容自动填充，需配置拦截器
    private Date createTime;

    @Schema(description = "更新时间")
    @JsonIgnore
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @Schema(description = "逻辑删除")
    @JsonIgnore
    @TableLogic
    @TableField(value = "is_deleted")
    private Byte isDeleted;

}