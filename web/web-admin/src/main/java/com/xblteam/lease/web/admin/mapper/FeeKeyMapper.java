package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xblteam.lease.model.entity.FeeKey;
import com.xblteam.lease.web.admin.vo.fee.FeeKeyVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【fee_key(杂项费用名称表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.FeeKey
 */
@Mapper
public interface FeeKeyMapper extends BaseMapper<FeeKey> {


    @Select("select * from fee_key where is_deleted = 0 ")
    @Results(
            id = "feeKeyVo",
            value = {
                    @Result(
                            property = "id",
                            column = "id",
                            id = true,
                            javaType = Long.class
                    ),
                    @Result(
                            property = "name",
                            column = "name",
                            javaType = String.class
                    ),
                    @Result(
                            property = "feeValueList",
                            column = "id",      // 用 fee_key 中的 id 字段去 fee_value 表中查 attr_key_id 字段
                            many = @Many(
                                    select = "com.xblteam.lease.web.admin.mapper.FeeValueMapper.findById",  //查询方法
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    List<FeeKeyVo> findAll();


    @Select("""
            select distinct
                fee_key.name
            from
                fee_key,
                fee_value
            where fee_key.id = #{feeKeyId}
                and fee_key.id = fee_value.fee_key_id
                and fee_key.is_deleted = 0;
            """)
    String queryNameById(@Param("feeKeyId") Long feeKeyId);
}




