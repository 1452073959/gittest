package com.pay.brand.channel.sdk.enums;

/**
 * 响应码
 *
 * @author wangyangyang
 */

public enum ResCode {
    SUCCESS("00", "成功"),
    FAIL("98", "失败"),
    ERROR("99", "错误");

    private String code;

    private String msg;


    ResCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
