package com.pay.brand.channel.sdk.exception;

/**
 * 流量限制异常
 *
 * @author wangyangyang
 * @date 2021-10-20 13:09:16
 **/
public class FlowLimitException extends RuntimeException {


    protected String errCode;

    protected String errMsg;

    public FlowLimitException() {
    }

    public FlowLimitException(String message) {
        super(message);
    }

    public FlowLimitException(String message, Throwable e) {
        super(message, e);
    }

    public FlowLimitException(Throwable cause) {
        super(cause);
    }

    public FlowLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FlowLimitException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public FlowLimitException(String message, String errCode, String errMsg) {
        super(message);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public FlowLimitException(String message, Throwable cause, String errCode, String errMsg) {
        super(message, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public FlowLimitException(Throwable cause, String errCode, String errMsg) {
        super(cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public FlowLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
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
