package com.fausgoal.okhttp;

import com.fausgoal.okhttp.cookie.CookieJarImpl;
import com.fausgoal.okhttp.cookie.store.MemoryCookieStore;
import com.fausgoal.okhttp.https.HttpsUtils;
import com.fausgoal.okhttp.log.LoggerInterceptor;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * Description：OkHttpClient默认的初始化
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public class OkHttpUtilsInitialize {
    private static final String HTTP_TAG = "Fausgoal[OkHttpUtils]";

    private OkHttpUtilsInitialize() {
    }

    public static OkHttpClient getDefaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(HTTP_TAG));
        return builder.build();
    }

    /**
     * 默认初始化
     */
    public static void initHttp(boolean showLog) {
        initHttp(OkHttpUtils.TIME_OUT, OkHttpUtils.TIME_OUT, showLog);
    }

    public static void initHttp(long connectTimeout, long readTimeout, boolean showLog) {
        initHttp(connectTimeout, readTimeout, HTTP_TAG, showLog);
    }

    public static void initHttp(long connectTimeout, long readTimeout, String tag, boolean showLog) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(tag, showLog, showLog));
        OkHttpUtils.initHttpClicnt(builder.build());
    }

    public static void initHttps(boolean showLog, String strCertificates, InputStream bksFile, String password) {
        InputStream[] certificates =
                new InputStream[]{new Buffer()
                        .writeUtf8(strCertificates)
                        .inputStream()};
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, password);
        initHttps(showLog, sslParams);
    }

    public static void initHttps(boolean showLog, HttpsUtils.SSLParams sslParams) {
        initHttps(OkHttpUtils.TIME_OUT, OkHttpUtils.TIME_OUT, HTTP_TAG, showLog, sslParams);
    }

    public static void initHttps(long connectTimeout, long readTimeout, boolean showLog,
                                 HttpsUtils.SSLParams sslParams) {
        initHttps(connectTimeout, readTimeout, HTTP_TAG, showLog, sslParams);
    }

    public static void initHttps(long connectTimeout, long readTimeout, String tag, boolean showLog,
                                 HttpsUtils.SSLParams sslParams) {
        CookieJarImpl cookieJar = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(tag, showLog, showLog))
                .cookieJar(cookieJar)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initHttpClicnt(okHttpClient);
    }
}
