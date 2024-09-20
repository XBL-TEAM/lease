package com.xblteam.lease.web.app.mapper;

import com.xblteam.lease.model.entity.FeeValue;
import com.xblteam.lease.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.atguigu.lease.model.entity.FeeValue
*/
@Mapper
public interface FeeValueMapper extends BaseMapper<FeeValue> {

    @Select("""
            select fv.id,
                   fv.name,
                   fv.unit,
                   fv.fee_key_id,
                   fk.name fee_key_name
            from fee_value fv
            left join fee_key fk
                on fv.fee_key_id = fk.id
                and fk.is_deleted = 0
            where fv.is_deleted = 0
              and fv.id in (
                select fee_value_id
                from apartment_fee_value
                where is_deleted = 0
                  and apartment_id = #{id}
              )
            """)
    List<FeeValueVo> selectListByApartmentId(Long apartmentId);
}




