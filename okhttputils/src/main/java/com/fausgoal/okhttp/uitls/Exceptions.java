package com.fausgoal.okhttp.uitls;

/**
 * Description：自定义异常类
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class Exceptions {
    private Exceptions() {
    }

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }
}
