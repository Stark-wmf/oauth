package com.redrock.oauth.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;

import java.util.UUID;

public class Oauth2
{
    public static String getCode(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getAccessToken(String code,Integer uid,String otherId ) throws Exception {
        JSONObject object = new JSONObject();
        object.put("uid",uid);
        object.put("otherId",otherId);
        return JWT.intance().createJWT(code,object.toJSONString(),7200*1000L);
    }

    public static boolean verifyAccessToken(String accessTtoken,String otherId) throws Exception {
        Claims claims =  JWT.intance().parseJWT(accessTtoken);
        JSONObject subject = JSONObject.parseObject(claims.getSubject());
        if(subject.getString("otherId").equals(otherId)){
            return true;
        }else{
            return false;
        }
    }

    public static  Integer getUserId(String accessTtoken) throws Exception {
        Claims claims =  JWT.intance().parseJWT(accessTtoken);
        JSONObject subject = JSONObject.parseObject(claims.getSubject());
        return Integer.parseInt(subject.getString("uid"));
    }
}

