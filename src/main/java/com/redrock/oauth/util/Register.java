package com.redrock.oauth.util;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
*注册加密算法
*/
public class Register {
    /**
     * 用户电话与密码实现加密,与shiro加密策略一致
     * @param username
     * @param credentials
     * @return
     */
    public static String register(String username, String credentials) {
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        return new SimpleHash("MD5", credentials, credentialsSalt, 1024).toString();
    }
}
