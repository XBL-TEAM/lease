package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xblteam.lease.model.entity.AttrValue;
import com.xblteam.lease.web.admin.vo.attr.AttrValueVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.AttrValue
 */
@Mapper
public interface AttrValueMapper extends BaseMapper<AttrValue> {

    /**
     * 查询所有未删除属性值信息
     *
     * @param id
     * @return
     */
    @Select("select * from attr_value where attr_key_id=#{id} and is_deleted = 0")
    List<AttrValue> findById(@Param("id") Integer id);

    @Select("""
            select
                attr_value.id,
                attr_value.name,
                attr_value.attr_key_id
            from
                room_attr_value,
                attr_value
            where
                    room_attr_value.room_id = #{roomId}
                and room_attr_value.attr_value_id = attr_value.id
                and room_attr_value.is_deleted = 0
                and attr_value.is_deleted = 0
            """)
    @Results(
            id = "attrValueVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "attrKeyId", column = "attr_key_id", javaType = Long.class),
                    @Result(
                            property = "attrKeyName",
                            column = "attr_key_id",
                            javaType = String.class,
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.AttrKeyMapper.queryNameById",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    List<AttrValueVo> queryByRoomId(@Param("roomId") Long roomId);
}




