package com.cloudgain.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author Huangdaye
 * @Desc config.properties配置读取
 * @Date 2020/7/8 15:27
 */
public class CommonConfig {

    /** 客户私钥 **/
    public static String PRIVATE_KEY="";

    /**  云获公钥 **/
    public static String PUBLIC_KEY="";

    /**  云获配置生成的clientId **/
    public static String CLIENT_ID="";

    /**  云获配置生成的client_secret **/
    public static String CLIENT_SECRET="";

    /**  token请求的url **/
    public static String TOKEN_URL="";

    static {
        InputStream in = CommonConfig.class.getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PRIVATE_KEY = p.getProperty("privateKey");
        PUBLIC_KEY = p.getProperty("publicKey");
        CLIENT_ID=p.getProperty("client_id");
        CLIENT_SECRET=p.getProperty("client_secret");
        TOKEN_URL=p.getProperty("token_url");
    }

    public static void main(String[] args) {
        System.out.println(PRIVATE_KEY);
        System.out.println(PUBLIC_KEY);
        System.out.println(CLIENT_ID);
        System.out.println(CLIENT_SECRET);
        System.out.println(TOKEN_URL);
    }

}
