package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xblteam.lease.model.entity.ApartmentInfo;
import com.xblteam.lease.model.entity.RoomInfo;
import com.xblteam.lease.model.enums.ReleaseStatus;
import com.xblteam.lease.web.admin.vo.room.RoomItemVo;
import com.xblteam.lease.web.admin.vo.room.RoomQueryVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.RoomInfo
 */
@Mapper
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    /**
     * 根据公寓id查询所有房间信息
     *
     * @param apartmentId 公寓id
     * @return 数量
     */
    @Select("select count(*) from room_info where apartment_id = #{apartmentId} and is_deleted = 0;")
    Long countAllByApartmentId(@Param("apartmentId") Long apartmentId);

    /**
     * 根据条件分页查询房间列表
     *
     * @param page
     * @param roomQueryVo
     * @return
     */
    @Select("""
            <script>
            select
                room_info.id,
                room_info.room_number,
                room_info.rent,
                room_info.apartment_id,
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
            from
                room_info
            left join
                apartment_info
                on
                    apartment_info.id = room_info.apartment_id
                    and apartment_info.is_deleted = 0
                    and apartment_info.is_release = 1
            <where>
                    room_info.is_deleted = 0
                and room_info.is_release = 1
                <if test="roomQueryVo.districtId != null">
                    and apartment_info.district_id = #{roomQueryVo.districtId}
                </if>
                <if test="roomQueryVo.cityId != null">
                    and apartment_info.city_id = #{roomQueryVo.cityId}
                </if>
                <if test="roomQueryVo.provinceId != null">
                    and apartment_info.province_id = #{roomQueryVo.provinceId}
                </if>
                <if test="roomQueryVo.apartmentId != null">
                    and apartment_id = #{roomQueryVo.apartmentId}
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
                    @Result(property = "apartmentId", column = "apartment_id", javaType = Long.class),
                    @Result(property = "isRelease", column = "is_release", javaType = ReleaseStatus.class),
                    @Result(property = "apartmentInfo.id", column = "apartment_id", javaType = Long.class),
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
                            property = "leaseEndDate",
                            column = "id",
                            javaType = Date.class,
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.LeaseAgreementMapper.queryLeaseEndDateByRoomId",
                                    fetchType = FetchType.LAZY
                            )
                    ),
                    @Result(
                            property = "isCheckIn",
                            column = "id",
                            javaType = Boolean.class,
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.LeaseAgreementMapper.queryCheckInStatusByRoomId",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    IPage<RoomItemVo> pageItem(IPage<RoomItemVo> page, @Param("roomQueryVo") RoomQueryVo roomQueryVo);
}




