package com.pay.brand.channel.sdk.bean;

import java.util.Objects;

/**
 * @author wangyangyang
 * @date 2022-02-16 15:03:01
 **/
public class SignInRes {

    /**
     * 工作密钥
     */
    private String workKey;

    /**
     * 机构公钥
     */
    private String publicKey;

    public String getWorkKey() {
        return workKey;
    }

    public void setWorkKey(String workKey) {
        this.workKey = workKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignInRes)) {
            return false;
        }
        SignInRes signInRes = (SignInRes) o;
        return Objects.equals(workKey, signInRes.workKey) &&
                Objects.equals(publicKey, signInRes.publicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workKey, publicKey);
    }

    @Override
    public String toString() {
        return "SignInRes{" +
                "workKey='" + workKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
