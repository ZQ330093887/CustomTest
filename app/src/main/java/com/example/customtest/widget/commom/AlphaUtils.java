package com.example.customtest.widget.commom;

/**
 * Created by ZQiong on 2018/9/5.
 */

public class AlphaUtils {
    public static void main(String[] args) {
        System.out.println("透明度百分比对应的十六进制:");
        for (int i = 0; i <= 100; i++) {
            float temp = 255 * i * 1.0f / 100f;
            int round = Math.round(temp);//四舍五入
            String hexString = Integer.toHexString(round);
            if (hexString.length() < 2) {
                hexString += "0";
            }
            System.out.println("    百分比:" + i + "%" + "  HEX: " + hexString.toUpperCase());
        }
    }
}