package com.lovely3x.jsonparser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lovely3x on 15-7-11.
 * 使用这个注解表示这个字段需要一个jsonArray
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONObject {
    /**
     * @return json字段key
     */
    String value();

    /**
     * JSONObject对应的对象
     *
     * @return
     */
    Class object();

}
