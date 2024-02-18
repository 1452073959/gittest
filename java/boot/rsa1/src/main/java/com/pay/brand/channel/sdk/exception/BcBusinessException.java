package com.pay.brand.channel.sdk.exception;

/**
 * 通道业务异常
 *
 * @author wangyangyang
 * @date 2021-10-20 13:09:16
 **/
public class BcBusinessException extends RuntimeException {


    protected String errCode;

    protected String errMsg;

    public BcBusinessException() {
    }

    public BcBusinessException(String message) {
        super(message);
    }

    public BcBusinessException(String message, Throwable e) {
        super(message, e);
    }

    public BcBusinessException(Throwable cause) {
        super(cause);
    }

    public BcBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BcBusinessException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BcBusinessException(String message, String errCode, String errMsg) {
        super(message);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BcBusinessException(String message, Throwable cause, String errCode, String errMsg) {
        super(message, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BcBusinessException(Throwable cause, String errCode, String errMsg) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BcBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
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
