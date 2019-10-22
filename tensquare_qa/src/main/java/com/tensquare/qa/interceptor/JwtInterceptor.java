package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("----经过了拦截器~~~~");

        /* 具体的操作
         *  拦截器只是负责把请求头中包含token的令牌进行一个解析操作
         *  */
        String header = request.getHeader("Authorization");
        // 判断请求头中的token
        if (header == null || "".equals(header)) {
            throw new RuntimeException("权限不足");
        }
        if (!header.startsWith("Bearer ")) {
            throw new RuntimeException("权限不足");
        }
        if (header != null && !"".equals(header)) {
            // 包含header头信息，对其进行解析
            if (header.startsWith("Bearer ")) {
                // 获取token
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && roles.equals("admin")) {
                        request.setAttribute("claims_admin", token);
                    }
                    if (roles != null && roles.equals("user")) {
                        request.setAttribute("claims_user", token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确");
                }
            }
        }

        return true;
    }
}
