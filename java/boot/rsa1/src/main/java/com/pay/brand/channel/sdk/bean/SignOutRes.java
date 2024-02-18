package com.pay.brand.channel.sdk.bean;

import java.util.Objects;

/**
 * @author wangyangyang
 * @date 2022-02-16 15:03:01
 **/
public class SignOutRes {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignOutRes)) {
            return false;
        }
        SignOutRes that = (SignOutRes) o;
        return Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }

    @Override
    public String toString() {
        return "SignOutRes{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
