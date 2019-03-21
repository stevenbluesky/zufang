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
@Table(name="commuLog" )
public class CommuLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**用户id*/
	private Long personId;
	
	/**通讯内容*/
	private String commuText;
	
	/**设备id*/
	private Long deviceId;
	
	/**插入时间*/
	private Date inputDate;
	/**插入内容类型 1,打开设备2,关闭设备3,修改密码4,设置密码失效5,修改临时密码6,设置临时密码失效
		7,修改用户密码8,设置用户密码失效9,设置密码10,设置卡用户11,删除用户*/
	private Integer type1;
	/**0，失败 1，成功*/
	private Integer type2;
	private Integer operatepersonid;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Integer getType1() {
		return type1;
	}

	public void setType1(Integer type1) {
		this.type1 = type1;
	}

	public Integer getType2() {
		return type2;
	}

	public void setType2(Integer type2) {
		this.type2 = type2;
	}

	public Integer getOperatepersonid() {
		return operatepersonid;
	}

	public void setOperatepersonid(Integer operatepersonid) {
		this.operatepersonid = operatepersonid;
	}
	
}
