package com.lihao.blob.utils;

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
     * @param str
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
}
