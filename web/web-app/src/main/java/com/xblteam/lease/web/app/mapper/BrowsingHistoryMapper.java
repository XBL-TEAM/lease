package com.xblteam.lease.web.app.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.model.entity.BrowsingHistory;
import com.xblteam.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.BrowsingHistory
*/
@Mapper
public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {

    @Select("""
            select browsing_history.id,
                   browsing_history.user_id,
                   browsing_history.room_id,
                   browsing_history.browse_time,
                   room_info.room_number,
                   room_info.rent,
                   apartment_info.name apartment_name,
                   apartment_info.district_name,
                   apartment_info.city_name,
                   apartment_info.province_name
            from browsing_history
                     left join room_info
                        on browsing_history.room_id = room_info.id
                        and room_info.is_deleted=0
                     left join apartment_info
                        on room_info.apartment_id = apartment_info.id
                        and apartment_info.is_deleted=0
            where browsing_history.is_deleted = 0
              and browsing_history.user_id = #{userId}
            order by browse_time
            desc
            """)
    @Results(
            id = "historyItemVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "userId", column = "user_id", javaType = Long.class),
                    @Result(property = "roomId", column = "room_id", javaType = Long.class),
                    @Result(property = "browseTime", column = "browse_time", javaType = Date.class),
                    @Result(property = "roomNumber", column = "room_number", javaType = String.class),
                    @Result(property = "rent", column = "rent", javaType = BigDecimal.class),
                    @Result(property = "apartmentName", column = "apartment_name", javaType = String.class),
                    @Result(property = "provinceName", column = "province_name", javaType = String.class),
                    @Result(property = "cityName", column = "city_name", javaType = String.class),
                    @Result(property = "districtName", column = "district_name", javaType = String.class),
                    @Result(
                            property = "roomGraphVoList",
                            column = "room_id",
                            javaType = List.class,
                            many = @Many(
                                    select = "com.xblteam.lease.web.app.mapper.GraphInfoMapper.getGraphVo",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    IPage<HistoryItemVo> pageHistoryItemByUserId(Page<HistoryItemVo> historyItemVoPage, @Param("userId") Long userId);
}




