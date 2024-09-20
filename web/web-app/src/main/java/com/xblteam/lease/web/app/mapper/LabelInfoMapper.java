package com.xblteam.lease.web.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xblteam.lease.model.entity.LabelInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【label_info(标签信息表)】的数据库操作Mapper
 * @createDate 2023-07-26 11:12:39
 * @Entity com.atguigu.lease.model.entity.LabelInfo
 */
@Mapper
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    @Select("""
            select *
            from label_info
            where is_deleted = 0
                and id in (
                    select label_id
                    from room_label
                    where is_deleted = 0
                        and room_id = #{id}
                )
            """)
    List<LabelInfo> selectByRoomId(@Param("id") Long id);

    @Select("""
                select id,
                       type,
                       name
                from label_info
                where is_deleted = 0
                  and id in (select label_id
                             from room_label
                             where is_deleted = 0
                               and room_id = #{id})
            """)
    List<LabelInfo> selectListByRoomId(@Param("id") Long id);

    @Select("""
            select id,
                   type,
                   name
            from label_info
            where is_deleted = 0
              and id in (
                 select label_id
                 from apartment_label
                 where is_deleted = 0
                   and apartment_id = #{id}
              )
            """)
    List<LabelInfo> selectListByApartmentId(Long id);
}




