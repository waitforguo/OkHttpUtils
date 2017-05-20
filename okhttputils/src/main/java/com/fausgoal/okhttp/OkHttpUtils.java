package com.fausgoal.okhttp;

import com.fausgoal.okhttp.builder.GetBuilder;
import com.fausgoal.okhttp.builder.HeadBuilder;
import com.fausgoal.okhttp.builder.OtherRequestBuilder;
import com.fausgoal.okhttp.builder.PostFileBuilder;
import com.fausgoal.okhttp.builder.PostFormBuilder;
import com.fausgoal.okhttp.builder.PostJsonBuilder;
import com.fausgoal.okhttp.builder.PostStringBuilder;
import com.fausgoal.okhttp.callback.Callback;
import com.fausgoal.okhttp.log.LoggerInterceptor;
import com.fausgoal.okhttp.request.RequestCall;
import com.fausgoal.okhttp.uitls.Exceptions;
import com.fausgoal.okhttp.uitls.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description：OkHttp网络请求工具类封装
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public class OkHttpUtils {
    public static final String TAG = "Fausgoal[OkHttpUtils]";

    public static final long DEFAULT_MILLISECONDS = 10_000L;

    /**
     * 超时 10s
     */
    public static final long TIME_OUT = 10 * 1000;

    private volatile static OkHttpUtils ins;

    /**
     * 初始化OkHttpClient，在Application的onCreate()中初始化
     *
     * @param okHttpClient okHttpClient对象
     * @return OkHttpUtils对象
     */
    public static OkHttpUtils initHttpClicnt(OkHttpClient okHttpClient) {
        if (null == ins) {
            synchronized (OkHttpUtils.class) {
                if (null == ins) {
                    ins = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return ins;
    }

    /**
     * 单例模式，如果不在Application的onCreate()中调用initHttpClicnt(OkHttpClient okHttpClient)方式初始化，
     * 会创建一个默认的okHttpClient对象
     *
     * @return OkHttpUtils对象
     */
    public static OkHttpUtils getIns() {
        return initHttpClicnt(null);
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }

    private static class HTTP_TYPE {
        public static final int HTTP = 888;
        public static final int HTTPS = 999;
    }

    private OkHttpClient mHttpClient = null;
    private final Platform mPlatform;

    private OkHttpUtils(OkHttpClient okHttpClient) {
        mPlatform = Platform.get();
        if (null == okHttpClient) {
            mHttpClient = getDefaultHttpClient();
        } else {
            mHttpClient = okHttpClient;
        }
    }

    private OkHttpClient getDefaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(TAG));
        return builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        if (null == mHttpClient) {
            Exceptions.illegalArgument("mHttpClient can not be null.");
        }
        return mHttpClient;
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostJsonBuilder postJson() {
        return new PostJsonBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null) {
            callback = Callback.CALLBACK_DEFAULT;
        }
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    ResponseBody body = response.body();
                    if (body != null)
                        body.close();
                }

            }
        });
    }

    private void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) {
            return;
        }

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
