package com.redrock.oauth.controller;

import com.redrock.oauth.entry.CommonException;
import com.redrock.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 测试hello
     * @return
     */
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(Model model,String username) {
        model.addAttribute("name", username);
        return "/hello";
    }


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
        try {
            userService.authorizationRedirect(response,redic_url);
        }catch (IOException e){
            log.error("IO异常={}",e.getMessage(),e);
            throw new CommonException(40001,"IO错误");
        }catch (ServletException x){
            log.error("Servlet异常={}",x.getMessage(),x);
            throw new CommonException(40002,"连接或服务器错误");
        }

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

        try {
            userService.getAccessToken(code,appid);
        }catch (Exception e){
            log.error("获取Acesstoken异常={}",e.getMessage(),e);

            throw new CommonException(500,"内部错误");
        }
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
    public Object getUserInfo(String accessToken, String appid) throws Exception {
        try {
            userService.getUserInfo(accessToken,appid);

        }catch (Exception e){
            log.error("获取用户信息异常={}",e.getMessage(),e);

            throw new CommonException(500,"内部错误");
        }
        return userService.getUserInfo(accessToken,appid);
    }
}
