package com.lovely3x.jsonparser.model;

import com.lovely3x.jsonparser.JSONType;
import com.lovely3x.jsonparser.conversation.rule.JSONGenerateRule;
import com.lovely3x.jsonparser.conversation.rule.JSONKeyGenerateRule;
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
import com.lovely3x.jsonparser.source.ListJSONSource;

import java.util.ArrayList;
import java.util.List;

/**
 * json数组
 * Created by lovely3x on 15-6-29.
 */
public class JSONArray {

    /**
     * 数据源
     */
    private final JSONSource mSource;

    /**
     * 解析器
     */
    private final JSONParser mParser;

    /**
     * 键值对工厂
     */
    private final JSONPairFactory mFactory;

    /**
     * 解析的结果
     */
    private List<JSONValue> values;

    /**
     * 使用默认的解析器对数据源解析
     *
     * @param source 数据源
     */
    public JSONArray(JSONSource source) {
        this(source, new JSONParserImpl(source));
    }

    /**
     * 使用默认的数据源工具解析指定的字符串 提供 数据源服务
     *
     * @param source
     */
    public JSONArray(String source) {
        this(new JSONSourceImpl(source));
    }

    /**
     * 指定一个解析器,通过指定的解析器来解析数据源
     *
     * @param source 数据源
     * @param parser 解析器
     */
    public JSONArray(JSONSource source, JSONParser parser) {
        this(source, parser, new JSONPairFactoryImpl());
    }

    /**
     * 通过指定的参数创建一个JSONArray对象
     *
     * @param source  数据源
     * @param parser  数据解析器
     * @param factory 键值对工厂
     */
    public JSONArray(JSONSource source, JSONParser parser, JSONPairFactory factory) {
        this.mSource = source;
        this.mParser = parser;
        this.mFactory = factory;
        parse();
    }


    /**
     * 根据一个列表创建一个jsonArray
     * 使用默认的创建规则(UnderlineJSONGenerateKeyRule)
     *
     * @param list 数据源
     */
    public JSONArray(List list) {
        this(new ListJSONSource(list, new UnderlineJSONGenerateKeyRule()));
    }


    /**
     * 根据一个列表和指定的规则
     * 创建一个jsonArray
     *
     * @param list 需要创建的列表
     * @param rule 规则
     */
    public JSONArray(List list, JSONGenerateRule rule) {
        this(new ListJSONSource(list, rule));
    }

    /**
     * 通过指定需要创建的list和解析器来创建JSONArray
     *
     * @param list   数据源
     * @param parser 解析器
     */
    public JSONArray(List list, JSONParser parser) {
        this(new ListJSONSource(list, new UnderlineJSONGenerateKeyRule()), parser);
    }

    /**
     * 通过指定需要创建的list和解析器来创建JSONArray和生成规则
     *
     * @param list   数据源
     * @param parser 解析器
     * @param rule   生成规则
     */
    public JSONArray(List list, JSONParser parser, JSONKeyGenerateRule rule) {
        this(new ListJSONSource(list, rule), parser);
    }


    /**
     * 通过指定需要创建的list和解析器和键值对工厂来创建JSONArray
     *
     * @param list    数据源
     * @param parser  解析器
     * @param factory 键值对工厂
     */
    public JSONArray(List list, JSONParser parser, JSONPairFactory factory) {
        this(new ListJSONSource(list, new UnderlineJSONGenerateKeyRule()), parser, factory);
    }


    /**
     * 通过指定的数据源和规则,解析器以及键值对工厂来创建JSONArray对象
     *
     * @param list    数据源
     * @param rule    规则文件
     * @param parser  解析器
     * @param factory 键值对工厂
     */
    public JSONArray(List list, JSONGenerateRule rule, JSONParser parser, JSONPairFactory factory) {
        this(new ListJSONSource(list, rule), parser, factory);
    }

    /**
     * 通过解析器解析
     */
    private void parse() {
        if (this.mParser == null) throw new RuntimeException("jsonParser can be not null.");
        this.values = mParser.parseJSONArray();
    }

    public JSONObject getJSONOObject(int index) {
        return values.get(index).getJSONObject();
    }

    /**
     * 获取JsonArray型的value
     *
     * @param index
     * @return
     */
    public JSONArray getJSONArray(int index) {
        return values.get(index).getJSONArray();
    }

    /**
     * 获取string类型的value
     *
     * @param index
     * @return
     */
    public String getString(int index) {
        return values.get(index).getString();
    }

    /**
     * 获取当前的这个JSONArray的长度,可通过遍历下标获取这个数组中的元素
     *
     * @return
     */
    public int length() {
        return values.size();
    }

    /**
     * 获取jsonValues
     *
     * @return
     */
    public List<JSONValue> getValues() {
        return values;
    }

    /**
     * 获取jsonValue
     *
     * @return
     */
    public JSONValue getValue(int index) {
        return values.get(index);
    }


    /**
     * 猜测类型
     *
     * @param index
     * @return
     */
    public int guessType(int index) {
        return values.get(index).guessType();
    }

    /**
     * 获取int 类型
     *
     * @param index 获取的下标
     * @return
     */
    public int getInt(int index) {
        return values.get(index).getInt();
    }

    /**
     * 获取long型的描述
     *
     * @param index 下标
     * @return
     */
    public long getLong(int index) {
        return values.get(index).getLong();
    }

    /**
     * 获取float型的描述
     *
     * @return
     */
    public float getFloat(int index) {
        return values.get(index).getFloat();
    }

    /**
     * 获取double型的描述
     *
     * @param index
     * @return
     */
    public double getDouble(int index) {
        return values.get(index).getDouble();
    }

    /**
     * 获取布尔型的描述
     *
     * @param index
     * @return
     */
    public boolean getBoolean(int index) {
        return values.get(index).getBoolean();
    }

    /**
     * 获取强制类型转换 int
     *
     * @param index
     * @return
     */
    public int getCastInt(int index) {
        return values.get(index).getCastInt();
    }


    /**
     * 使用指定的对象创建器 和 匹配器 创建一个对象集合
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param container 对象的容器
     * @param clazz     需要创建的对象的 类对象
     * @param creator   对象创建器 用来创建类
     * @param matcher   属性匹配器 用来对json和class的字段惊醒匹配
     * @param <T>       创建的结果集合
     */
    public <T> List<T> createObjects(Class<? extends List> container, Class clazz, ObjectCreator<T> creator, JSONMatcher matcher) throws IllegalAccessException, InstantiationException {
        final int count = values.size();
        List<T> list = container.newInstance();
        for (int i = 0; i < count; i++) {
            JSONValue value = values.get(i);
            switch (value.guessType()) {
                case JSONType.JSON_TYPE_OBJECT:
                    T obj = value.getJSONObject().createObject(clazz, creator, matcher);
                    if (obj != null) list.add(obj);
                    break;
            }
        }
        return list;
    }


    /**
     * 使用指定的对象创建器,类对象 和 默认的属性匹配器(UnderlineMatcher) 创建对象
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param clazz   需要创建的对象的 类对象
     * @param creator 对象创建器 用来创建类
     * @param <T>     创建的结果集合
     */
    public <T> List<T> createObjects(Class clazz, ObjectCreator<T> creator) throws InstantiationException, IllegalAccessException {
        return createObjects(ArrayList.class, clazz, creator, new UnderlineMatcher());
    }


    /**
     * 使用指定的默认的对象创建器(ObjectCreatorImpl) 和 指定的匹配器与类对象 创建一个对象集合
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param <T>     创建的结果集合
     * @param clazz   需要创建的对象的 类对象
     * @param matcher 属性匹配器 用来对json和class的字段惊醒匹配
     */
    public <T> List<T> createObjects(Class clazz, JSONMatcher matcher) throws InstantiationException, IllegalAccessException {
        return createObjects(ArrayList.class, clazz, new ObjectCreatorImpl<T>(), matcher);
    }

    /**
     * 使用指定的默认的对象创建器(ObjectCreatorImpl) 类对象 创建一个对象集合
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param <T>   创建的结果集合
     * @param clazz 需要创建的对象的 类对象
     */
    public <T> List<T> createObjects(Class clazz) throws InstantiationException, IllegalAccessException {
        return createObjects(ArrayList.class, clazz, new ObjectCreatorImpl<T>(), new UnderlineMatcher());
    }


    /**
     * 使用指定的对象创建器,类对象 和 默认的属性匹配器(UnderlineMatcher) 创建对象
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param container 对象的容器
     * @param clazz     需要创建的对象的 类对象
     * @param creator   对象创建器 用来创建类
     * @param <T>       创建的结果集合
     */
    public <T> List<T> createObjects(Class<? extends List> container, Class clazz, ObjectCreator<T> creator) throws InstantiationException, IllegalAccessException {
        return createObjects(container, clazz, creator, new UnderlineMatcher());
    }


    /**
     * 使用指定的默认的对象创建器(ObjectCreatorImpl) 和 指定的匹配器与类对象 创建一个对象集合
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param container 对象的容器
     * @param <T>       创建的结果集合
     * @param clazz     需要创建的对象的 类对象
     * @param matcher   属性匹配器 用来对json和class的字段惊醒匹配
     */
    public <T> List<T> createObjects(Class<? extends List> container, Class clazz, JSONMatcher matcher) throws InstantiationException, IllegalAccessException {
        return createObjects(container, clazz, new ObjectCreatorImpl<T>(), matcher);
    }

    /**
     * 使用指定的默认的对象创建器(ObjectCreatorImpl) 类对象 创建一个对象集合
     * <p/>
     * 使用这个方法创建的对象是不存在对象依赖的,
     * 它只能解析出单个对象中的基本类型和String 对于复合类型是没有办法解析的
     * 也就是说如果 现在 一个 类中有一个字段是List 那么是没办法解析出这个List的
     *
     * @param container 对象的容器
     * @param clazz     需要创建的对象的 类对象
     * @param <T>       创建的结果集合
     */
    public <T> List<T> createObjects(Class<? extends List> container, Class clazz) throws InstantiationException, IllegalAccessException {
        return createObjects(container, clazz, new ObjectCreatorImpl<T>(), new UnderlineMatcher());
    }

    @Override
    public String toString() {
        return mSource.input();
    }

    /**
     * 格式化这个JSONArray对象
     *
     * @return 格式化好的字符串
     */
    public String format() {
        return format(new JSONFormatterImpl());
    }

    /**
     * 使用指定的格式类,记性格式化
     *
     * @param formatter 格式化工具
     * @return 格式化好的字符串
     */
    public String format(JSONFormatter formatter) {
        return formatter.formatJSONArray(this).toString();
    }
}
