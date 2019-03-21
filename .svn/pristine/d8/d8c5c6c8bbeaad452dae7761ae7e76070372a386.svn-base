package com.ant.restful.service;

import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpUtilTest
{
	public static void main(String arg[])
	{
		try
		{
			System.out.println(1);
			JSONObject json = new JSONObject();
			json.put("code", "fafsafda");
			json.put("password", "fdafdafdsa");
			String rst = HttpUtilTest.httprequest("https://test.isurpass.com.cn/iremote/thirdpart/login", json);
			
			System.out.println(rst);
			System.out.println(2);
		}
		catch(Throwable t )
		{
			System.out.println(3);
			t.printStackTrace(System.out);
		}
		System.out.println(4);
	}
/*
    public final static void main(String[] args) throws Exception {
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, new AnyTrustStrategy())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpGet httpget = new HttpGet("https://iremote.isurpass.com.cn/iremote/thirdpart/login");

            System.out.println("Executing request " + httpget.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
    */
	private static Log log = LogFactory.getLog(HttpUtil.class);
	
	public static String httprequest(String url , Map pmap)
	{
		JSONObject json = JSON.parseObject(JSON.toJSONString(pmap));
		
		
		try 
		{
			HttpClient httpclient = createHttpClient(url);
			
			if ( log.isInfoEnabled() )
				log.info(json.toJSONString());
			
			HttpPost httpost = new HttpPost(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			for ( String key : json.keySet())
				nvps.add(new BasicNameValuePair(key, json.getString(key)));
			
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally 
		{
			
		}
		return "";
	}
	
	public static HttpClient createHttpClient(String url) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		if ( url.startsWith("https"))
		{
			 // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadTrustMaterial(null, new AnyTrustStrategy())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
	        return httpclient;
		}
		else 
			return HttpClientBuilder.create().build();
	}
	
	
}
