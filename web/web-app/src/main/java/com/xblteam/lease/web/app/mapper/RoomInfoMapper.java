package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.RoomInfo;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.app.vo.room.RoomItemVo;
import com.xblteam.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigDecimal;
import java.util.List;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.RoomInfo
*/
@Mapper
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    @Select("""
            <script>
            select
                room_info.id,
                room_info.room_number,
                room_info.apartment_id,
                room_info.rent,
                apartment_info.id apartment_info_id,
                apartment_info.name,
                apartment_info.introduction,
                apartment_info.district_id,
                apartment_info.district_name,
                apartment_info.city_id,
                apartment_info.city_name,
                apartment_info.province_id,
                apartment_info.province_name,
                apartment_info.address_detail,
                apartment_info.latitude,
                apartment_info.longitude,
                apartment_info.phone,
                apartment_info.is_release
            from room_info
            left join apartment_info
            on apartment_id = apartment_info.id
                and apartment_info.is_deleted=0
            <where>
                    room_info.is_deleted = 0
                and room_info.is_release = 1
                and room_info.id not in (
                    select room_id
                    from lease_agreement
                    where is_deleted = 0
                        and status in (2,5)
                )
                <if test="queryVo.provinceId != null">
                    and apartment_info.province_id = #{queryVo.provinceId}
                </if>
                <if test="queryVo.cityId != null">
                    and apartment_info.city_id = #{queryVo.cityId}
                </if>
                <if test="queryVo.districtId != null">
                    and apartment_info.district_id = #{queryVo.districtId}
                </if>
                <if test="queryVo.minRent != null and queryVo.maxRent != null ">
                    and (room_info.rent &gt;= #{queryVo.minRent}
                    and room_info.rent &lt;= #{queryVo.maxRent})
                </if>
                <if test="queryVo.paymentTypeId != null">
                     and room_info.id in (
                         select room_id
                         from room_payment_type
                         where is_deleted = 0
                         and payment_type_id = #{queryVo.paymentTypeId}
                     )
                 </if>
                <if test="queryVo.orderType == 'desc' or queryVo.orderType == 'asc'">
                    order by room_info.rent ${queryVo.orderType}
                </if>
            </where>
            </script>
            """)
    @Results(
            id = "roomItemVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "roomNumber", column = "room_number", javaType = String.class),
                    @Result(property = "rent", column = "rent", javaType = BigDecimal.class),
                    @Result(property = "apartmentInfo.id", column = "apartment_info_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.name", column = "name", javaType = String.class),
                    @Result(property = "apartmentInfo.introduction", column = "introduction", javaType = String.class),
                    @Result(property = "apartmentInfo.districtId", column = "district_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.districtName", column = "district_name", javaType = String.class),
                    @Result(property = "apartmentInfo.cityId", column = "city_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.cityName", column = "city_name", javaType = String.class),
                    @Result(property = "apartmentInfo.provinceId", column = "province_id", javaType = Long.class),
                    @Result(property = "apartmentInfo.provinceName", column = "province_name", javaType = String.class),
                    @Result(property = "apartmentInfo.addressDetail", column = "address_detail", javaType = String.class),
                    @Result(property = "apartmentInfo.latitude", column = "latitude", javaType = String.class),
                    @Result(property = "apartmentInfo.longitude", column = "longitude", javaType = String.class),
                    @Result(property = "apartmentInfo.phone", column = "phone", javaType = String.class),
                    @Result(property = "apartmentInfo.isRelease", column = "is_release", javaType = ReleaseStatus.class),
                    @Result(
                            property = "graphVoList",
                            column = "id",
                            javaType = List.class,
                            many = @Many(
                                    select = "com.xblteam.lease.web.app.mapper.GraphInfoMapper.getGraphVo",
                                    fetchType = FetchType.LAZY
                            )
                    ),
                    @Result(
                            property = "labelInfoList",
                            column = "id",
                            javaType = List.class,
                            many = @Many(
                                    select = "com.xblteam.lease.web.app.mapper.LabelInfoMapper.selectByRoomId",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    IPage<RoomItemVo> pageItem(Page<RoomItemVo> roomItemVoPage, @Param("queryVo") RoomQueryVo queryVo);

    @Select("select min(rent) from room_info where is_deleted = 0 and is_release = 1 and apartment_id = #{id}")
    BigDecimal selectMinRentByApartmentId(@Param("id") Long id);

    @Select("""
            select
                room_info.id,
                room_info.room_number,
                room_info.apartment_id,
                room_info.rent,
                apartment_info.id apartment_info_id,
                apartment_info.name,
                apartment_info.introduction,
                apartment_info.district_id,
                apartment_info.district_name,
                apartment_info.city_id,
                apartment_info.city_name,
                apartment_info.province_id,
                apartment_info.province_name,
                apartment_info.address_detail,
                apartment_info.latitude,
                apartment_info.longitude,
                apartment_info.phone,
                apartment_info.is_release
            from room_info
            left join apartment_info
            on apartment_id = apartment_info.id
                and apartment_info.is_deleted=0
            where room_info.is_deleted = 0
                and room_info.is_release = 1
                and room_info.apartment_id = #{id}
                and room_info.id
                not in (
                    select room_id
                    from lease_agreement
                    where is_deleted = 0
                        and status in (2,5)
                )
            """)
    @ResultMap("roomItemVo")
    IPage<RoomItemVo> pageItemByApartmentId(IPage<RoomItemVo> iPage, @Param("id") Long id);
}