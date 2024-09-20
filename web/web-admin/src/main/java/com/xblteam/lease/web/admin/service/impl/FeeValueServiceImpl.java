package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.FeeValue;
import com.xblteam.lease.web.admin.service.FeeValueService;
import com.xblteam.lease.web.admin.mapper.FeeValueMapper;
import com.xblteam.lease.web.admin.vo.fee.FeeValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FeeValueServiceImpl extends ServiceImpl<FeeValueMapper, FeeValue>
    implements FeeValueService{

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Override
    public List<FeeValueVo> queryByApartmentId(Long apartmentId) {
        List<FeeValueVo> feeValueVos = feeValueMapper.queryByApartmentId(apartmentId);
        System.out.println("feeValueVos = " + feeValueVos);
        return feeValueVos;
    }
}




