package com.fausgoal.sampleokhttp.http;

import com.fausgoal.okhttp.callback.Callback;

import java.util.Map;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 5/20/17.
 * <br/><br/>
 */
public interface IHttpFunction {
    void sendCode(String funckey, Map<String, Object> params, Callback callback);
}
