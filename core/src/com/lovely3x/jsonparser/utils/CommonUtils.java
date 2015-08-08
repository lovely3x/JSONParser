package com.lovely3x.jsonparser.utils;

import com.lovely3x.jsonparser.JSONType;

/**
 * Created by lovely3x on 15-8-8.
 * 放一些工具方法进来
 */
public class CommonUtils {

    public static final int guessType(String value) {

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
        } else if (value.startsWith("\"") && value.endsWith("\"")) {//string
            type = JSONType.JSON_TYPE_STRING;
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
        }
        return type;
    }
}
