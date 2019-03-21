package com.ant.business;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.ant.controller.PersonController;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 房间
 * @author 艾建
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="room" )
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**用户id*/
	private Long personId;

	private Integer typeinpersonid;
	/**小区id*/
	private Long districtId;
	
	/**房间类型 1 一居室；2 两居室；3 三居室*/
	private Integer roomType;
	
	/**房间名称*/
	private String roomName;
	
	
	/**图片*/
	private String roomImg;
	
	private int rentfee;
	
	private int balance;
	private int arrearage;
	
	/**栋*/
	private String build;
	
	/**单元*/
	private String unit;
	
	/**楼*/
	private String floor;
	
	/**设备数量*/
//	private Integer deviceCount;
	
	/**是否已授权 1 已授权；0 未授权*/
	private Integer grantFlag;
	
	/**授权用户名*/
	private String grantUserName;
	
	/**授权租客名称*/
	private String grantRealName;
	
	/**备注*/
	private String remark;
	
	/**插入时间*/
	private Date inputDate;
	
	/**授权开始时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date grantBeginDate;
	
	/**授权结束时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date grantEndDate;

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


	public Long getDistrictId() {
		return districtId;
	}


	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}


	public Integer getRoomType() {
		return roomType;
	}


	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getRoomImg() {
		return roomImg;
	}


	public void setRoomImg(String roomImg) {
		this.roomImg = roomImg;
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


//	public Integer getDeviceCount() {
//		return deviceCount;
//	}
//
//
//	public void setDeviceCount(Integer deviceCount) {
//		this.deviceCount = deviceCount;
//	}


	public Integer getGrantFlag() {
		return grantFlag;
	}


	public void setGrantFlag(Integer grantFlag) {
		this.grantFlag = grantFlag;
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


	/**
	 * 校验权限
	 * @param inspirationCollect
	 * @param sessionPersonVo
	 */
	public static void checkRole(Room room,PersonVo sessionPersonVo, ResourceBundle rb){
		if(room.getPersonId() == null || 
				room.getPersonId().longValue() != sessionPersonVo.getId().longValue()){
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
	public static void checkSecondAdminRole(Room room,PersonVo sessionPersonVo, ResourceBundle rb){
		List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
		if ((room.getPersonId() == null)
				|| (room.getPersonId().longValue() != sessionPersonVo.getId().longValue()
				&& !idList.contains(room.getDistrictId()))) {
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}

	public int getRentfee() {
		return rentfee;
	}


	public void setRentfee(int rentfee) {
		this.rentfee = rentfee;
	}


	public int getBalance() {
		return balance;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}


	public int getArrearage() {
		return arrearage;
	}


	public void setArrearage(int arrearage) {
		this.arrearage = arrearage;
	}


	public Integer getTypeinpersonid() {
		return typeinpersonid;
	}


	public void setTypeinpersonid(Integer typeinpersonid) {
		this.typeinpersonid = typeinpersonid;
	}
	
}
