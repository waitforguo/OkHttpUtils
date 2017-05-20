package com.fausgoal.sampleokhttp;

import android.app.Application;

import com.fausgoal.okhttp.OkHttpUtilsInitialize;
import com.fausgoal.sampleokhttp.http.HttpRequestParams;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */

public class GLApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpUtilsInitialize.initHttps(true, HttpRequestParams.CER_ROAMTECH, null, null);
    }
}
