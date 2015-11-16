package com.lovely3x.jsonparser.model;

import com.lovely3x.jsonparser.classcreator.ClassCreator;
import com.lovely3x.jsonparser.classcreator.UnderlineClassCreator;
import com.lovely3x.jsonparser.conversation.rule.JSONGenerateRule;
import com.lovely3x.jsonparser.conversation.rule.UnderlineJSONGenerateKeyRule;
import com.lovely3x.jsonparser.formatter.JSONFormatter;
import com.lovely3x.jsonparser.formatter.JSONFormatterImpl;
import com.lovely3x.jsonparser.matcher.JSONMatcher;
import com.lovely3x.jsonparser.matcher.UnderlineMatcher;
import com.lovely3x.jsonparser.objectcreator.ObjectCreator;
import com.lovely3x.jsonparser.objectcreator.ObjectCreatorImpl;
import com.lovely3x.jsonparser.parser.JSONParser;
import com.lovely3x.jsonparser.parser.JSONParserImpl;
import com.lovely3x.jsonparser.source.JSONSource;
import com.lovely3x.jsonparser.source.JSONSourceImpl;
import com.lovely3x.jsonparser.source.MapJSONSource;
import com.lovely3x.jsonparser.source.ObjectJSONSource;

import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * Created by lovely3x on 15-6-29.
 * json 对象
 */
public class JSONObject {

    /**
     * json解析器
     */
    private final JSONParser mParser;

    /**
     * json数据源
     */
    private final JSONSource mSource;

    /**
     * 键值对工厂
     */
    private final JSONPairFactory mFactory;
    /**
     * 解析好的键值对
     */
    private Map<JSONKey, JSONValue> mPair;


    public JSONObject(String source) {
        this(new JSONSourceImpl(source));
    }

    /**
     * 通过默认的解析器和指定的数据源创建一个json对象
     *
     * @param source 数据源
     */
    public JSONObject(JSONSource source) {
        this(source, new JSONParserImpl(source));
    }

    /**
     * 通过制定的解析器和指定的数据源创建一个json对象
     *
     * @param source 数据源
     * @param parser 解析器
     */
    public JSONObject(JSONSource source, JSONParser parser) {
        this(source, parser, new JSONPairFactoryImpl());
    }

    /**
     * 通过制定的键值对工厂
     * 和指定的数据源创建一个json对象
     *
     * @param source 数据源
     */
    public JSONObject(JSONSource source, JSONPairFactory factory) {
        this(source, new JSONParserImpl(source, factory), factory);
    }


    /**
     * 通过指定的参数创建一个JsonObject对象
     *
     * @param source  数据源
     * @param parser  数据解析器
     * @param factory 键值对工厂
     */
    public JSONObject(JSONSource source, JSONParser parser, JSONPairFactory factory) {
        this.mSource = source;
        this.mParser = parser;
        this.mFactory = factory;
        parse();
    }


    /**
     * 将一个对象转换为json对象
     *
     * @param object 需要转换的对象
     *               使用默认的转换规则(UnderlineJSONGenerateKeyRule)
     */
    public JSONObject(Object object) {
        this(object, new UnderlineJSONGenerateKeyRule());
    }

    /**
     * 将一个对象转换为json对象
     *
     * @param object 需要转换的对象
     * @param rule   指定的转换规则
     */
    public JSONObject(Object object, JSONGenerateRule rule) {
        this(new ObjectJSONSource(object, rule));
    }

    /**
     * 将一个对象转换为json对象
     *
     * @param object 需要转换的对象
     * @param rule   指定的转换规则
     */
    public JSONObject(Object object, JSONGenerateRule rule, JSONParser parser) {
        this(new ObjectJSONSource(object, rule), parser);
    }

    /**
     * 将一个对象转换为json对象
     *
     * @param object 需要转换的对象
     * @param rule   指定的转换规则
     */
    public JSONObject(Object object, JSONGenerateRule rule, JSONParser parser, JSONPairFactory factory) {
        this(new ObjectJSONSource(object, rule), parser, factory);
    }

    /**
     * 将一个对象转换为json对象
     *
     * @param object 需要转换的对象
     * @param rule   指定的转换规则
     */
    public JSONObject(Object object, JSONGenerateRule rule, JSONPairFactory factory) {
        this(new ObjectJSONSource(object, rule), factory);
    }


    /**
     * @param map
     */
    public JSONObject(Map map) {
        this(map, new UnderlineJSONGenerateKeyRule());
    }

    /**
     * @param map
     * @param rule
     */
    public JSONObject(Map map, JSONGenerateRule rule) {
        this(new MapJSONSource(map, rule));
    }

    /**
     * 通过一个map作为数据源
     * 一般用于生成json串
     *
     * @param map    数据源 Map
     * @param parser 指定解析器
     * @param rule   生成的规则
     */
    public JSONObject(Map map, JSONGenerateRule rule, JSONParser parser) {
        this(new MapJSONSource(map, rule), parser);
    }


    /**
     * 通过一个map作为数据源
     * 一般用于生成json串
     *
     * @param map     数据源 Map
     * @param rule    生成的规则
     * @param factory 键值对生成工厂
     */
    public JSONObject(Map map, JSONGenerateRule rule, JSONParser parser, JSONPairFactory factory) {
        this(new MapJSONSource(map, rule), parser, factory);
    }


    /**
     * 解析,如果是在解析情况下会自动调用
     * 但是如果是在将对象创建为json对象,则需要主动调用这个方法解析
     * 因为在大多数情况下,没有人会将一个对象转换为json串之后再自己进行解析
     */
    private void parse() {
        this.mPair = mParser.parseJSONObject();
    }

    /**
     * 通过制定的key获取json对象
     *
     * @param key 指定的key
     */
    public JSONObject getJSONObject(String key) {
        return getJSONObject(mFactory.getJSONKey(key));
    }

    /**
     * 通过制定的key获取json对象
     *
     * @param key 指定的key
     */
    public JSONObject getJSONObject(JSONKey key) {
        return mPair.get(key).getJSONObject();
    }

    /**
     * 通过指定的key 获取jsonArray
     *
     * @param key 指定的key
     * @return null如果没有找到该key 或者是 JsonArray
     */
    public JSONArray getJSONArray(String key) {
        return getJSONArray(mFactory.getJSONKey(key));
    }

    /**
     * 通过指定的key 获取jsonArray
     *
     * @param key 指定的key
     * @return null如果没有找到该key 或者是 JsonArray
     */
    public JSONArray getJSONArray(JSONKey key) {
        return mPair.get(key).getJSONArray();
    }

    /**
     * 通过制定的key 获取String
     */
    public String getString(String key) {
        return getString(mFactory.getJSONKey(key));
    }

    /**
     * 通过制定的key 获取String
     */
    public String getString(JSONSource source) {
        return getString(mFactory.getJSONKey(source));
    }

    /**
     * 通过制定的key 获取String
     */
    public String getString(JSONKey key) {
        return mPair.get(key).getString();
    }


    /**
     * 获取当前json串的键值对
     */
    public Map<JSONKey, JSONValue> getPair() {
        return mPair;
    }


    /**
     * 猜测指定的key对应的数据类型
     */
    public int guessType(String key) {
        return guessType(new JSONSourceImpl(key));
    }

    /**
     * 猜测指定的key对应的数据类型
     */
    public int guessType(JSONSource key) {
        return mPair.get(mFactory.getJSONKey(key)).guessType();
    }


    /**
     * 获取int 类型
     *
     * @param key 获取的的key
     */
    public int getInt(String key) {
        return getInt(new JSONSourceImpl(key));
    }

    /**
     * 获取 int 类型
     */
    public int getInt(JSONSource key) {
        return getInt(mFactory.getJSONKey(key));
    }


    /**
     * 获取 int 类型
     */
    public int getInt(JSONKey key) {
        return mPair.get(key).getInt();
    }


    /**
     * 获取long型的描述
     *
     * @param key key
     */
    public long getLong(String key) {
        return getLong(new JSONSourceImpl(key));
    }

    /**
     * 获取long型的描述
     *
     * @param source key
     */
    public long getLong(JSONSource source) {
        return getLong(mFactory.getJSONKey(source));
    }

    /**
     * 获取long型的描述
     *
     * @param key key
     */
    public long getLong(JSONKey key) {
        return mPair.get(key).getLong();
    }

    /**
     * 获取float型的描述
     *
     * @param key key
     */
    public float getFloat(String key) {
        return getFloat(new JSONSourceImpl(key));
    }

    /**
     * 获取float型的描述
     *
     * @param source key
     */
    public float getFloat(JSONSource source) {
        return getFloat(mFactory.getJSONKey(source));
    }

    /**
     * 获取float型的描述
     *
     * @param key key
     */
    public float getFloat(JSONKey key) {
        return mPair.get(key).getFloat();
    }

    /**
     * 获取double型的描述
     *
     * @param key key
     */
    public double getDouble(String key) {
        return getDouble(new JSONSourceImpl(key));
    }

    /**
     * 获取double的描述
     *
     * @param source key
     */
    public double getDouble(JSONSource source) {
        return getDouble(mFactory.getJSONKey(source));
    }

    /**
     * 获取double的描述
     *
     * @param key key
     */
    public double getDouble(JSONKey key) {
        return mPair.get(key).getDouble();
    }

    /**
     * 获取double型的描述
     *
     * @param key key
     */
    public boolean getBoolean(String key) {
        return getBoolean(new JSONSourceImpl(key));
    }

    /**
     * 获取double的描述
     *
     * @param source key
     */
    public boolean getBoolean(JSONSource source) {
        return getBoolean(mFactory.getJSONKey(source));
    }

    /**
     * 获取double的描述
     *
     * @param key key
     */
    public boolean getBoolean(JSONKey key) {
        return mPair.get(key).getBoolean();
    }

    /**
     * 获取强制类型转换 int
     *
     * @param key key
     */
    public int getCastInt(String key) {
        return getCastInt(new JSONSourceImpl(key));
    }

    /**
     * 获取强制类型转换 int
     *
     * @param source key
     */
    public int getCastInt(JSONSource source) {
        return getCastInt(mFactory.getJSONKey(source));
    }

    /**
     * 获取强制类型转换 int
     *
     * @param key key
     */
    public int getCastInt(JSONKey key) {
        return mPair.get(key).getCastInt();
    }


    /**
     * 获取JSONValue对象
     *
     * @param key 指定获取JSONValue的key
     * @return 这个key 对应的value
     */
    public JSONValue getValue(String key) {
        return getValue(new JSONKeyImpl(key));
    }


    /**
     * 通过json数据源 获取JSONValue对象
     *
     * @param source 数据源 应该是key的数据源
     * @return 这个key对应的value
     */
    public JSONValue getValue(JSONSource source) {
        return getValue(mFactory.getJSONKey(source));
    }


    /**
     * 获取JSONValue对象
     *
     * @param key 制定的JSONKey
     * @return 根据JSONKey获取JSONValue对象
     */
    public JSONValue getValue(JSONKey key) {
        return mPair.get(key);
    }

    /**
     * 获取key集合
     *
     * @return 获取当前的这个json对象的所有key集合
     */
    public Set<JSONKey> keySet() {
        return mPair.keySet();
    }

    /**
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     * 将当前的json对象转换为java对象
     *
     * @param clazz   需要转换的类
     * @param <T>     需要结果类型
     * @param creator 对象创建器
     * @param matcher 属性匹配器 用来对json和class的字段进行匹配
     * @return 创建成功则返回该对象, 否则为null
     */
    public <T> T createObject(Class clazz, ObjectCreator<T> creator, JSONMatcher matcher) {
        return creator.create(this, clazz, matcher);
    }


    /**
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     * 将当前的json对象转换为java对象
     * 使用默认的属性匹配器(UnderlineMatcher)
     *
     * @param clazz   需要转换的类
     * @param <T>     需要结果类型
     * @param creator 对象创建器
     * @return 创建成功则返回该对象, 否则为null
     */
    public <T> T createObject(Class clazz, ObjectCreator<T> creator) {
        return creator.create(this, clazz, new UnderlineMatcher());
    }

    /**
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     * 将当前的json对象转换为java对象
     * 使用默认的属性匹配器(UnderlineMatcher)
     * 使用的默认的对象创建器(ObjectCreatorImpl)
     *
     * @param clazz 需要转换的类
     * @param <T>   需要结果类型
     * @return 创建成功则返回该对象, 否则为null
     */
    public <T> T createObject(Class<T> clazz) {
        return createObject(clazz, new UnderlineMatcher());
    }

    /**
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     * 将当前的json对象转换为java对象
     * <p>
     * 使用默认的对象创建器
     *
     * @param clazz   需要转换的类
     * @param <T>     需要结果类型
     * @param matcher 属性匹配器 用来对json和class的字段进行匹配
     * @return 创建成功则返回该对象, 否则为null
     */
    public <T> T createObject(Class<T> clazz, JSONMatcher matcher) {
        return new ObjectCreatorImpl<T>().create(this, clazz, matcher);
    }

    /**
     * 使用指定的格式化类进行格式化
     *
     * @param formatter 指定的格式化器
     * @return 格式化后的字符串
     */
    public String format(JSONFormatter formatter) {
        return formatter.formatJSONObject(this).toString();
    }

    /**
     * 使用默认的格式化实现类进行格式化
     *
     * @return 格式化号的字符串
     */
    public String format() {
        return format(new JSONFormatterImpl());
    }

    /**
     * 使用默认的类创建器
     *
     * @param className 类名
     * @param os        输出流
     * @return 输出流
     */
    public OutputStream createClass(String className, OutputStream os) {
        return createClass(new UnderlineClassCreator(), className, os);
    }

    /**
     * 使用指定的creator 创建 class
     *
     * @param creator   类创建器
     * @param className 类名
     * @param os        输出流
     * @return 输出流
     */
    public OutputStream createClass(ClassCreator creator, String className, OutputStream os) {
        return creator.createObject(this, className, os);
    }


    /**
     * toString 可以显示出当前的数据源,如果你是在进行使用对象生成json串,
     * 那么你可能需要通过这个方法获得转换字符串的
     *
     * @return 当前的数据源
     */
    @Override
    public String toString() {
        return mSource.input();
    }
}
