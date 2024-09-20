package com.xblteam.lease.web.admin.Intercptor;

import com.xblteam.lease.common.login.LoginUser;
import com.xblteam.lease.common.login.LoginUserHolder;
import com.xblteam.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 处理请求之前的校验
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler
     * @return 是否放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("access-token");
        Claims claims = JwtUtil.parseToken(token);
        Long userId = claims.get("userId", Long.class);
        String username = claims.get("username", String.class);
        LoginUserHolder.setLoginUser(new LoginUser(userId, username));
        return true;
    }

    /**
     * 请求之后调用
     * 用于清理ThreadLocal中的LoginUser
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserHolder.clearLoginUser();
    }
}
