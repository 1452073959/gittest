package com.pay.brand.channel.sdk.cipher;


import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 国密 SM3 算法工具
 * <p>
 * SM3算法：
 * SM3杂凑算法是我国自主设计的密码杂凑算法，适用于商用密码应用中的数字签名和验证消息认证码的生成与验证以及随机数的生成，
 * 可满足多种密码应用的安全需求。
 * 为了保证杂凑算法的安全性，其产生的杂凑值的长度不应太短，
 * <p>
 * 例如:
 * MD5输出128比特杂凑值，输出长度太短，影响其安全性SHA-1算法的输出长度为160比特，
 * SM3算法的输出长度为256比特，因此SM3算法的安全性要高于MD5算法和SHA-1算法。
 *
 * @author wangyangyang
 * @date 2021-10-20
 */
public class SM3Utils {

    /**
     * 生成16进制的hash值
     *
     * @param data
     * @return HexString
     */
    public static String hashHexString(String data) {
        return Hex.toHexString(hash(data)).toUpperCase();
    }

    /**
     * 生成 hash 字节数据
     *
     * @param data
     * @return byte[]
     */
    public static byte[] hash(String data) {
        SM3Digest digest = new SM3Digest();
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 验证 hash
     *
     * @param data 明文
     * @param hash sm3 hash
     * @return Boolean
     */
    public static boolean verify(String data, String hash) {
        byte[] bytes1 = hash(data);
        byte[] bytes2 = Hex.decode(hash);
        if (Arrays.equals(bytes1, bytes2)) {
            return true;
        } else {
            return false;
        }
    }
}
