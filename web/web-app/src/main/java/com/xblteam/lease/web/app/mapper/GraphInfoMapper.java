package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.GraphInfo;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.GraphInfo
*/
@Mapper
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    @Select("""
            select
                name,
                url
            from graph_info
            where is_deleted = 0
                and item_type = 2
                and item_id = #{id}
            """)
    List<GraphVo> getGraphVo(@Param("id") Long id);


    @Select("select name, url from graph_info where is_deleted = 0 and item_type = #{itemType} and item_id = #{id}")
    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, @Param("id") Long id);

    @Select("""
            select
                name,
                url
            from graph_info
            where is_deleted = 0
                and item_type = 1
                and item_id = #{apartmentId}
            """)
    List<GraphVo> queryById(@Param("apartmentId") Long apartmentId);
}




