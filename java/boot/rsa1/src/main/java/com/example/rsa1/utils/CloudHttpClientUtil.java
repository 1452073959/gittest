package com.example.rsa1.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CloudHttpClientUtil {

	private static final int timeOut = 30*1000;
	private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1.1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(400);//max connection
            cm.setDefaultMaxPerRoute(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * httpClient post请求
     * @param url 请求url
     * @param header 头部信息
     * @param param 请求参数 form提交适用
     * @param contentType
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String postMap(String  url, Map<String, String> header, Map<String, Object> param) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            RequestConfig requestConfig = RequestConfig.custom().
            		setSocketTimeout(timeOut).
            		setConnectTimeout(timeOut)
            		.setConnectionRequestTimeout(timeOut).build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 设置头信息
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // 设置请求参数
            if (param != null && param.size() > 0) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    //给参数赋值
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            // 设置实体 优先级高
//            if (entity != null) {
//                httpPost.setEntity(entity);
//            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            }else
            	readHttpResponse(httpResponse);
        } catch (Exception e) {
        	throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }
    
    /**
     * httpClient post请求
     * @param url 请求url
     * @param header 头部信息
     * @param contentType
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String post(String  url, Map<String, String> header, String json,String contentType) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            RequestConfig requestConfig = RequestConfig.custom().
            		setSocketTimeout(timeOut).
            		setConnectTimeout(timeOut)
            		.setConnectionRequestTimeout(timeOut).build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 设置头信息
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpPost.setHeader("Content-type", contentType);
            // 设置请求参数
            if(StringUtils.isNotBlank(json)){
            	StringEntity entity = new StringEntity(json, Consts.UTF_8);
                entity.setContentEncoding("UTF-8");
                httpPost.setEntity(entity);
            }
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            }else
            	readHttpResponse(httpResponse);
        } catch (Exception e) {
        	throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }
    
    
    /**
     * httpClient get请求
     * @param url 请求url
     * @param param 请求参数
     * @param contentType
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String get(String  url,Map<String, String> header, Map<String, String> param,String contentType) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            StringBuilder sbUrl = new StringBuilder();
        	sbUrl.append(url);
        	if (null != param) {
        		StringBuilder sbQuery = new StringBuilder();
            	for (Map.Entry<String, String> query : param.entrySet()) {
            		if (0 < sbQuery.length()) {
            			sbQuery.append("&");
            		}
            		if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
            			sbQuery.append(query.getValue());
                    }
            		if (!StringUtils.isBlank(query.getKey())) {
            			sbQuery.append(query.getKey());
            			if (!StringUtils.isBlank(query.getValue())) {
            				sbQuery.append("=");
            				sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
            			}        			
                    }
            	}
            	if (0 < sbQuery.length()) {
            		sbUrl.append("?").append(sbQuery);
            	}
            }
        	RequestConfig requestConfig = RequestConfig.custom().
        			setSocketTimeout(timeOut).
        			setConnectTimeout(timeOut)
        			.setConnectionRequestTimeout(timeOut).build();
        	HttpGet httpGet = new HttpGet(sbUrl.toString());
        	httpGet.setConfig(requestConfig);
        	// 设置头信息
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                	httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpGet.setHeader("Content-type", contentType);
            
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            }else
            	readHttpResponse(httpResponse);
        } catch (Exception e) {
        	throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }
    
    public static CloseableHttpClient getHttpClient() throws Exception {
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .setConnectionManager(cm)
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))	//不使用重试机制
//                .setConnectionManagerShared(true)
//                .build();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient;
    }
    
    public static String readHttpResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        return builder.toString();
    }
}
