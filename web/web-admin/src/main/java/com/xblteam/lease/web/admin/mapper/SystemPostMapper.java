package com.xblteam.lease.web.admin.mapper;

import com.xblteam.lease.model.entity.SystemPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author liubo
* @description 针对表【system_post(岗位信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xblteam.lease.model.SystemPost
*/
@Mapper
public interface SystemPostMapper extends BaseMapper<SystemPost> {

    @Select("select name from system_post where id = #{postId}")
    String queryNameById(@Param("postId") Long postId);

}




