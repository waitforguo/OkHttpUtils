package com.fausgoal.okhttp.common;

import okhttp3.MediaType;

/**
 * Description：e, appropriate to describe
 * the content type of an HTTP request or response body.
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public interface MediaTypeConst {
    /**
     * 提交字符串数据
     */
    MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    /**
     * 提交流数据
     */
    MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    /**
     * 提交json数据
     */
    MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    /**
     * 提交字符串(markdown)数据
     */
    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
}
