package com.pay.brand.channel.sdk.exception;

/**
 * 业务签名异常
 *
 * @author wangyangyang
 * @date 2021-10-20 13:09:16
 **/
public class BizSignException extends RuntimeException {


    protected String errCode;

    protected String errMsg;

    public BizSignException() {
    }

    public BizSignException(String message) {
        super(message);
    }

    public BizSignException(String message, Throwable e) {
        super(message, e);
    }

    public BizSignException(Throwable cause) {
        super(cause);
    }

    public BizSignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BizSignException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BizSignException(String message, String errCode, String errMsg) {
        super(message);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BizSignException(String message, Throwable cause, String errCode, String errMsg) {
        super(message, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BizSignException(Throwable cause, String errCode, String errMsg) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BizSignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
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
