package com.xblteam.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xblteam.lease.common.login.LoginUser;
import com.xblteam.lease.common.login.LoginUserHolder;
import com.xblteam.lease.common.utils.JwtUtil;
import com.xblteam.lease.model.entity.SystemUser;
import com.xblteam.lease.web.admin.mapper.SystemUserMapper;
import com.xblteam.lease.web.admin.service.SystemUserService;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public IPage<SystemUserItemVo> queryPage(long current, long size, SystemUserQueryVo queryVo) {
        IPage<SystemUserItemVo> iPage = systemUserMapper.queryPage(new Page<SystemUser>(current,size),queryVo);

        System.out.println("iPage = " + iPage);
        return iPage;
    }

    @Override
    public SystemUserItemVo getSystemUserById(Long id) {
        SystemUserItemVo systemUserItemVo = systemUserMapper.getSystemUserById(id);
        System.out.println("systemUserItemVo = " + systemUserItemVo);
        return systemUserItemVo;
    }

}




