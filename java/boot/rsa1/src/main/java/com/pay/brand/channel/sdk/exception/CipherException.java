package com.pay.brand.channel.sdk.exception;

/**
 * 加密，解密运行时异常
 *
 * @author wangyangyang
 * @date 2021-10-20 13:09:16
 **/
public class CipherException extends RuntimeException {


    protected String errCode;

    protected String errMsg;

    public CipherException() {
    }

    public CipherException(String message) {
        super(message);
    }

    public CipherException(String message, Throwable e) {
        super(message, e);
    }

    public CipherException(Throwable cause) {
        super(cause);
    }

    public CipherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CipherException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CipherException(String message, String errCode, String errMsg) {
        super(message);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CipherException(String message, Throwable cause, String errCode, String errMsg) {
        super(message, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CipherException(Throwable cause, String errCode, String errMsg) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CipherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           String errCode, String errMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
