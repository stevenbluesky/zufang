package com.ant.vo;

import java.util.Date;

/**
 * 开锁记录
 *
 */
public class OpenDeviceLogVo extends CommonVo {
	
	/**id*/
	private Long id;
	
	/**用户id*/
	private Long personId;
	
	/**设备id*/
	private Long deviceId;
	
	/**结果*/
	private String result;
	
	/**开门人*/
	private String operateName;

	
	/**开门时间*/
	private Date inputDate;

	/**0 当天记录；1 最近一周；2 最近一个月*/
	private Integer queryFlag;
	
	public Integer getQueryFlag() {
		return queryFlag;
	}


	public void setQueryFlag(Integer queryFlag) {
		this.queryFlag = queryFlag;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPersonId() {
		return personId;
	}


	public void setPersonId(Long personId) {
		this.personId = personId;
	}


	public Long getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getOperateName() {
		return operateName;
	}


	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}


	public Date getInputDate() {
		return inputDate;
	}


	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	
}
