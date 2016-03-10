package com.lovely3x.common.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by lovely3x on 15-9-22.
 */
public class CommonUtils {

    /**
     * 判断一个字段是否能够修改
     *
     * @param field 需要判断的字段
     * @return
     */
    public static boolean fieldCanBeModify(Field field) {
        return field != null && ((field.getModifiers() & Modifier.FINAL) == 0);
    }
}
