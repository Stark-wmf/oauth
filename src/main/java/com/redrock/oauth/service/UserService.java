package com.redrock.oauth.service;
import com.alibaba.fastjson.JSONObject;
import com.redrock.oauth.util.GetUserUtil;
import com.redrock.oauth.util.JWT;
import com.redrock.oauth.util.UserContextUtil;
import org.springframework.data.redis.core.RedisTemplate;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redrock.oauth.entry.Response;
import com.redrock.oauth.entry.User;

import com.redrock.oauth.mapper.UserMapper;
import com.redrock.oauth.util.Oauth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class UserService {
    @Autowired
     private UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;

@Transactional
    public Object login(String username,String pwd) throws Exception {
        User user = userMapper.getPassword(username);

        if(null == user){
            return  new Response();
        }
        if(user.getPassword().equals(pwd)){
            JSONObject subject = new JSONObject();
            subject.put("uid",user.getUserid());
            subject.put("name",user.getUsername());
            subject.put("status",user.getStatus());
            return new Response(1,"登录成功", JWT.intance().createJWT(UUID.randomUUID().toString(),subject.toJSONString() ,7200*1000L));
        }else {
            return new Response(0,"登录失败",null);
        }
    }


    @Transactional
    public Object insertUser(User user) throws Exception {
        int insert = userMapper.register(user);
    Response response=new Response();
        JSONObject subject = new JSONObject();
        subject.put("uid",user.getUserid());
        subject.put("name",user.getUsername());
        subject.put("status",user.getStatus());
        if (insert != 0) {
            response.setCode(1);
            response.setMessage("注册成功");
            response.setData(JWT.intance().createJWT(UUID.randomUUID().toString(),subject.toJSONString(),7200*1000L));
        } else {
            response.setCode(0);
            response.setMessage("注册失败");
            response.setData(null);
        }
        return response;
    }

    public Object redirectlogin(String code) {
        JSONObject subject =  JSONObject.parseObject(GetUserUtil.useCode(code).toString());
        String accessRoken = subject.getString("data");
        if(accessRoken == null){
            Response response = new Response(0,"code 错误或过期",null);
            return response;
        }
        JSONObject sub = JSONObject.parseObject(GetUserUtil.getUserInfo(accessRoken).toString());
        Response response = new Response(1,"user_info",sub);
        return response;
    }

    public Object getUserInfo(String access_token,String appid) throws Exception {
        if(!Oauth2.verifyAccessToken(access_token,appid)){
            return new Response(0,"access_token error",null);
        }
        User user = userMapper.getUser(Oauth2.getUserId(access_token));
        if(user == null){
            return new Response(0,"授权失败，账号不存在",null);
        }
        user.setPassword(null);
        return new Response(1,"获取成功",user);
    }

    public Object getAccessToken(String code,String appid) throws Exception {
        Integer uid = (Integer) redisTemplate.opsForValue().get(code);
        if(uid == null){
            System.out.println("code 错误或过期");
            return new Response(0,"code error or to exceed the time limit",null);
        }
        redisTemplate.expire(code,0, TimeUnit.SECONDS);
        return  new Response(1,"access_token",Oauth2.getAccessToken(code,uid,appid));
    }

    public Object authorizationRedirect(HttpServletResponse res, String redirectUrl) throws IOException, ServletException {
        String code = Oauth2.getCode();
        System.out.println("授权："+ UserContextUtil.getUserId());
        redisTemplate.opsForValue().set(code, UserContextUtil.getUserId(),60, TimeUnit.MINUTES);
        res.setHeader("redic_url",redirectUrl+"?"+code);
        return new Response(1,"成功",null);
    }
}
