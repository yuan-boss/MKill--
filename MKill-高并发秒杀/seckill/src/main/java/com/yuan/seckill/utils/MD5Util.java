package com.yuan.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @module:
 * @description:
 * @author: yuan_boss
 * @create: 2022-08-11 14:07
 **/
public class MD5Util {


    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "yuanBoss";

    //第一次加密
    public static String inputPassToFromPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //第二次加密
    public static String fromPassToDBPass(String formPass,String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        //09a5656540c8a9567ca8ced2cdd58159
        System.out.println(inputPassToFromPass("123456"));
        //af279ed7f0930dcef9b637889603876d
        System.out.println(fromPassToDBPass("35dceab561dcc6be77ad3267536b1b5e","yuanBoss"));
        //af279ed7f0930dcef9b637889603876d
        System.out.println(inputPassToDBPass("123456","yuanBoss"));

    }
}
