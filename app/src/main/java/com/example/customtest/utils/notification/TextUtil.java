package com.example.customtest.utils.notification;

/**
 * File: TextUtil.java
 * Author: chenyihui
 * Date: 2018/12/17
 */
public class TextUtil {
    /**
     * 字符串不为空时返回True，否则返回false
     *
     * @param content
     * @return
     */
    public static boolean isValidate(String content) {
        return content != null && !"".equals(content.trim()) && !"null".equals(content.trim());
    }

    /**
     * byte[]不为空时返回True，否则返回false
     *
     * @param content
     * @return
     */
    public static boolean isValidate(byte[] content) {
        return content != null && content.length > 0;
    }

    public static boolean isValidate(long[] content) {
        return content != null && content.length > 0;
    }
}
