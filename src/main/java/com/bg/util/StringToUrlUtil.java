package com.bg.util;

/**
 * Created by Administrator on 2016/7/19.
 */
public class StringToUrlUtil {

    public static String buildUrl(String url, String content) {
        return "<a href='" + url + "'>" + content + "</a>";
    }
}
