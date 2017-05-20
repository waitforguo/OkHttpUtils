package com.fausgoal.okhttp.builder;

import java.util.Map;

/**
 * Description：
 * <br/><br/>Created by zhy on 16/3/1.
 * <br/><br/>
 */
public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParams(String key, String val);
}
