package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xblteam.lease.model.LeaseTerm
*/
@Mapper
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    @Select("""
            select
                lease_term.id,
                lease_term.month_count,
                lease_term.unit
            from
                room_lease_term,
                lease_term
            where
                    room_lease_term.room_id = #{roomId}
                and room_lease_term.lease_term_id = lease_term.id
                and room_lease_term.is_deleted = 0
                and lease_term.is_deleted = 0
            """)
    List<LeaseTerm> queryByRoomId(@Param("roomId") Long roomId);

}




