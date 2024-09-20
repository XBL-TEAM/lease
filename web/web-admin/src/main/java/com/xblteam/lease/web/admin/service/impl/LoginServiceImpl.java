package com.xblteam.lease.web.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import com.xblteam.lease.common.constant.RedisConstant;
import com.xblteam.lease.common.exception.LeaseException.LeaseException;
import com.xblteam.lease.common.login.LoginUser;
import com.xblteam.lease.common.login.LoginUserHolder;
import com.xblteam.lease.common.result.ResultCodeEnum;
import com.xblteam.lease.common.utils.JwtUtil;
import com.xblteam.lease.model.entity.SystemUser;
import com.xblteam.lease.model.enums.BaseStatus;
import com.xblteam.lease.web.admin.mapper.SystemUserMapper;
import com.xblteam.lease.web.admin.service.LoginService;
import com.xblteam.lease.web.admin.vo.login.CaptchaVo;
import com.xblteam.lease.web.admin.vo.login.LoginVo;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {

        //生成验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);         //生成验证码图片
        String code = specCaptcha.text().toLowerCase();                                 //转字符串
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();              //生成图片对应的key

        //保存到redis
        stringRedisTemplate.opsForValue().set(
                key,
                code,
                RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC,
                TimeUnit.SECONDS                                    //验证码有效期60s
        );

        return new CaptchaVo(specCaptcha.toBase64(), key);          //转字符串
    }

    @Override
    public String login(LoginVo loginVo) {

        //未输入验证码
        if(loginVo.getCaptchaCode() == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        //查询验证码
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());

        //验证码已过期
        if(code == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        //验证码错误
        if(!loginVo.getCaptchaCode().toLowerCase().equals(code)){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        //查询用户
        SystemUser systemUser = systemUserMapper.selectByUsername(loginVo.getUsername());

        //用户不存在
        if(systemUser == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        //账号被禁用
        if(systemUser.getStatus() == BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        //用户名或密码不正确
        if(!systemUser.getPassword()
                .equals(DigestUtils.md5Hex(loginVo.getPassword()))){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //创建token并返回
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
    }

    @Override
    public SystemUserInfoVo info() {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        SystemUser systemUser = systemUserMapper.selectById(loginUser.getUserId());
        return new SystemUserInfoVo(systemUser.getName(),systemUser.getAvatarUrl());
    }
}
