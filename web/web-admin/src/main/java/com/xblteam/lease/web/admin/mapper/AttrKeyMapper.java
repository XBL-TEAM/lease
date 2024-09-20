package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xblteam.lease.model.entity.AttrKey;
import com.xblteam.lease.web.admin.vo.attr.AttrKeyVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【attr_key(房间基本属性表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.AttrKey
 */
@Mapper
public interface AttrKeyMapper extends BaseMapper<AttrKey> {

    @Select("select * from attr_key where is_deleted = 0")
    @Results(
            id = "attrKeyVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(
                            property = "attrValueList",
                            column = "id",      // 用 attr_key 中的 id 字段去 attr_value 表中查 attr_key_id 字段
                            many = @Many(
                                    select = "com.xblteam.lease.web.admin.mapper.AttrValueMapper.findById",     //查询方法
                                    fetchType = FetchType.LAZY
                            ),
                            javaType = List.class

                    )
            }
    )
    List<AttrKeyVo> findAll();

    @Select("""
            select distinct
                attr_key.name
            from
                attr_key,
                attr_value
            where
                    attr_key.id = #{attrKeyId}
                and attr_key.id = attr_value.attr_key_id
                and attr_key.is_deleted = 0
                and attr_value.is_deleted = 0;
            """)
    String queryNameById(@Param("attrKeyId") Long attrKeyId);
}




