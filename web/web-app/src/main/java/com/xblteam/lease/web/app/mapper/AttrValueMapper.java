package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.AttrValue;
import com.xblteam.lease.web.app.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.AttrValue
*/
@Mapper
public interface AttrValueMapper extends BaseMapper<AttrValue> {

    @Select("""
            select av.id,
                   av.name,
                   av.attr_key_id,
                   ak.name attr_key_name
            from attr_value av
            left join attr_key ak
                on av.attr_key_id = ak.id
                and ak.is_deleted = 0
            where av.is_deleted = 0
              and av.id in (
                select attr_value_id
                from room_attr_value
                where is_deleted = 0
                    and room_id = #{id}
              )
            """)
    List<AttrValueVo> selectListByRoomId(Long id);
}




