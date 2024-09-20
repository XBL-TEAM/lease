package com.xblteam.lease.web.admin.service;

import com.xblteam.lease.web.admin.vo.login.CaptchaVo;
import com.xblteam.lease.web.admin.vo.login.LoginVo;
import com.xblteam.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);

    SystemUserInfoVo info();
}
