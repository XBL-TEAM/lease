package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xblteam.lease.model.entity.SystemUser;
import com.xblteam.lease.model.enums.BaseStatus;
import com.xblteam.lease.model.enums.SystemUserType;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xblteam.lease.model.SystemUser
*/
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    @Select("""
            <script>
            select *
            from system_user
            <where>
                is_deleted = 0
                <if test="queryVo.name != null and queryVo.name != ''">
                    and system_user.name = #{queryVo.name}
                </if>
                <if test="queryVo.phone != null and queryVo.phone != ''">
                    and system_user.phone = #{queryVo.phone}
                </if>
            </where>
            </script>
            """)
    @Results(
            id = "systemUserItemVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "username", column = "username", javaType = String.class),
                    @Result(property = "password", column = "password", javaType = String.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "type", column = "type", javaType = SystemUserType.class),
                    @Result(property = "phone", column = "phone", javaType = String.class),
                    @Result(property = "avatarUrl", column = "avatar_url", javaType = String.class),
                    @Result(property = "additionalInfo", column = "additional_info", javaType = String.class),
                    @Result(property = "postId", column = "post_id", javaType = Long.class),
                    @Result(property = "status", column = "status", javaType = BaseStatus.class),
                    @Result(
                            property = "postName",
                            column = "post_id",
                            javaType = String.class,
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.SystemPostMapper.queryNameById",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    IPage<SystemUserItemVo> queryPage(Page<SystemUser> systemUserPage, @Param("queryVo") SystemUserQueryVo queryVo);

    @Select("select * from system_user where is_deleted = 0 and system_user.id = #{id}")
    @ResultMap("systemUserItemVo")
    SystemUserItemVo getSystemUserById(@Param("id") Long id);

    @Select("select * from system_user where username = #{username} and is_deleted = 0")
    SystemUser selectByUsername(@Param("username") String username);
}




