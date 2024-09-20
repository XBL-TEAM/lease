package com.xblteam.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xblteam.lease.model.entity.FeeValue;
import com.xblteam.lease.web.admin.vo.fee.FeeValueVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.xblteam.lease.model.FeeValue
 */
@Mapper
public interface FeeValueMapper extends BaseMapper<FeeValue> {

    /**
     * 查询所有杂费信息
     *
     * @param id
     * @return
     */
    @Select("select * from fee_value where fee_key_id = #{id} and is_deleted = 0")
    List<FeeValue> findById(@Param("id") Integer id);

    /**
     * 根据公寓id查询杂费值列表
     *
     * @param apartmentId 公寓id
     * @return 杂费值列表
     */
    @Select("""
            select distinct
                fee_value.id,
                fee_value.name,
                fee_value.unit,
                fee_value.fee_key_id
            from
                apartment_fee_value,
                fee_value
            where apartment_fee_value.apartment_id = #{apartmentId}
                and apartment_fee_value.fee_value_id = fee_value.id
                and apartment_fee_value.is_deleted = 0
                and fee_value.is_deleted = 0
            order by fee_value.id
            """)
    @Results(
            id = "feeValueVo",
            value = {
                    @Result(id = true, property = "id", column = "id", javaType = Long.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "unit", column = "unit", javaType = String.class),
                    @Result(property = "feeKeyId", column = "fee_key_id", javaType = Long.class),
                    @Result(
                            property = "feeKeyName",
                            column = "fee_key_id",
                            one = @One(
                                    select = "com.xblteam.lease.web.admin.mapper.FeeKeyMapper.queryNameById",
                                    fetchType = FetchType.LAZY
                            )
                    )
            }
    )
    List<FeeValueVo> queryByApartmentId(@Param("apartmentId") Long apartmentId);
}




