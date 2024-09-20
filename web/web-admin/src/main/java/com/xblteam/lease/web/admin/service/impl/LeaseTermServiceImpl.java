package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.model.entity.LeaseTerm;
import com.xblteam.lease.web.admin.service.LeaseTermService;
import com.xblteam.lease.web.admin.mapper.LeaseTermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
    implements LeaseTermService{

    @Autowired
    LeaseTermMapper leaseTermMapper;

    @Override
    public List<LeaseTerm> queryByRoomId(Long roomId) {
        List<LeaseTerm> leaseTermList = leaseTermMapper.queryByRoomId(roomId);
        System.out.println("leaseTermList = " + leaseTermList);
        return leaseTermList;
    }
}




