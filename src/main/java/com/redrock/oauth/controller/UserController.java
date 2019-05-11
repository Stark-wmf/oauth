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
    @RequestMapping("/getCode")
    public Object getCode(String redic_url, HttpServletResponse response) throws IOException, ServletException {
            return   userService.authorizationRedirect(response,redic_url);

    }
    @RequestMapping("/accessToken")
    public Object getAccessToken(String code,String appid) throws Exception {
        return userService.getAccessToken(code,appid);
    }

    @RequestMapping("/getUserInfo")
    public Object getUserInfo(String accessToken,String appid) throws Exception {
        return userService.getUserInfo(accessToken,appid);
    }
}
