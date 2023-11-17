package zcc.testbook.controller;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.core.util.StrUtil;
public class SM4Utils {
    //key必须是16字节，即128位
    final static String key = "testsm4key123456";

    //指明加密算法和秘钥
    static SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());

    /**
     * 加密为16进制，也可以加密成base64/字节数组
     *
     * @param plaintext
     * @return
     */
    public static String encryptSm4(String plaintext) {
        if (StrUtil.isBlank(plaintext)) {
            return "";
        }
        return sm4.encryptHex(plaintext);
    }

    /**
     * 解密
     *
     * @param ciphertext
     * @return
     */
    public static String decryptSm4(String ciphertext) {
        if (StrUtil.isBlank(ciphertext)) {
            return "";
        }
        return sm4.decryptStr(ciphertext);
    }

}
