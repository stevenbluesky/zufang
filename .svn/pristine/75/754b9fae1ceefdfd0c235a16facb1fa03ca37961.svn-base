package com.ant.business;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@javax.persistence.Entity
@Table(name="roomprivilege" )
public class RoomPrivilege {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	private Long roomId ;
	private Long typeinpersonid;
	
	/**国家区号*/
	private String countrycode;
	/**授权用户名*/
	private String grantUserName;
	
	/**授权租客名称*/
	private String grantRealName;

	/**备注*/
	private String remark;
	
	/**插入时间*/
	private Date inputDate = new Date();
	
	/**授权开始时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date grantBeginDate;
	
	/**授权结束时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date grantEndDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomid) {
		this.roomId = roomid;
	}

	public String getGrantUserName() {
		return grantUserName;
	}

	public void setGrantUserName(String grantUserName) {
		this.grantUserName = grantUserName;
	}

	public String getGrantRealName() {
		return grantRealName;
	}

	public void setGrantRealName(String grantRealName) {
		this.grantRealName = grantRealName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getGrantBeginDate() {
		return grantBeginDate;
	}

	public void setGrantBeginDate(Date grantBeginDate) {
		this.grantBeginDate = grantBeginDate;
	}

	public Date getGrantEndDate() {
		return grantEndDate;
	}

	public void setGrantEndDate(Date grantEndDate) {
		this.grantEndDate = grantEndDate;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public Long getTypeinpersonid() {
		return typeinpersonid;
	}

	public void setTypeinpersonid(Long typeinpersonid) {
		this.typeinpersonid = typeinpersonid;
	}

}
