package com.lovely3x.jsonpareser.bean;

/**
 * 网络请求响应
 * Created by lovely3x on 15-7-18.
 */
public class Response {

    /**
     * 本次的请求是否成功
     */
    public boolean isSucessful;

    /**
     * 附加消息
     */
    public int addtional;
    /**
     * 附加的对象
     */
    public Object obj;

    /**
     * 失败码
     */
    public int errorCode;

    /**
     * 失败的原因
     */
    public String errorReason;

}
