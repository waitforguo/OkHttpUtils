package com.fausgoal.okhttp.builder;

import com.fausgoal.okhttp.request.PostFormRequest;
import com.fausgoal.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：表单参数类型提交请求
 * <br/><br/>Created by zhy on 15/12/14.
 * <br/><br/>
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable {
    private List<FileInput> mFiles = new ArrayList<>();

    @Override
    public RequestCall build() {
        return new PostFormRequest(mUrl, mTag, mParams, mHeaders, mFiles, mId).build();
    }

    public PostFormBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.mFiles.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        mFiles.add(new FileInput(name, filename, file));
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return this;
    }
}
