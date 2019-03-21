package com.isurpass.message.processor;

import com.alibaba.fastjson.JSONObject;

public interface IMessageProcessor {

	void process(JSONObject message);
}
