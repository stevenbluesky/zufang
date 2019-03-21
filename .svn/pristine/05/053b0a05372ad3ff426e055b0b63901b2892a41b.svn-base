package com.ant.business;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 电价日志表
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="powerPriceLog" )
public class PowerPriceLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**月份，201401*/
	private String month;
	
	/**上个月度数*/
	private Float preMonthDegrees;
	
	/**当前度数*/
	private Float currentDegrees;
	
	/**本月用电量*/
	private Float monthDegrees;
	
	/**电表底数*/
	private Float baseDegrees;
	
	/**合计用电量*/
	private Float sumDegrees;
	
	/**电价 以分为单位*/
	private Integer price;
	
	/**均摊费用 以分为单位*/
	private Integer shareAmount;
	
	/**本月电费合计 以分为单位*/
	private Integer sumAmount;
	
	/**插入时间*/
	private Date inputDate;
	
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Float getPreMonthDegrees() {
		return preMonthDegrees;
	}

	public void setPreMonthDegrees(Float preMonthDegrees) {
		this.preMonthDegrees = preMonthDegrees;
	}

	public Float getCurrentDegrees() {
		return currentDegrees;
	}

	public void setCurrentDegrees(Float currentDegrees) {
		this.currentDegrees = currentDegrees;
	}

	public Float getMonthDegrees() {
		return monthDegrees;
	}

	public void setMonthDegrees(Float monthDegrees) {
		this.monthDegrees = monthDegrees;
	}

	public Float getBaseDegrees() {
		return baseDegrees;
	}

	public void setBaseDegrees(Float baseDegrees) {
		this.baseDegrees = baseDegrees;
	}

	public Float getSumDegrees() {
		return sumDegrees;
	}

	public void setSumDegrees(Float sumDegrees) {
		this.sumDegrees = sumDegrees;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(Integer shareAmount) {
		this.shareAmount = shareAmount;
	}

	public Integer getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Integer sumAmount) {
		this.sumAmount = sumAmount;
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
