package com.lovely3x.jsonparser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解用于你和json数据格式交换
 * 用法,假设你现在你的类的字段名叫做 'person' 但是你现在想要这个字段 能够和服务器的 'ren' 匹配,那么你可以
 * 在你的 'person' 这个字段上加这个个注解 如:
 * <p>@JSON("ren") <br>String person; </p>
 * Created by lovely3x on 15-7-11.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSON {
    /**
     * json字段的名字
     *
     * @return
     */
    String value();
}