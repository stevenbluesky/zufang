package com.isurpass.common.winxipay;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponsetoCharge 
{
	private int resultCode ;
	private String mweb_url;
	
	public ResponsetoCharge(int resultCode, String mweb_url) {
		super();
		this.resultCode = resultCode;
		this.mweb_url = mweb_url;
	}
	@XmlElement
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	@XmlElement
	public String getMweb_url() {
		return mweb_url;
	}
	public void setMweb_url(String mweb_url) {
		this.mweb_url = mweb_url;
	}
	
	
}
