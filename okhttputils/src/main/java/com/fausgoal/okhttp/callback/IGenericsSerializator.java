package com.fausgoal.okhttp.callback;

/**
 * Description：
 * <br/><br/>Created by JimGong on 2016/6/23.
 * <br/><br/>
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
