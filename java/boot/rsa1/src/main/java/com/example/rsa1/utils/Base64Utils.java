package com.example.rsa1.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Utils {

    /**
     * base64 加密
     * 
     * @param encodeStr
     *            加密内容
     * @return string
     * @throws UnsupportedEncodingException
     */
    public static String encodeStrintToString(String encodeStr) throws UnsupportedEncodingException {
        byte[] b = encodeStr.getBytes("UTF-8");
        Base64 base64 = new Base64();
        b = base64.encode(b);
        return new String(b, "UTF-8");
    }

    /**
     * base64 加密
     * 
     * @param encodeByte
     *            加密字节内容
     * @return string
     * @throws UnsupportedEncodingException
     */
    public static String encodeByteToString(byte[] encodeByte) throws UnsupportedEncodingException {
        Base64 base64 = new Base64();
        byte[] b = base64.encode(encodeByte);
        return new String(b, "UTF-8");
    }

    /**
     * base64 加密
     * 
     * @param encodeStr
     *            加密内容
     * @return byte
     * @throws UnsupportedEncodingException
     */
    public static byte[] encodeStringToByte(String encodeStr) throws UnsupportedEncodingException {
        byte[] b = encodeStr.getBytes("UTF-8");
        Base64 base64 = new Base64();
        return base64.encode(b);
    }

    /**
     * base64 加密
     * 
     * @param encodeByte
     *            加密字节内容
     * @return byte
     * @throws UnsupportedEncodingException
     */
    public static byte[] encodeByteToByte(byte[] encodeByte) throws UnsupportedEncodingException {
        Base64 base64 = new Base64();
        return base64.encode(encodeByte);
    }

    /**
     * base64 解密
     * 
     * @param decodeStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeStringToString(String decodeStr) throws UnsupportedEncodingException {
        byte[] b = decodeStr.getBytes("UTF-8");
        Base64 base64 = new Base64();
        b = base64.decode(b);
        String s = new String(b, "UTF-8");
        return s;
    }

    /**
     * base64 解密
     * 
     * @param decodeByte
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeByteToString(byte[] decodeByte) throws UnsupportedEncodingException {
        Base64 base64 = new Base64();
        byte[] b = base64.decode(decodeByte);
        return new String(b, "UTF-8");
    }

    /**
     * base64 解密
     * 
     * @param decodeStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] decodeStringToByte(String decodeStr) throws UnsupportedEncodingException {
        byte[] b = decodeStr.getBytes("UTF-8");
        Base64 base64 = new Base64();
        return base64.decode(b);
    }

    /**
     * base64 解密
     * 
     * @param decodeByte
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] decodeByteToByte(byte[] decodeByte) throws UnsupportedEncodingException {
        Base64 base64 = new Base64();
        return base64.decode(decodeByte);
    }
}