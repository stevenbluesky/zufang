package com.ant.vo;

import java.util.Date;


/**
 * 通讯记录
 * @author aijian
 *
 */
public class CommuLogVo extends CommonVo {
	
	/**id*/
	private Long id;

	
	/**用户id*/
	private Long personId;
	
	/**通讯内容*/
	private String commuText;
	
	/**设备id*/
	private Long deviceId;
	
	/**插入时间*/
	private Date inputDate;
	
	/**0 当天记录；1 最近一周；2 最近一个月*/
	private Integer queryFlag;
	
	/**操作名称*/
	private String operateName;

	
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

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

	public String getCommuText() {
		return commuText;
	}

	public void setCommuText(String commuText) {
		this.commuText = commuText;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
}
