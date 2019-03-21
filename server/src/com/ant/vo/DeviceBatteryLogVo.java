package com.ant.vo;

import java.util.Date;


/**
 * 通讯记录
 * @author aijian
 *
 */
public class DeviceBatteryLogVo extends CommonVo {

	private Long id;
	
	/**用户id*/
	private Long personId;
	
	/**电量*/
	private Integer battery;
	
	
	/**设备id*/
	private Long deviceId;
	
	/**插入时间*/
	private Date inputDate;	
	/**0 当天记录；1 最近一周；2 最近一个月*/
	private Integer queryFlag;
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(Integer queryFlag) {
		this.queryFlag = queryFlag;
	}
	
	
}
