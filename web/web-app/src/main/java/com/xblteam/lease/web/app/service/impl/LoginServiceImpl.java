package com.xblteam.lease.web.app.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xblteam.lease.common.constant.RedisConstant;
import com.xblteam.lease.common.exception.LeaseException.LeaseException;
import com.xblteam.lease.common.result.ResultCodeEnum;
import com.xblteam.lease.common.utils.AliyunSmsUtil;
import com.xblteam.lease.common.utils.JwtUtil;
import com.xblteam.lease.model.entity.UserInfo;
import com.xblteam.lease.model.enums.BaseStatus;
import com.xblteam.lease.web.app.mapper.UserInfoMapper;
import com.xblteam.lease.web.app.service.LoginService;
import com.xblteam.lease.web.app.vo.user.LoginVo;
import com.xblteam.lease.web.app.vo.user.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private Client client;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */

    @Override
    public void getCode(String phone) {

        String key = RedisConstant.APP_LOGIN_PREFIX + phone;

        //验证是否发送过验证码
        if (stringRedisTemplate.hasKey(key)) {                                      //判断是否存在验证码
            Long ttl = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);        //获取剩余时间
            //如果一分钟内发过短信
            if (RedisConstant.APP_LOGIN_CAPTCHA_TTL_SEC - ttl < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC) {
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);    //响应验证码获取过于频繁
            }
        }

        //如果没有发过短信

        //生成验证码
        String code = AliyunSmsUtil.getRandomCode(6);

        //保存到redis
        stringRedisTemplate.opsForValue().set(
                key,                                            //key：验证码id
                code,                                           //value：验证码
                RedisConstant.APP_LOGIN_MODE_CODE_TTL_SEC,      //验证码过期时间：10
                TimeUnit.SECONDS);                              //过期时间单位：分钟

        //发送验证码
        AliyunSmsUtil.sendcode(client, phone, code);
    }

    @Override
    public String login(LoginVo loginVo) {

        //输入为空 或 手机号为空
        if (loginVo == null || loginVo.getPhone() == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }

        //验证码为空
        if (loginVo.getCode() == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }

        //查询验证码
        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getPhone();
        String code = stringRedisTemplate.opsForValue().get(key);

        //验证码为空
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }

        //验证码错误
        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }

        //查询用户信息
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getPhone, loginVo.getPhone()));

        //用户不存在
        if (userInfo == null) {

            //进行注册
            userInfo = new UserInfo(
                    loginVo.getPhone(),
                    null,
                    null,
                    "user_" + loginVo.getPhone().substring(7, 11),
                    BaseStatus.ENABLE);
            userInfoMapper.insert(userInfo);

            //查询id
            UserInfo newUser = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getPhone, userInfo.getPhone()));

            //生成token
            return JwtUtil.createToken(newUser.getId(), newUser.getPhone());
        }

        //用户已存在

        //用户如果被禁用
        if (userInfo.getStatus() == BaseStatus.DISABLE) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }


        //登录成功,删除验证码缓存，使验证码有效性为一次
        stringRedisTemplate.delete(key);

        //生成token
        return JwtUtil.createToken(userInfo.getId(), userInfo.getPhone());
    }

    @Override
    public UserInfoVo getLoginUserById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        return new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
    }
}
