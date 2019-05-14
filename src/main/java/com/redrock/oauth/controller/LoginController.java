package com.redrock.oauth.controller;

import com.redrock.oauth.entry.CommonException;
import com.redrock.oauth.entry.User;
import com.redrock.oauth.service.UserService;
import com.redrock.oauth.util.Register;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
public class LoginController {
@Autowired
private UserService userService;
    /**
     * 登陆注册接口
     *
     */
    @RequestMapping("/register")
    public Object register(String username,String password,String status) throws Exception {
        User user =new User();
        user.setStatus(status);
        user.setUsername(username);
        user.setPassword(password);
        try {
            userService.insertUser(user);
        }catch (Exception e){
            log.error("用户注册异常={}",e.getMessage(),e);
            throw new CommonException(500,"内部错误");
        }
        return userService.insertUser(user);
    }

    @RequestMapping("/login")
    public Object login(String username,String pwd) throws Exception {
        try {
            userService.login(username,pwd);
        }catch (Exception e){
            log.error("用户登录异常={}",e.getMessage(),e);
            throw new CommonException(500,"内部错误");
        }
        return userService.login(username,pwd);
    }

}
