package com.fausgoal.sampleokhttp.http;

import com.fausgoal.okhttp.OkHttpUtils;
import com.fausgoal.okhttp.callback.Callback;
import com.fausgoal.okhttp.common.MediaTypeConst;
import com.fausgoal.sampleokhttp.BuildConfig;
import com.fausgoal.sampleokhttp.utils.DeviceUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Description：Http网络请求实现类
 * <br/><br/>Created by Fausgoal on 5/20/17.
 * <br/><br/>
 */
public class HttpFunctionImpl implements IHttpFunction {
    private static final String TAG = "HttpFunctionImpl";

    private final String mVersion;
    private final String mChannel;

    public HttpFunctionImpl() {
        mVersion = BuildConfig.VERSION_NAME;
        mChannel = BuildConfig.FLAVOR;
    }

    @Override
    public void sendCode(String funckey, Map<String, Object> params, Callback callback) {
        request(funckey, params, callback);
    }

    /**
     * POST请求
     *
     * @param funcKey 接口名称
     * @param params  参数
     */
    private void request(String funcKey, Map<String, Object> params, Callback callback) {
        // 直接使用url
        String url = getAPIUrl(funcKey);

        OkHttpUtils.postJson()
                .url(url)
                .mediaType(MediaTypeConst.MEDIA_TYPE_JSON) // json格式
                .headers(getHeaderParams()) // 默认header参数
                .content(params) // 请求参数
                .build()
                .execute(callback);
    }

    private String getAPIUrl(String key) {
        String url;
        if (HttpRequestParams.IS_USE_HTTPS) {
            url = HttpRequestParams.HTTPS_API_URL;
        } else {
            url = HttpRequestParams.HTTP_API_URL;
        }

        StringBuilder buffer = new StringBuilder(url);
        if (!url.endsWith("/") && !key.startsWith("/")) {
            buffer.append("/");
        }
        if (url.endsWith("/") && key.startsWith("/")) {
            key = key.substring(1, key.length());
        }
        return buffer.append(key).toString();
    }

    /**
     * 设置Header默认参数
     */
    private Map<String, String> getHeaderParams() {
        Map<String, String> defaultParams = new TreeMap<>();
        defaultParams.put(HttpRequestFiled.APP_OS, DeviceUtils.getSystemVersion());
        defaultParams.put(HttpRequestFiled.APP_VERSION, mVersion);
        defaultParams.put(HttpRequestFiled.APP_CHANNEL, mChannel);
        // TODO: 5/20/17
        String token = "";
        defaultParams.put(HttpRequestFiled.AUTH_TOKEN, token);
        return defaultParams;
    }
}
