package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xblteam.lease.model.FacilityInfo
*/
@Mapper
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    /**
     * 根据公寓id查询标签信息列表
     * @param apartmentId 公寓id
     * @return 标签信息列表
     */
    @Select("""
            select
                facility_info.id,
                facility_info.name,
                facility_info.type,
                facility_info.icon
            from
                facility_info,
                apartment_facility
            where
                apartment_facility.apartment_id = #{apartmentId}
            and apartment_facility.facility_id = facility_info.id
            and apartment_facility.is_deleted = 0
            """)
    List<FacilityInfo> queryByApartmentId(Long apartmentId);

    @Select("""
            select
                facility_info.id,
                facility_info.type,
                facility_info.name,
                facility_info.icon
            from
                facility_info,
                room_facility
            where
                    room_facility.room_id = #{roomId}
                and room_facility.facility_id = facility_info.id
                and room_facility.is_deleted = 0
                and facility_info.is_deleted = 0
            """)
    List<FacilityInfo> queryByRoomId(@Param("roomId") Long roomId);
}




