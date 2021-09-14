package com.xujialin.Handler;

import com.alibaba.fastjson.JSON;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.CommonReturnResult.ReturnResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XuJiaLin
 * @date 2021/9/13 21:56
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        ReturnResult returnResult = new ReturnResult(ReturnResultCode.LOGIN_SUCCESS.getCode(),true, null);
        httpServletResponse.getWriter().write(JSON.toJSONString(returnResult));
    }
}
