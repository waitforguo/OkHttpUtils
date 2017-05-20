package com.fausgoal.sampleokhttp;

import android.app.Application;

import com.fausgoal.okhttp.OkHttpUtils;
import com.fausgoal.okhttp.cookie.CookieJarImpl;
import com.fausgoal.okhttp.cookie.store.MemoryCookieStore;
import com.fausgoal.okhttp.https.HttpsUtils;
import com.fausgoal.okhttp.log.LoggerInterceptor;
import com.fausgoal.sampleokhttp.http.HttpRequestParams;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */

public class GLApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initHttps();
    }

    private void initHttps() {
        InputStream[] certificates =
                new InputStream[]{new Buffer()
                        .writeUtf8(HttpRequestParams.CER_ROAMTECH)
                        .inputStream()};
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, null, null);

        CookieJarImpl cookieJar = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(OkHttpUtils.TAG, true, true))
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
