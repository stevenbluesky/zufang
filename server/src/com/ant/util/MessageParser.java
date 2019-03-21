package com.ant.util;

import com.alibaba.fastjson.JSONObject;

public class MessageParser{
	private String message ;
	private JSONObject parameter;	
	
	public MessageParser(String message,JSONObject parameter){
		super();
		this.message = message;
		this.parameter = parameter;
		if ( this.parameter == null )
			this.parameter = new JSONObject();
	}
	
	public String getMessage(){
		if( message != null && parameter != null ){
			for ( String key  : parameter.keySet()){
				String v = parameter.getString(key);
				if ( v == null )
					v = "" ;
				message = message.replaceAll( String.format("\\$\\{%s\\}", key), v );
			}
		}
		return message;
	}
	
	public String getMessageforLog(){
		if( message != null && parameter != null ){
			for ( String key  : parameter.keySet()){
				if ( "password".equals(key) || "code".equals(key))
					message = message.replaceAll( String.format("\\$\\{%s\\}", key), "*********");
				else 
					message = message.replaceAll( String.format("\\$\\{%s\\}", key), parameter.getString(key));
			}
		}
		return message;
	}	
	public JSONObject getParameter(){
		return parameter;
	}	
}
