package com.pay.brand.channel.sdk.bean;

import java.util.Objects;

/**
 * 签退请求
 *
 * @author wangyangyang
 * @date 2022-02-16 15:08:42
 **/
public class SignOutReq {

    private String workKey;

    public String getWorkKey() {
        return workKey;
    }

    public void setWorkKey(String workKey) {
        this.workKey = workKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignOutReq)) {
            return false;
        }
        SignOutReq signInReq = (SignOutReq) o;
        return Objects.equals(workKey, signInReq.workKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workKey);
    }

    @Override
    public String toString() {
        return "SignOutReq{" +
                "workKey='" + workKey + '\'' +
                '}';
    }
}
