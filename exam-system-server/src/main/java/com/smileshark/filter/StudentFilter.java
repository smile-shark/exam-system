package com.smileshark.filter;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.Result;
import com.smileshark.entity.user.Student;
import com.smileshark.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/student/*")
public class StudentFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().contains("signup") || request.getRequestURI().contains("login")) {
            // 如果是登录和注册
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            // 如果不是登录和注册
            Student student = JwtUtils.parseJwt(request.getHeader("StudentToken"), Student.class);
            if (student != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                // 如果token解析失败
                response.getWriter().write(JSONObject.toJSONString(
                        Result.error().setMsg("请重新登录")
                ));
            }
        }
    }
}
