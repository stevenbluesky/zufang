package com.ant.vo;

import java.util.Date;

/**
 *  电价日志表
 * @author aijian
 *
 */
public class PowerPriceLogVo extends CommonVo {

	/**id*/
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
	
	private Integer devicetype ;
	private Integer devicesubtype;

	/**小区id*/
	private Long districtId;
	
	/**小区*/
	private String districtName;
	
	/**栋*/
	private String build;
	
	/**单元*/
	private String unit;
	
	/**楼*/
	private String floor;
	
	/**房间id*/
	private Long roomId;

	/**房间名称*/
	private String roomName;
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getRoomId() {
		return roomId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

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

	public Integer getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(Integer devicetype) {
		this.devicetype = devicetype;
	}

	public Integer getDevicesubtype() {
		return devicesubtype;
	}

	public void setDevicesubtype(Integer devicesubtype) {
		this.devicesubtype = devicesubtype;
	}
	
	
	
}
