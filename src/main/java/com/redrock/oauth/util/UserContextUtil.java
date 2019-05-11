package com.redrock.oauth.util;

import com.redrock.oauth.entry.User;

public class UserContextUtil {
    private static ThreadLocal<User> entrySet = new ThreadLocal<>();

    /**
     * 这个用户信息set集合插入User
     */
    public static void addUser(User user) {
        entrySet.set(user);
    }

    /**
     * 获取用户信息
     */
    public static Object getUserInfor() {
        return entrySet.get();
    }

    /**
     * 获取用户ID
     */
    public static Integer getUserId() {
        return entrySet.get().getUserid();
    }

    /**
     * 清除本次请求连接
     */
    public static void clear() {
        entrySet.remove();
    }

    /**
     * 直接获取 user 实例
     */

    public static User getUser() {
        return entrySet.get();
    }
}
