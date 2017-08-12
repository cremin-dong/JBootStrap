package com.dmm.common.utils;

import com.dmm.module.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;

/**
 * Created by cremin on 2017/8/11.
 */
public class UserUtils {

    /**
     * 获取当前用户的ID
     * @return
     */
    public static String getCurrUserId(){
        return  getCurrUser().getId();
    }

    /**
     * 获取当前用户
     * @return
     */
    public static User getCurrUser(){
        return (User) SecurityUtils.getSubject().getSession().getAttribute("currUser");
    }

    /**
     * 获取用户默认密码
     * @param username
     * @return
     */
    public static String getUserDefaultPassword(String username){
        return new SimpleHash("MD5", User.DEFAULT_PASSWORD, username,2).toString();
    }
}
