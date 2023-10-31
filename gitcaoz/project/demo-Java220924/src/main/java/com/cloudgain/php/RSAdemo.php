<?php

    xlzxop();
    function xlzxop()
    {
        
        require "RSAUtil1024.php";
        require "TokenUtils.php";
   
        //获取token地址
        $tokenUrl = "http://127.0.0.1:9017/api/oauth/anno/token";
        //打款接口地址
        $url = "http://127.0.0.1:9017/api/thirdparty/payment/V2/pay";

        
        //以下参数请找商务人员提供
        $username = "xxxxx";//账号
        $password = "xxxx";//密码
        $clientId = "xxxxx";//客户id
        $client_secret = "xxxx";//客户密钥
        $platformId = "xxxx";//平台id

        
        $tokenUtils = new \TokenUtils();
		//获取token
		$token = $tokenUtils->getToken($tokenUrl,$username,$password,$clientId,$client_secret);
		
		
		$data[platformId]=$platformId;//平台id，云获提供
		$data[merOrderNo]=time().rand(1000,9999);
		$data[inAcctName]="张三";//收款人姓名
		$data[inCidno]="xxxxx";//收款人身份证号码
		$data[inMobile]="xxxxx"; //收款人手机号
		$data[inAcctNo]="xxxxx"; //收款人银行卡号
		$data[amount]=200;//打款金额（分）
		$data[remark]="银行卡打款";//打款备注
		$data[cidAddress]="广东深圳";//打款备注
		
		
		//密钥存放地址
		$rsapath =	"E:/home/xlzx/key/test/";
		$XlDes = new \RSAUtil1024($rsapath);
	
		$publicKey = file_get_contents($rsapath."rsa_public_key_1024.pem");
		$privateKey = file_get_contents($rsapath."pkcs8_rsa_private_key_1024.pem");

	
		$rsa_encrypt = $XlDes->encrypt($data,$publicKey);
		

		$sign = $XlDes->wjSign($data,$privateKey);

		
		$fileBase64Str1 = imgToBase64("C:\Users\xiefeiqiang\Desktop\imagetest\b6f4cc4791900c34cbf7c5ccf8d5361.jpg");
		$fileBase64Str2 = imgToBase64("C:\Users\xiefeiqiang\Desktop\imagetest\b6f4cc4791900c34cbf7c5ccf8d5361.jpg");

		
		$post_data = array(
		    "encryptStr"=>$rsa_encrypt,
		    "signStr" => $sign,
		    "tenant"=>'MDAwMA==',//固定参数
		    'fileBase64Str1: '.$fileBase64Str1,
		    'fileBase64Str2: '.$fileBase64Str2,
	   
		);
		
		$authorization=  $tokenUtils->getAuthorization($clientId, $client_secret);
		
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
		curl_close($curl); 
		$back_res = json_decode($tmpInfo,true);
		var_dump($tmpInfo);
		
		var_dump($back_res['data']['encrypt']);
		
		$back_encrypt  = $XlDes->decrypt($back_res['data']['encrypt'],$privateKey);
		var_dump($back_encrypt);
		echo '打款返回结果'.$back_encrypt;
		
		
			
		
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
    
                $img_base64 = 'data:image/' . $img_type . ';base64,' . $file_content;//�ϳ�ͼƬ��base64����
    
            }
            fclose($fp);
        }
    
        return $img_base64; 
    }


?>