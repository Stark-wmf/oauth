package com.redrock.oauth.intercpter;

import com.alibaba.fastjson.JSONObject;
import com.redrock.oauth.entry.Response;
import com.redrock.oauth.entry.User;
import com.redrock.oauth.util.JWT;
import com.redrock.oauth.util.UserContextUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IntercpterLogin implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(IntercpterLogin.class);
    /**
     * redis
     *
     * */
    @Autowired
    RedisTemplate redisTemplate;
    /**
     *  token ,用户登录标识
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse res, Object o) throws Exception {
        String token = request.getHeader("token");
        res.setContentType("application/json;charset=utf-8");
        if(token == null){
            Response response = new Response();
            response.setMessage("没有携带登录密钥");
            response.setCode(0);
            res.getWriter().write(JSONObject.toJSONString(response));
            return false;
        }

        Claims clams =  JWT.intance().parseJWT(token);
        if(clams != null) {
            JSONObject userInfo = JSONObject.parseObject(clams.getSubject());
            User user = new User();
            user.setStatus(userInfo.getString("status"));
            user.setUsername(userInfo.getString("name"));
            user.setUserid(Integer.parseInt(userInfo.getString("uid")));
            UserContextUtil.addUser(user);
            return true;
        }else{
            Response response = new Response();
            response.setMessage("登录过期");
            response.setCode(0);
            res.getWriter().write(JSONObject.toJSONString(response));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        UserContextUtil.clear();
    }
}