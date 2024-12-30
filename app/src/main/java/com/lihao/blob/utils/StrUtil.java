package com.lihao.blob.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 字符串工具类
 *
 * @author lihao
 * &#064;date  2024/11/27--12:15
 * @since 1.0
 */
public class StrUtil {
    /**
     * 检查字符串是否包含空或者null
     * @param str 检测字符串
     * @return
     */
    public static boolean isBlank(String... str){
        for(String item : str){
            if(item == null || item.isEmpty()){
                return true;
            }
        }
        return false;
    }

    /**
     * 格式化时间
     * @param postTime 时间
     * @return
     */
    public static String formatPostTime(String postTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            Date date = inputFormat.parse(postTime);
            if (date != null) {
                // 返回正常格式化的时间
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //解析失败，返回原始时间
        return postTime;
    }
}
