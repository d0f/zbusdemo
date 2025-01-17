/**
 * Project Name: zbusdemo
 * File Name: HttpClientRpc.java
 * Package Name: cn.gnux.zbus.httpclient
 * Date: 2015年12月30日下午3:22:29
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.httpclient;

import java.util.Arrays;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.zbus.mq.Protocol;
import org.zbus.net.http.Message;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName:HttpClientRpc <br/>
 * Date:     2015年12月30日 下午3:22:29 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class HttpClientRpc {
	public static void main(String[] args) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost http = new HttpPost("http://localhost:15555"); 
		//zbus扩展HTTP头部协议，主要两个MQ和命令CMD
		http.addHeader(Message.CMD, Protocol.Produce);
		http.addHeader(Message.MQ, "MyRpc"); 
		
		//Rpc模式下需要设置ack为false, 等待的是service的回复
		http.addHeader(Message.ACK, "false");
		
		JSONObject json = new JSONObject();
		json.put("method", "echo");
		json.put("params", Arrays.asList("param1")); //params 数组
		StringEntity body = new StringEntity(json.toJSONString());
		http.setEntity(body);
		
		CloseableHttpResponse resp = httpClient.execute(http);
		HttpKit.printResponse(resp);
		resp.close();
		
		httpClient.close();
	}
}
