package com.xujialin.Handler;

import com.alibaba.fastjson.JSON;
import com.xujialin.CommonReturnResult.ReturnResult;
import com.xujialin.CommonReturnResult.ReturnResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XuJiaLin
 * @date 2021/7/19 21:40
 */

@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ReturnResult returnResult=new ReturnResult(ReturnResultCode.ACCESS_DENY.getCode(),false, ReturnResultCode.ACCESS_DENY.getMessage());
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(returnResult));
    }
}
