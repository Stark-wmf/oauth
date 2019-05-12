package com.redrock.oauth.controller;

import com.redrock.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录后得到code值，在res的head里得到redirectUrl+"?"+code
     * @param redic_url
     * @param response
     * @return  1,"成功",null
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/getCode")
    public Object getCode(String redic_url, HttpServletResponse response) throws IOException, ServletException {
            return   userService.authorizationRedirect(response,redic_url);

    }

    /**
     * 用户登录后访问redis得到code判断是否过期后得到accesstoken
     * @param code
     * @param appid
     * @return  1,"access_token",acesstoken
     * @throws Exception
     */
    @RequestMapping("/accessToken")
    public Object getAccessToken(String code,String appid) throws Exception {
        return userService.getAccessToken(code,appid);
    }

    /**
     * 判断是否授权和授权是否成功后得到用户信息
     * @param accessToken
     * @param appid
     * @return  0,"access_token error",null
     *          0,"授权失败，账号不存在",null
     *          1,"获取成功",user
     * @throws Exception
     */
    @RequestMapping("/getUserInfo")
    public Object getUserInfo(String accessToken,String appid) throws Exception {
        return userService.getUserInfo(accessToken,appid);
    }
}
