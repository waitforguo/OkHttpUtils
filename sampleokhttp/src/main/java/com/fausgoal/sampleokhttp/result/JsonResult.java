package com.fausgoal.sampleokhttp.result;

/**
 * Description：HTTP返回的JSON结果
 * <pre>
 *     {"status":1,"data":[]|{},"msg":"发送成功"}
 * </pre>
 * <br/><br/>Created by Fausgoal on 5/19/17.
 * <br/><br/>
 */
public class JsonResult<T> {
    private int status;
    private T data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "status = " + status + ", msg = " + msg;
    }
}
