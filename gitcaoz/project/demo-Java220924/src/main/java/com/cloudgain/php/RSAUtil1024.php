<?php
/**
 * RSA加密解密工具类
 * @author xiefeiqiang
 *
 */
class RSAUtil1024 {
    
    //php openssl(SHA1WithRSA) 签名
    public function __construct($path){
        $this->rsapath = $path;
    }
    public function wjSign($params,$privateKey){
        //解决中文乱码问题
        foreach ( $params as $key => $value ) {
            $params[$key] = urlencode ( $value );
        }
        $data = urldecode (json_encode ( $params )) ;
        
        openssl_sign($data, $sign, $privateKey, OPENSSL_ALGO_MD5);
        $sign = base64_encode($sign);
        return $sign;
    }
  
   /**
     * 对参数进行加密
     *
     * @param $params 待加密参数数组
     * @param $publicKey 对端的公钥
     * @return  string base64字符串
     */
    function encrypt($params, $publicKey)
    {
        //解决中文乱码问题
        foreach ( $params as $key => $value ) {
            $params[$key] = urlencode ( $value );
        }
        $_rawData = urldecode (json_encode ( $params ) );

        $_encryptedList = array();
        $_step = 117;
        $_encrypted = '';
        for ($_i = 0, $_len = strlen($_rawData); $_i < $_len; $_i += $_step) {
            $_data = substr($_rawData, $_i, $_step);
            $_encrypted = '';

            openssl_public_encrypt($_data, $_encrypted, $publicKey);
            $_encryptedList [] = ($_encrypted);
        }
        $_data = base64_encode(join('', $_encryptedList));
        return $_data;
    }
  


    /**
     * 对参数进行解密
     *
     * @param $encryptedData 待解密参数
     * @param $privateKey 自己的私钥
     * @return string
     */
    function decrypt($encryptedData, $privateKey)
    {
        $_encryptedData = base64_decode($encryptedData);

        $_decryptedList = array();
        $_step = 128;
        if (strlen($privateKey) > 1000) {
            $_step = 256;
        }
        for ($_i = 0, $_len = strlen($_encryptedData); $_i < $_len; $_i += $_step) {
            $_data = substr($_encryptedData, $_i, $_step);
            $_decrypted = '';
            openssl_private_decrypt($_data, $_decrypted, $privateKey);
            $_decryptedList [] = $_decrypted;
        }

        return join('', $_decryptedList);
    }
}
?>