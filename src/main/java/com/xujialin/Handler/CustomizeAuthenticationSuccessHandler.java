package com.xujialin.Handler;

import com.alibaba.fastjson.JSON;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.CommonReturnResult.ReturnResultCode;
import com.xujialin.SafetyVerification.MyUserDetails;
import com.xujialin.entity.Userinfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XuJiaLin
 * @date 2021/9/13 21:56
 */
@Slf4j
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        MyUserDetails principal = (MyUserDetails)authentication.getPrincipal();
        Userinfo user = principal.getUser();
        user.setPassword(null);
        user.setAuthorityId(null);
        user.setUpdatatime(null);
        user.setCreatetime(null);
        user.setIsLogicDelete(null);
        log.info("{}",user);
        ReturnResult returnResult = new ReturnResult(ReturnResultCode.LOGIN_SUCCESS.getCode(),true, user);
        httpServletResponse.getWriter().write(JSON.toJSONString(returnResult));
    }
}
