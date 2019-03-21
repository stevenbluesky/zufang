package com.ant.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LockUserResultVo {

	private int resultCode;
	private String tid;
	private int usertype;
	private String username;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date validfrom;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date validthrough;
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getValidfrom() {
		return validfrom;
	}
	public void setValidfrom(Date validfrom) {
		this.validfrom = validfrom;
	}
	public Date getValidthrough() {
		return validthrough;
	}
	public void setValidthrough(Date validthrough) {
		this.validthrough = validthrough;
	}
	
	
}
