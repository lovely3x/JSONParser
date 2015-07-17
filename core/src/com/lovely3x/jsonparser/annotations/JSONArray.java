package com.lovely3x.jsonparser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovely3x on 15-7-11.
 * 使用这个注解表示这个字段需要一个jsonArray
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONArray {

    /**
     * @return 存放对象的容器
     */
    Class<? extends List> container() default ArrayList.class;

    /**
     * 容器中存放的对象
     *
     * @return
     */
    Class object();

    /**
     * jsonArray的字段名
     *
     * @return
     */
    String value();
}
