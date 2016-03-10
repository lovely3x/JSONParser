package com.lovely3x.jsonparser.utils;

import com.lovely3x.jsonparser.JSONType;

/**
 * Created by lovely3x on 15-8-8.
 * 放一些工具方法进来
 */
public class CommonUtils {

    /**
     * 猜测一个字符串的类型
     *
     * @param value 需要猜测的字符串
     * @return 猜测的类型 {@link JSONType}
     */
    public static int guessType(String value) {

        int type = JSONType.JSON_TYPE_INVALIDATE;
        //无效的值
        if (value == null || value.trim().length() == 0) {
            return type;
        }
        value = value.trim();
        if (value.startsWith("[") && value.endsWith("]")) {//json array
            type = JSONType.JSON_TYPE_ARRAY;
        } else if (value.startsWith("{") && value.endsWith("}")) {//json object
            type = JSONType.JSON_TYPE_OBJECT;
        } else if (value.matches("^[\\+|\\-]?\\d+\\.\\d+([e|E]{1}[\\+|\\-]?\\d+)?$")) {//浮点数匹配
            if (Double.parseDouble(value) > Float.MAX_VALUE / 2) {
                type = JSONType.JSON_TYPE_DOUBLE;//double float
            } else {
                type = JSONType.JSON_TYPE_FLOAT;//single float
            }
        } else if (value.matches("^[\\+|\\-]?\\d+$")) {//整形匹配
            if (Long.parseLong(value) > Integer.MAX_VALUE) {
                type = JSONType.JSON_TYPE_LONG;//long int
            } else {
                type = JSONType.JSON_TYPE_INT;// int
            }
        } else if (value.matches("^(true|false)$")) {
            type = JSONType.JSON_TYPE_BOOLEAN;
        } else if (value.matches("^null$")) {
            type = JSONType.JSON_TYPE_NULL;//null
        } else {//string
            type = JSONType.JSON_TYPE_STRING;
        }
        return type;
    }


    /**
     * 将tab键替换为 \t
     *
     * @param string 需要替换的字符串
     * @return 替换完成的字符串
     */
    public static String replaceTab(String string) {
        return string.replaceAll("\\t", "\\\\t");
    }

    /**
     * 将回车键替换为 \r
     *
     * @param string 需要替换的字符串
     * @return 替换完成的字符串
     */
    public static String replaceEnter(String string) {
        return string.replaceAll("\\r", "\\\\r");
    }

    /**
     * 将换行键替换为 \n
     *
     * @param string 需要替换的字符串
     * @return 替换完成的字符串
     */
    public static String replaceNewLine(String string) {
        return string.replaceAll("\\n", "\\\\n");
    }

    /**
     * 替换特殊的空白字符为可见字符
     *
     * @param string 需要替换的字符串
     * @return 替换完成的字符串
     */
    public static String replaceSpaceCharToVisibleChar(String string) {
        return replaceNewLine(replaceEnter(replaceTab(string)));
    }

    /**
     * 替换可见的特殊字符标记为不可见的空白字符
     *
     * @param string 需要替换的字符
     * @return 替换完成的字符
     */
    public static String replaceVisibleChatToInvisibleSpaceChar(String string) {
        return string.replaceAll("\\\\t", "\t").replaceAll("\\\\r", "\r").replaceAll("\\\\n", "\n");
    }

}
