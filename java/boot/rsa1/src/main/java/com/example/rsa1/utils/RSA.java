package com.example.rsa1.utils;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {
	
	
	private static final String KEY_ALGORITHM = "RSA";  
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";  
  
    private static final String PUBLIC_KEY = "RSAPublicKey";  
    private static final String PRIVATE_KEY = "RSAPrivateKey";  
    
    //private static final int KEY_LENGTH = 1024;
    //private static final int MAX_ENCRYPT_BLOCK = 117; 
    //private static final int MAX_DECRYPT_BLOCK = 128;
  
    private static final int KEY_LENGTH = 1024 * 2;
    private static final int MAX_ENCRYPT_BLOCK = 128 * 2 - 11; 
    private static final int MAX_DECRYPT_BLOCK = 128 * 2;
    
	public static void main(String[] args) throws Exception {
//    	Map<String, Object> merKeyMap = RSA.initKey();  
//        //公钥
//    	String merPublicKey = RSA.getPublicKey(merKeyMap);  
//    	//私钥
//    	String merPrivateKey = RSA.getPrivateKey(merKeyMap);  
//
//        System.out.println("公钥: \n" + new String(merPublicKey.getBytes()).replaceAll("\r\n", ""));  
//        System.out.println("私钥： \n" + new String(merPrivateKey.getBytes()).replaceAll("\r\n", ""));
//        
        
		JSONObject body = new JSONObject();
		body.put("action", "trans");
		for (int i = 0; i < 100; i++) {
			body.put("action"+i, "trans"+System.currentTimeMillis()+System.currentTimeMillis()+System.currentTimeMillis()+System.currentTimeMillis()+System.currentTimeMillis()+System.currentTimeMillis()+System.currentTimeMillis());
		}
		String pubString  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmU8MIYoQ3tfTeBAM6oLPewv2jDxzS2rjc9PZm9WdjfgL08sYG9aOn1uGFCFp3gJ07c+ZbGRmj49bPpD7oCS6CmmmtRi2wNWUZcjQaduLMvAIQ2KstfdDhfxDiSnYszunqxw8D1CEcWAnKC4+F9FRZPlYTF3CCiigHIvr1++vVjnRRutmYW9tlOx31Hbn7WYQ8HLHLzIPptgGP/blMVqoNwDakOogQ66OghGZ1BkE0wir1nPfAaGY1WN3y4E2L68/qUpxYfFr6SCCM8JWwG6A1dfM5FalbW9ESYCL4IxaVdNq1A6i5KV+UaAjhYImurYk3BKu2W96SP4MAQ31Z12TUQIDAQAB";
		encryptByPublicKey(body.toString().getBytes(), pubString);
		//System.out.println(senddata);
        
	}
    
    /** 
     * 用私钥对信息生成数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param privateKey 
     *            私钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String sign(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = Base64.decryptBASE64(privateKey);  
  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(priKey);  
        signature.update(data);  
  
        return Base64.encryptBASE64(signature.sign());  
    }  
  
    /** 
     * 校验数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param publicKey 
     *            公钥 
     * @param sign 
     *            数字签名 
     *  
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *  
     */  
    public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
  
        // 解密由base64编码的公钥  
        byte[] keyBytes = Base64.decryptBASE64(publicKey);  
  
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
        return signature.verify(Base64.decryptBASE64(sign));  
    }  
  
    /** 
     * 解密<br> 
     * 用私钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPrivateKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = Base64.decryptBASE64(key);  
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
  
        //return cipher.doFinal(data);  
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        //int MAX_ENCRYPT_BLOCK = 117; 
        int inputLen = data.length; 
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        return decryptedData;
        
        /*
        int encryptedFileBytesChunkLength = 128;
        int numberOfEncryptedChunks = data.length / encryptedFileBytesChunkLength;

        //The limit per chunk is 117 bytes for RSA
        int decryptedFileBytesChunkLength = 100;
        int decryptedFileBytesLength = numberOfEncryptedChunks * encryptedFileBytesChunkLength;
        //It looks like we must create the decrypted file as long as the encrypted since RSA need 128 for output

        //Create the decoded byte array
        byte[] decryptedFileBytes = new byte[decryptedFileBytesLength];

        //Counters
        int decryptedByteIndex = 0;
        int encryptedByteIndex = 0;

        for(int i = 0; i < numberOfEncryptedChunks; i++) {
        	if( i < numberOfEncryptedChunks -1 ) {
        		decryptedByteIndex = decryptedByteIndex + cipher.doFinal(data, encryptedByteIndex, encryptedFileBytesChunkLength, decryptedFileBytes, decryptedByteIndex);
        		encryptedByteIndex = encryptedByteIndex + encryptedFileBytesChunkLength;
        	}
        	else {
        		decryptedByteIndex = decryptedByteIndex + cipher.doFinal(data, encryptedByteIndex, data.length - encryptedByteIndex, decryptedFileBytes, decryptedByteIndex);
        	}
        }
        return decryptedFileBytes;
        */
    }  
  
    /** 
     * 解密<br> 
     * 用公钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPublicKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = Base64.decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
  
        //return cipher.doFinal(data);  
        
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        //int MAX_ENCRYPT_BLOCK = 117; 
        int inputLen = data.length; 
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        return decryptedData;
    }  
  
    /** 
     * 加密<br> 
     * 用公钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPublicKey(byte[] data, String key)  
            throws Exception {  
        // 对公钥解密  
        byte[] keyBytes = Base64.decryptBASE64(key);  
  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
  
        //return cipher.doFinal(data);  
        
        /*
        //The limit per chunk is 117 bytes for RSA
        int decryptedFileBytesChunkLength = 100;
        int numberenOfDecryptedChunks = (data.length-1) / decryptedFileBytesChunkLength + 1;

        //RSA need 128 bytes for output
        int encryptedFileBytesChunkLength = 128;
        int encryptedFileBytesLength = numberenOfDecryptedChunks * encryptedFileBytesChunkLength;

        //Create the encoded byte array
        byte[] encryptedBytes = new byte[ encryptedFileBytesLength ];

        //Counters
        int decryptedByteIndex = 0;
        int encryptedByteIndex = 0;

        for(int i = 0; i < numberenOfDecryptedChunks; i++) {
        	if(i < numberenOfDecryptedChunks - 1) {
        		encryptedByteIndex = encryptedByteIndex + cipher.doFinal(data, decryptedByteIndex, decryptedFileBytesChunkLength, encryptedBytes, encryptedByteIndex);
                decryptedByteIndex = decryptedByteIndex + decryptedFileBytesChunkLength;
        	}
        	else {
        		cipher.doFinal(data, decryptedByteIndex, data.length - decryptedByteIndex, encryptedBytes, encryptedByteIndex);
        	}
        }
        return encryptedBytes;
        */
        /*
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        int MAX_DECRYPT_BLOCK = 128;
        int MAX_ENCRYPT_BLOCK = 117; 
        int inputLen = data.length; 
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        return decryptedData;
        */


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
     * 加密<br> 
     * 用私钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPrivateKey(byte[] data, String key)  
            throws Exception {  
        // 对密钥解密  
        byte[] keyBytes = Base64.decryptBASE64(key);  
  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
  
        //return cipher.doFinal(data);  
        
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
     * 取得私钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
  
        return Base64.encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 取得公钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
  
        return Base64.encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 初始化密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> initKey() throws Exception {  
        KeyPairGenerator keyPairGen = KeyPairGenerator  
                .getInstance(KEY_ALGORITHM);  
        keyPairGen.initialize(KEY_LENGTH);  
  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
  
        // 公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
  
        // 私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }
}