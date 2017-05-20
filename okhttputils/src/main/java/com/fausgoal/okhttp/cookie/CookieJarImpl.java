package com.fausgoal.okhttp.cookie;

import com.fausgoal.okhttp.cookie.store.CookieStore;
import com.fausgoal.okhttp.uitls.Exceptions;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Description：
 * <br/><br/>Created by zhy on 16/3/10.
 * <br/><br/>
 */
public class CookieJarImpl implements CookieJar {
    private CookieStore mCookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            Exceptions.illegalArgument("mCookieStore can not be null.");
        }
        this.mCookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mCookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return mCookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return mCookieStore;
    }
}
