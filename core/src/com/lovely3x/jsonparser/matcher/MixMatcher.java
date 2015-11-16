package com.lovely3x.jsonparser.matcher;

import com.lovely3x.jsonparser.model.JSONObject;
import com.lovely3x.jsonparser.model.ObjectCreatorConfig;

/**
 * 混合匹配器解析器
 * 他将AnnotationMatcher与UnderlineMatcher同时作用来进行匹配器
 * 注意:Annotation的优先级比UnderLine的高
 * Created by lovely3x on 15-11-15.
 */
public class MixMatcher implements JSONMatcher {

    protected final AnnotationMatcher mAnnotationMatcher;
    protected final UnderlineMatcher mUnderlineMatcher;

    public MixMatcher(UnderlineMatcher underlineMatcher, AnnotationMatcher annotationMatcher) {
        this.mUnderlineMatcher = underlineMatcher;
        this.mAnnotationMatcher = annotationMatcher;
    }

    public MixMatcher() {
        this(new UnderlineMatcher(), new AnnotationMatcher());
    }

    @Override
    public ObjectCreatorConfig match(ObjectCreatorConfig newConfig) {
        mAnnotationMatcher.match(newConfig);
        if (!newConfig.isEqual) {
            mUnderlineMatcher.match(newConfig);
        }
        return newConfig;
    }

    @Override
    public void putValue(Object instance, JSONMatcher matcher, JSONObject jsonObject, ObjectCreatorConfig config) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        try {
            mUnderlineMatcher.putValue(instance, matcher, jsonObject, config);
        } catch (Exception e) {
            mAnnotationMatcher.putValue(instance, matcher, jsonObject, config);
        }
    }

    @Override
    public Object valueRule(Object value) {
        return value;
    }
}
