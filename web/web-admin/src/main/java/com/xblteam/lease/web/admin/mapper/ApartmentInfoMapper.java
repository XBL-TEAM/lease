package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.enums.LeaseStatus;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.xblteam.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.ApartmentInfo
 */
@Mapper
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    @Select(value = {
            "<script>",
            "select id, name, introduction, district_id, district_name, city_id, city_name, province_id, province_name, address_detail, latitude, longitude, phone, is_release from apartment_info ",
            "<where>",
            " is_deleted = 0",
            "<if test='apartmentQueryVo.provinceId != null'>",
            " and province_id = #{apartmentQueryVo.provinceId}",
            "</if>",
            "<if test='apartmentQueryVo.cityId != null'>",
            " and city_id = #{apartmentQueryVo.cityId}",
            "</if>",
            "<if test='apartmentQueryVo.districtId != null'>",
            " and district_id = #{apartmentQueryVo.districtId}",
            "</if>",
            "</where>",
            "</script>"
    })
    @Results(
            id = "apartmentItemVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "introduction", column = "introduction", javaType = String.class),
                    @Result(property = "districtId", column = "district_id", javaType = Long.class),
                    @Result(property = "districtName", column = "district_name", javaType = String.class),
                    @Result(property = "cityId", column = "city_id", javaType = long.class),
                    @Result(property = "cityName", column = "city_name", javaType = String.class),
                    @Result(property = "provinceId", column = "province_id", javaType = Long.class),
                    @Result(property = "provinceName", column = "province_name", javaType = String.class),
                    @Result(property = "addressDetail", column = "address_detail", javaType = String.class),
                    @Result(property = "latitude", column = "latitude", javaType = String.class),
                    @Result(property = "longitude", column = "longitude", javaType = String.class),
                    @Result(property = "phone", column = "phone", javaType = String.class),
                    @Result(property = "isRelease", column = "is_release", javaType = ReleaseStatus.class),
                    @Result(
                            property = "totalRoomCount",
                            column = "id",
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.RoomInfoMapper.countAllByApartmentId",
                                    fetchType = FetchType.LAZY
                            )
                    ),
                    @Result(
                            property = "freeRoomCount",
                            column = "id",
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.LeaseAgreementMapper.countCheckInByApartmentId",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo apartmentQueryVo);

    @Select("""
            select *
            from apartment_info
            where id = (
                select apartment_id
                from room_info
                where id = #{roomId}
                and is_deleted = 0
            )
              and is_deleted = 0;
            """)
    ApartmentInfo queryByRoomId(@Param("roomId") Long roomId);
}




