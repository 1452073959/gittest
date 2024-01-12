package com.example.rsa1.utils;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {

    /** 指定加密算法为RSA */
    private static String ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    private static String PADDING_MODE = "RSA/ECB/PKCS1Padding";
    
    /** 指定key的大小 */
    private static int KEYSIZE = 2048;
    /** 指定公钥存放文件 */
    private static String PUBLIC_KEY_FILE = "PublicKey";
    /** 指定私钥存放文件 */
    private static String PRIVATE_KEY_FILE = "PrivateKey";
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 247;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥):秘钥位数 2048
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> genKeyPair() throws Exception {
    	
    	
//      // /** RSA算法要求有一个可信任的随机数源 */
      SecureRandom secureRandom = new SecureRandom();
      /** 为RSA算法创建一个KeyPairGenerator对象 */
      KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance(KEY_ALGORITHM);
      /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
      keyPairGenerator.initialize(KEYSIZE, secureRandom);
//      /** 生成密匙对 */
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      Map<String, String> keyMap = new HashMap<String, String>();
//      /** 得到公钥 */
      BASE64Encoder encoder = new BASE64Encoder();
      keyMap.put("public", new String(encoder.encode(keyPair.getPublic().getEncoded()).getBytes(), "UTF-8"));
      keyMap.put("private", new String(encoder.encode(keyPair.getPrivate().getEncoded()).getBytes(), "UTF-8"));
      return keyMap;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = decryptBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);// TODO ALGORITHM
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(PADDING_MODE);
        // Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }



    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey,String PADDING_MODE) throws Exception {
        byte[] keyBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(PADDING_MODE);
        // Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = decryptBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 公钥加密
     * 
     * @param encryptContent
     *            加密内容
     * @param publicKey
     *            公钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String encryptContent, String publicKey,String PADDING_MODE) throws Exception {
        byte[] data = encryptContent.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey,PADDING_MODE);
        return encryptBASE64(encodedData);
    }

    /**
     * 私钥解密
     * 
     * @param decodeContent
     *            需解密内容
     * @param privateKey
     *            私钥
     * @return
     * @throws Exception
     */
    public static String decode(String decodeContent, String privateKey) throws Exception {
        byte[] decry = decryptBASE64(decodeContent);
        byte[] decodedData = RSAUtils.decryptByPrivateKey(decry, privateKey);
        String target = new String(decodedData);
        return target;
    }

    /**
     * 生成密钥对
     */
    public static void generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        /** 用对象流将生成的密钥写入文件 */
        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
        try {
            oos1.writeObject(publicKey);
            oos2.writeObject(privateKey);
        } catch (Exception e) {
           throw e;
        } finally {
            /** 清空缓存，关闭文件输出流 */
            oos1.close();
            oos2.close();
        }

    }

    /**
     * 产生签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 验证签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * BASE64解密
     *
     * @param decodeStr
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String decodeStr) throws Exception {
        return Base64Utils.decodeStringToByte(decodeStr);
    }

    /**
     * BASE64解密
     *
     * @param decodeStr
     * @return
     * @throws Exception
     */
    public static String decryptBASE64StringToString(String decodeStr) throws Exception {
        return Base64Utils.decodeStringToString(decodeStr);
    }

    /**
     * BASE64加密
     *
     * @param encodeByte
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] encodeByte) throws Exception {
        return Base64Utils.encodeByteToString(encodeByte);
    }

    /**
     * BASE64加密
     *
     * @param encodeByte
     * @return
     * @throws Exception
     */
    public static String encryptBASE64StrintToString(String encodeByte) throws Exception {
        return Base64Utils.encodeStrintToString(encodeByte);
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = decryptBASE64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey)keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = decryptBASE64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char)(((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char)(val > 9 ? val + 'A' - 10 : val + '0');

            val = (char)(bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char)(val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte)(asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte)(asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte)(asc - 'a' + 10);
        else
            bcd = (byte)(asc - 48);
        return bcd;
    }

    /**
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     * 
     * @throws UnsupportedEncodingException
     */
    public static String unicode2String(String unicode) throws UnsupportedEncodingException {

        String result = URLDecoder.decode(unicode, "UTF-8");
        return result;
    }
    public static void main(String[] args) throws Exception {
    	Map<String, String> rs = genKeyPair();
    	System.out.println("private:");
    	System.out.println(rs.get("private"));
    	System.out.println("public:");
    	System.out.println(rs.get("public"));
	}
}