package com.pay.brand.channel.sdk.bean;

import java.util.Objects;

/**
 * @author wangyangyang
 * @date 2022-02-16 15:08:42
 **/
public class SignInReq {

    private String brandNo;

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignInReq)) {
            return false;
        }
        SignInReq signInReq = (SignInReq) o;
        return Objects.equals(brandNo, signInReq.brandNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandNo);
    }

    @Override
    public String toString() {
        return "SignInReq{" +
                "brandNo='" + brandNo + '\'' +
                '}';
    }
}
