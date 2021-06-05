package cn.kuwo.networker.exception;

import cn.kuwo.common.utilscode.UtilsCode;

public class ApiException extends RuntimeException {
    private int code;

    public ApiException(Throwable throwable, int code, String message) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        if (UtilsCode.INSTANCE.isEmpty(super.getMessage())) {
            return "请求失败";
        }
        return super.getMessage();
    }
}
