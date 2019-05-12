package com.redrock.oauth.controller;


import com.redrock.oauth.service.UserService;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RedirectController {
private UserService userService;

    /**
     * 第三方登录接口
     * @param code
     * @return  0,"code 错误或过期",null  或者  1,"user_info",用户信息
     */
    @RequestMapping("/redirectlogin")
    public Object redirectlogin(String code){
        return   userService.redirectlogin(code);
    }
}
