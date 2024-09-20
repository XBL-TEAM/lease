package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.LabelInfo;
import com.xblteam.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xblteam.lease.model.LabelInfo
*/
@Mapper
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    @Select("""
            select
                label_info.id,
                label_info.name,
                label_info.type
            from
                label_info,
                apartment_label
            where
                    apartment_label.apartment_id = #{apartmentId}
                and apartment_label.label_id = label_info.id
                and apartment_label.is_deleted = 0
                and label_info.is_deleted = 0
            """)
    List<LabelInfo> queryByApartmentId(@Param("apartmentId") Long apartmentId);

    @Select("""
            select
                label_info.id,
                label_info.name,
                label_info.type
            from
                label_info,
                room_label
            where
                    room_label.room_id = #{roomId}
                and room_label.label_id = label_info.id
                and room_label.is_deleted = 0
                and label_info.is_deleted = 0
            """)
    List<LabelInfo> queryByRoomId(@Param("roomId") Long roomId);
}




