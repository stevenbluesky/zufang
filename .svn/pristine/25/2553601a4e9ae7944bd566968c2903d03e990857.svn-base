package com.ant.business;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@javax.persistence.Entity 
@Table(name="smshistory" )
public class SmsHistory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long smshistoryid;
	private String countrycode;
	private String phonenumber ;
	private String message ;
	private int status ;
	private Date createtime = new Date();
	private Date sendtime ;
	private Long personId;
	private Long districtId;
	private String districtName;
	private String build;
	private String unit;
	private String floor;
	private String roomName;

	public Long getSmshistoryid() {
		return smshistoryid;
	}
	public void setSmshistoryid(Long smshistoryid) {
		this.smshistoryid = smshistoryid;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public Long getPersonId()
	{
		return personId;
	}
	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}
	public Long getDistrictId()
	{
		return districtId;
	}
	public void setDistrictId(Long districtId)
	{
		this.districtId = districtId;
	}
	public String getBuild()
	{
		return build;
	}
	public void setBuild(String build)
	{
		this.build = build;
	}
	public String getUnit()
	{
		return unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	public String getFloor()
	{
		return floor;
	}
	public void setFloor(String floor)
	{
		this.floor = floor;
	}
	public String getRoomName()
	{
		return roomName;
	}
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}
	public String getDistrictName()
	{
		return districtName;
	}
	public void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}
	
}
