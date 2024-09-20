package com.xblteam.lease.common.login;

public class LoginUserHolder {

    /**
     * 本地线程容器
     */
    private static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    /**
     * 将变量存储进线程中
     * @param user LoginUser
     */
    public static void setLoginUser(LoginUser user) {
        threadLocal.set(user);
    }

    /**
     * 从线程中获取变量信息
     * @return LoginUser
     */
    public static LoginUser getLoginUser() {
        return threadLocal.get();
    }

    /**
     * 清楚线程中的变量
     */
    public static void clearLoginUser() {
        threadLocal.remove();
    }
}
