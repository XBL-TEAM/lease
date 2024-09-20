package com.xblteam.lease.common.utils;

import com.xblteam.lease.common.exception.LeaseException.LeaseException;
import com.xblteam.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import java.util.Date;

public class JwtUtil {

    private static final String secretKey = "THE_KEY_SIZE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_THE_HASH_OUTPUT_SIZE";

    /**
     * 创建token
     * @param userId
     * @param username
     * @return
     */
    public static String createToken(Long userId, String username) {

        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))  //过期时间1小时
                .setSubject("LOGIN_USER")
                .claim("userId",userId)
                .claim("username",username)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseToken(String token) {

        if (token == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);      //token为空，未登录
        }

        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);         //token已过期
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);         //token非法
        }
    }

}
