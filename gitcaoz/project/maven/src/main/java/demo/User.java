package demo;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.codec.binary.Base64;
public class User {

    public static void main(String[] args) throws Exception {
        // 公钥的 Base64 编码字符串
        String publicKeyBase64 = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcv6GX4LrgtL6jDXoStrpspCEZ\n" +
                "VSJUEq7lFqKl4ScXdQ9dIKFT4NMgjhuRhboZhQ8MsqzsDHTD76hfDCfCi7noG1vj\n" +
                "EwHHMfLEcDFKBIfa6O/iQStzC1ZcMRoLMgLgOG/Z+QHFjv1rYt+khJVn3kQPIk2W\n" +
                "y2hVRnUVgcjK/tr34wIDAQAB\n" +
                "-----END PUBLIC KEY-----";

        // 待验证的数据
        String dataToVerify = "要验证的数据";

        // 解码公钥
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // 创建 Signature 对象并初始化
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);

        // 设置要验证的数据
        signature.update(dataToVerify.getBytes());

        // 验证数字签名
        boolean verified = signature.verify(Base64.decodeBase64("数字签名Base64编码字符串"));

        if (verified) {
            System.out.println("数字签名验证成功");
        } else {
            System.out.println("数字签名验证失败");
        }
    }
}

    // 用响应数据data 和sign去谢签是否通过如果验签通过，对data参数解密即可String data = Dns3.decade("合利宝下发加密秘钥",body.&atstring("data"));

