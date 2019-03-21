package com.ant.business;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 通讯记录
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="openDeviceLog" )
public class OpenDeviceLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**用户id*/
	private Long personId;
	
	private Integer operatepersonid;
	/**设备id*/
	private Long deviceId;
	
	/**结果*/
	private String result;
	
	/**开门人*/
	private String operateName;

	
	/**开门时间*/
	private Date inputDate;
	/**操作类型*/
	private String type;
	private Integer type2;
	private String msg1;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMsg1() {
		return msg1;
	}


	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}


	public Integer getType2() {
		return type2;
	}


	public void setType2(Integer type2) {
		this.type2 = type2;
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


	public Integer getOperatepersonid() {
		return operatepersonid;
	}


	public void setOperatepersonid(Integer operatepersonid) {
		this.operatepersonid = operatepersonid;
	}

}
