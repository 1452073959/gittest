<?php

	header("Content-Type:text/html;charset=utf-8");
    xlzxop();
    
    

    function xlzxop()
    {
        

        //获取token地址
        $tokenUrl="http://123.58.32.120:9017/api/oauth/anno/token";
        
        //以下参数请找商务人员提供
        $username="TestAcount5@yunhuo";//账号
        $password="yp92641717";//密码
        $clientId="kdirhn32iu8eap6xodbkd6i4";//客户id
        $client_secret="9kptv32ur57jp4t6w2ohbk3jrgomh9p1";//客户密钥
        

		//获取token
		$token = getToken($tokenUrl,$username,$password,$clientId,$client_secret);
		// echo $token;exit;
		
		$data['platformId']="20210610434423565368963072";//平台id，云获提供
		$data['merOrderNo']=time().rand(1000,9999);
		$data['inAcctName']="桂荣";//收款人姓名
		$data['inCidno']="522622199210104517";//收款人身份证号码
		$data['inMobile']="15220228230"; //收款人手机号
		$data['inAcctNo']="6228480128020121579"; //收款人银行卡号
		$data['amount']=100;//打款金额（分）
		$data['remark']="银行卡打款";//打款备注
		$data['cidAddress']="广东深圳";//打款备注

		// echo $data['merOrderNo'];
		
		
		//密钥存放地址
		// $rsapath =	"";
		// $XlDes = new \RSAUtil1024($rsapath);
	
		// $publicKey = file_get_contents($rsapath."rsa_public_key_2048.pem");
		// $privateKey = file_get_contents($rsapath."pkcs8_rsa_private_key_2048.pem");
		$publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMOjbCr5G/YRCVeyhQWkUY6QhgbbQPjN2Ut3vA3yHGRRb+VuH/DBwTDi65QbP2ykzldlm27Ck6eQCHWxjlHwJfB1zIfNekfsXW50XUqdtCC6EWd7e7bpA5jWQmsiQKCAJa9O7JsdCJJ9kqS/vOfk/NPqILKd4wxtFEsF0EOSXMswIDAQAB';
		$privateKey = 'MIICXAIBAAKBgQCF/3ppmXxLVpo5irt5eFs1nPQ+5J0/nLxzdwsyXTcYFAqdBLUUKBnmfgZRFHUcoFycA2vYcAUHmLQA3pqEPxDAsZYayyDpHYFWO+4NHwlxPZ7DioVFVeyK+aP0LQ8qHauV3H++5QWwHwEKQV/f+YR9rwVcNRPPLX4vcsTaNPTkIwIDAQABAoGAHnAtN2WlJVvzxpRsB6i+V0xi8xJtYfDRogztnzArfq22x3o1Q/HkCEaiWrKh87G9t7bTPaQckGAwq23lfZrxy+8qWOTVBTm2nxoj5Nm7kbvMOhMJNDgmvZ/Q3Vvut7y/i8GD+5xu539h9chhdrZIrpz8Tui0VM4sZx3DW1Z/dwkCQQDOKH64UaAjTuMXKXUQKYvVNy2BRkm/c4/goyx1vj4gAdZVSomUmJO2cHGOzlMLrxpckncyZbfuhs4PGA3VKKbXAkEApmTbE8qW6kaok4vHIWwN7thlOo9afNEHgPaC0HkL2PG9ckRxIfQENV0kfzMBDzRhsbMxabuZJ5fVUse50m5flQJAeJu+Tw0J974eIx3Q66yyrwGB8MBzQrtT8CLmTtgGtmj8wJdD2BhPSouME/TnJs3BSWkyrNxPeA+Lm88BwLPwrwJAMZclY8ShLblZTIAAHQnjLSf5I3bN9R0nK1xMqG0rBsElQQT84q2jA3R7CB3fVvS5mQha1bQ/dPlfbohv7TNP8QJBAIwuUJkl/CKkPvNbN8SJvQw3Xj4LsWQNbxGK/S/UGV0EskfEbsuAVEgvq7cGgozs4tBc1wXBCtrXkJM4HXFvZck=';

		$publicKey = "-----BEGIN PUBLIC KEY-----\n" . wordwrap($publicKey, 64, "\n", true) . "\n-----END PUBLIC KEY-----";
		$privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" . wordwrap($privateKey, 64, "\n", true) . "\n-----END RSA PRIVATE KEY-----";

	
		$rsa_encrypt = encrypt($data,$publicKey);
		
	
		$sign = wjSign($data,$privateKey);
		
		
		
		
		// $fileBase64Str1 = imgToBase64("C:\Users\xiefeiqiang\Desktop\imagetest\b6f4cc4791900c34cbf7c5ccf8d5361.jpg");
		// $fileBase64Str2 = imgToBase64("C:\Users\xiefeiqiang\Desktop\imagetest\b6f4cc4791900c34cbf7c5ccf8d5361.jpg");
		$fileBase64Str1 = '';
		$fileBase64Str2 = '';

		$post_data = array(
		    "encryptStr"=>$rsa_encrypt,
		    "signStr" => $sign,
		    "tenant"=>'MDAwMA==',//固定参数
		    'fileBase64Str1'=>$fileBase64Str1,
		    'fileBase64Str2'=>$fileBase64Str2,
	   
		);

		$authorization=  getAuthorization($clientId, $client_secret);
		$header_data = array(
		    'token: '.$token,
		    'tenant: MDAwMA==',//固定参数
		    'Authorization: '.$authorization
		);

		//打款接口地址
		$url = "http://123.58.32.120:9017/api/thirdparty/payment/V2/pay";

		echo "<pre>";
		var_dump($header_data);
		var_dump($post_data);
		// exit;
		
		$curl = curl_init(); 
		curl_setopt($curl, CURLOPT_URL, $url); 
		curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE); 
		curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE); 
		curl_setopt($curl, CURLOPT_POST, true); 
		curl_setopt($curl, CURLOPT_POSTFIELDS, $post_data); 
		curl_setopt($curl, CURLOPT_TIMEOUT, 50000); 
		curl_setopt($curl,CURLOPT_RETURNTRANSFER,true);
		curl_setopt($curl, CURLOPT_HTTPHEADER, array(
		    'token: '.$token,
		    'tenant: MDAwMA==',//固定参数
		    'Authorization: '.$authorization)
		);
		
		$tmpInfo = curl_exec($curl); 
		var_dump($tmpInfo);

		curl_close($curl); 
		$back_res = json_decode($tmpInfo,true);
		
		$back_encrypt  = decrypt($back_res['data']['encrypt'],$privateKey);
		
		echo '打款返回结果'.$back_encrypt;
		
		
    }

    function getToken($url,$username,$password,$clientId,$client_secret){

        $post_data = array(
            "account"=>$username,
            "grantType" => "password",
            "password"=>$password,
        );
        
        $authorization = getAuthorization($clientId,$client_secret);
        $headers = array(
            "Content-Type" => 'application/json',
            "tenant" => "MDAwMA==",
            "Authorization"=>$authorization,
        );
       
        
       	$curl = curl_init(); 
		curl_setopt($curl, CURLOPT_URL, $url); 
		curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE); 
		curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE); 
		curl_setopt($curl, CURLOPT_POST, true); 
		curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "POST");
		curl_setopt($curl, CURLOPT_POSTFIELDS, json_encode($post_data)); 
		curl_setopt($curl, CURLOPT_TIMEOUT, 50000);
		curl_setopt($curl,CURLOPT_RETURNTRANSFER,true);	
		curl_setopt($curl, CURLOPT_HEADER, 0);
		curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                'Content-Type: application/json',
		         'tenant: MDAwMA==',
                 'Authorization: '.$authorization)
            );
		
		$tmpInfo = curl_exec($curl);
		$data = json_decode($tmpInfo, true);
		return "Bearer ".$data['data']['token'];
    } 
    
    /**
     * 图片base64处理
     * @date 2017-02-20 19:41:22
     *
     * @param $img_fileַ
     *
     * @return string
     */
    function imgToBase64($img_file) {
    
        $img_base64 = '';
        if (file_exists($img_file)) {
            $app_img_file = $img_file; 
            $img_info = getimagesize($app_img_file); 
    
           
            $fp = fopen($app_img_file, "r"); 
    
            if ($fp) {
                $filesize = filesize($app_img_file);
                $content = fread($fp, $filesize);
                $file_content = chunk_split(base64_encode($content)); 
                switch ($img_info[2]) {          
                    case 1: $img_type = "gif";
                    break;
                    case 2: $img_type = "jpg";
                    break;
                    case 3: $img_type = "png";
                    break;
                }
    
                $img_base64 = 'data:image/' . $img_type . ';base64,' . $file_content;
    
            }
            fclose($fp);
        }
    
        return $img_base64; 
    }

    /**
     * 获取Authorization
     *
     * @param $clientId 
     * @param $client_secret 
     * @return string
     */
    function getAuthorization($clientId, $client_secret)
    {
        $uniqueFlag=$clientId.":".$client_secret;
        $uniqueFlag = "Basic ".base64_encode($uniqueFlag);
        return $uniqueFlag; 
    }

    function wjSign($params,$privateKey){
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


?>

