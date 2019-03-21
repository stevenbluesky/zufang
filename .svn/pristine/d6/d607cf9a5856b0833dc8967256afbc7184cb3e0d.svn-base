package com.ant.business;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 电表度数时报表
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="deviceDegreesHourLog" )
public class DeviceDegreesHourLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**电表度数*/
	private Float degrees;
	
	/**插入时间*/
	private Date inputDate;
	
	/**时间 2016052501*/
	private String hour;
	
	/**用户id*/
	private Long personId;
	
	/**设备id*/
	private Long deviceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Float getDegrees() {
		return degrees;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public void setDegrees(Float degrees) {
		this.degrees = degrees;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
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
	
}
