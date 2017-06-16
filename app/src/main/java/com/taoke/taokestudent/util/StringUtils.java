package com.taoke.taokestudent.util;

import com.taoke.taokestudent.common.Consts;

/**
 * 字符串工具类
 */
public class StringUtils {

    public static String changeImageUrl(String str) {
        if (str.indexOf("/Public/") != -1 && str.indexOf(Consts.NetUrl.BASE_URL + "Public/") == -1) {
            str = str.replace("/Public/", Consts.NetUrl.BASE_URL + "Public/");
        }
        return str;
    }
}
