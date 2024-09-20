package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.FacilityInfo
*/
@Mapper
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    @Select("""
                select id,
                       type,
                       name,
                       icon
                from facility_info
                where is_deleted = 0
                  and id in (select facility_id
                             from room_facility
                             where is_deleted = 0
                               and room_id = #{id})
            """)
    List<FacilityInfo> selectListByRoomId(@Param("id") Long id);

    @Select("""
            select id,
                   type,
                   name,
                   icon
            from facility_info
            where is_deleted = 0
              and id in (
                select facility_id
                from apartment_facility
                where is_deleted = 0
                  and apartment_id = #{id})
            """)
    List<FacilityInfo> selectListByApartmentId(@Param("id") Long id);
}




