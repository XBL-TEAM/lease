package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.GraphInfo;
import com.xblteam.lease.model.enums.ItemType;
import com.xblteam.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xblteam.lease.model.GraphInfo
*/
@Mapper
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    /**
     * 根据公寓id查询图片信息列表
     * @param apartmentId 公寓id
     * @return 图片信息列表
     */
    @Select("""
            select name,url
            from graph_info
            where item_id = #{apartmentId}
              and item_type = #{itemType}
              and is_deleted = 0;
            """)
    List<GraphVo> queryByApartmentId(@Param("apartmentId") Long apartmentId,@Param("itemType") ItemType itemType);

    @Select("""
            select name,url
            from graph_info
            where item_id = #{roomId}
              and item_type = #{itemType}
              and is_deleted = 0;
            """)
    List<GraphVo> queryByRoomId(@Param("roomId") Long roomId,@Param("itemType") ItemType itemType);
}




