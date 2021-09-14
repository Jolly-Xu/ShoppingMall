package com.xujialin.Handler;

import com.alibaba.fastjson.JSON;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.CommonReturnResult.ReturnResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XuJiaLin
 * @date 2021/7/18 21:46
 * 处理匿名用户访问保护数据
 */

@Slf4j
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ReturnResult returnResult=new ReturnResult(ReturnResultCode.LOGIN_ERROR.getCode() ,false,ReturnResultCode.LOGIN_ERROR.getMessage());
        httpServletResponse.setContentType("text/json;charset=utf-8");
        log.info("登录失败");
        httpServletResponse.getWriter().write(JSON.toJSONString(returnResult));
    }
}
