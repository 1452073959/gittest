<?php 

/**
 * token工具类
 * @author xiefeiqiang
 *
 */

class TokenUtils {
   
    
    /**
     * 获取token
     * @param unknown $url 
     * @param unknown $username
     * @param unknown $password
     * @param unknown $clientId
     * @param unknown $client_secret
     * @return string
     */
    function getToken($url,$username,$password,$clientId,$client_secret){
    
        $post_data = array(
            "account" => $username,
            "grantType" => "password",
            "password"=>$password,
        );
    
        $authorization = $this->getAuthorization($clientId,$client_secret);
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
     * 获取Authorization
     * @param unknown $clientId
     * @param unknown $client_secret
     * @return string
     */
    function getAuthorization($clientId, $client_secret)
    {
        $uniqueFlag=$clientId.":".$client_secret;
        $uniqueFlag = "Basic ".base64_encode($uniqueFlag);
        return $uniqueFlag;
    }
     
    
}

?>