package com.fausgoal.okhttp.callback;

import com.fausgoal.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description：
 * <br/><br/>Created by zhy on 15/12/15.
 * <br/><br/>
 */
public abstract class FileCallBack extends Callback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String mDestFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String mDestFileName;

    public FileCallBack(String destFileDir, String destFileName) {
        this.mDestFileDir = destFileDir;
        this.mDestFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response, id);
    }

    public File saveFile(Response response, final int id) throws IOException {
        ResponseBody body = response.body();
        if (null == body) {
            return null;
        }
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            final long total = body.contentLength();

            long sum = 0;

            File dir = new File(mDestFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, mDestFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getIns().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        inProgress(finalSum * 1.0f / total, total, id);
                    }
                });
            }
            fos.flush();
            return file;
        } finally {
            try {
                body.close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // do nothing
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
