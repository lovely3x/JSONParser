package com.lovely3x.jsonparser.source;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by lovely3x on 15-6-29.
 * json 数据源 字符串提供者默认实现类
 */
public class JSONSourcImpl implements JSONSource {

    /**
     * 默认的编码方式
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 输入的数据源的字符串形式
     */
    private String input;

    /**
     * 使用一个字符串作为json输入源
     *
     * @param string
     */
    public JSONSourcImpl(String string) {
        this.input = string;
    }

    /**
     * 使用一个byte数组 作为json输入源 编码方式使用默认的UTF-8
     *
     * @param buf
     */
    public JSONSourcImpl(byte[] buf) throws UnsupportedEncodingException {
        this.input = new String(buf, DEFAULT_CHARSET);
    }

    /**
     * @param buf     使用一个byte数组 作为json输入源
     * @param charset 指定byte[]转换为String的编码方式
     */
    public JSONSourcImpl(byte[] buf, String charset) throws UnsupportedEncodingException {
        this.input = new String(buf, charset);
    }

    /**
     * @param stream 使用一个InputStream数组 作为json输入源
     *               使用默认的编码方式 UTF-8
     */
    public JSONSourcImpl(InputStream stream) throws IOException {
        this.input = streamToString(stream, DEFAULT_CHARSET);
    }

    /**
     * @param stream  使用一个InputStream数组 作为json输入源
     * @param charset 使用指定的编码方式
     */
    public JSONSourcImpl(InputStream stream, String charset) throws IOException {
        this.input = streamToString(stream, charset);
    }

    /**
     * 就爱那个输入流转换为string
     *
     * @param stream  需要转换的流
     * @param charset 制定的字符编码集
     * @return 转换好的string
     */
    private String streamToString(InputStream stream, String charset) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024 * 8];
        int len = -1;
        while ((len = stream.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        baos.flush();
        String str = new String(baos.toByteArray(), charset);
        baos.close();
        return str;
    }

    @Override
    public String input() {
        return input;
    }
}
