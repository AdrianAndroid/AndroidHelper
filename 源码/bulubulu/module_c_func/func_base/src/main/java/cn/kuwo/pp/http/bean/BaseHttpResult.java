package cn.kuwo.pp.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author shihc
 * @date 2016/10/31
 */

public class BaseHttpResult<T> {

    @SerializedName("code")
    private int status;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return status == 200;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        if (data == null) {
            return (T) new Object();
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
