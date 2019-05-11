package com.redrock.oauth.util;

public class GetUserUtil {
    private static final String appid = "123456";


    /**
     * 换取access_token
     * */
    public static Object useCode(String code){
        String url = "http://localhost:8080/oauth/user/accessToken?code="+code+"&appid="+appid;
        return HttpUtil.sendGet(url,null);
    }

    /**
     * 换取用户信息
     * */
    public static Object getUserInfo(String accessToken){
        String url = "http://localhost:8080/oauth/user/getUserInfo?accessToken="+accessToken+"&appid="+appid;
        return HttpUtil.sendGet(url,null);
    }
}
