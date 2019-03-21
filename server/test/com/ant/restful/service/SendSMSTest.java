package com.ant.restful.service;

import java.util.HashMap;
import java.util.Map;

import com.ant.config.MjConfig;

public class SendSMSTest {

	public static void main(String[] args) {
		
		String text = "您已成功预订万达酒店，101房间，入住时间为：从2019-01-01 01:01:01到2019-08-08 08:08:08，开门密码为233233";
		Map<String , Object> pmap = new HashMap<String , Object>();
		pmap.put("countrycode", 86);
		pmap.put("message", text);
		pmap.put("phonenumber", "15820751127");
		String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/sendsms", pmap);
		System.out.println(str);
	}
}
