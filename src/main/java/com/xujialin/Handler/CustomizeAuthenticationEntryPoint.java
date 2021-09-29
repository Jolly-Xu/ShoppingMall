package com.xujialin.Handler;

import com.alibaba.fastjson.JSON;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.CommonReturnResult.ReturnResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XuJiaLin
 * @date 2021/8/4 9:33
 */

@Slf4j
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ReturnResult returnResult=new ReturnResult(ReturnResultCode.LOGIN_FRIST.getCode(),false,null);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(returnResult));
    }
}
