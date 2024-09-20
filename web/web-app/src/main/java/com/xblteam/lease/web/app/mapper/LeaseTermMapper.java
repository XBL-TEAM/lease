package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.LeaseTerm
*/
@Mapper
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    @Select("""
                select id,
                       month_count,
                       unit
                from lease_term
                where is_deleted = 0
                  and id in (select lease_term_id
                             from room_lease_term
                             where is_deleted = 0
                               and room_id = #{id})
            """)
    List<LeaseTerm> selectListByRoomId(@Param("id") Long id);
}




