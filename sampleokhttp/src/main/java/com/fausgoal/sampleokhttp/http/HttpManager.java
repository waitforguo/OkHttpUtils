package com.fausgoal.sampleokhttp.http;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 5/20/17.
 * <br/><br/>
 */
public class HttpManager {
    private static final String TAG = "HttpManager";

    /**
     * 调用API
     */
    public static IHttpFunction getInitialize() {
        return new HttpFunctionImpl();
    }
}
