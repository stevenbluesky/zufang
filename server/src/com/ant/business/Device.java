package com.ant.business;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.ant.controller.PersonController;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 网关
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="device" )
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**网关id*/
	private Long gatewayId;
	
	/**平台设备id*/
	private String ptDeviceId;
	
	/**设备名称*/
	private String deviceName;
	
	/**管理员id*/
	private Long managerId;
	
	/**小区id*/
	private Long districtId;
	
	/**用户id*/
	private Long personId;
	
	private Integer signalstrength;
	
	/**信号强度 0 在线；1 离线*/
	private Integer signalFlag;

	/**设备类型 0 门锁；1 电表*/
	private Integer deviceType;
	
	private Integer devicesubtype;
	
	/**当前度数*/
	private Float currentDegrees;
	
	/**当前功率*/
	private Float currentPower;
	
	/**电表底数*/
	private Float baseDegrees;
	
	/**电池电量 100满电*/
	private Integer battery;
	
	/**状态 0 开；1 关*/
	private Integer openStatus;
	
	/**绑定状态 0 未绑定；1 已绑定*/
	private Integer bindStatus;
	
	/**密码*/
	private String password;
	
	/**临时密码*/
	private String tempPassword;
	
	/**密码开始时间*/
	private Date pwdBeginDate;
	
	/**密码结束时间*/
	private Date pwdEndDate;
	
	/**临时密码开始时间*/
	private Date tempPwdBeginDate;
	
	/**临时密码结束时间*/
	private Date tempPwdEndDate;
	
	/**绑定房间id*/
	private Long bindRoomId;
	
	/**插入时间*/
	private Date inputDate;
	
	/**密码标识 0 录入；1 随机  随机录入不存储密码*/
	private Integer passwordFlag;

	/**临时密码标识  0 录入；1 随机  随机录入不存储密码*/
	private Integer tempPasswordFlag;
	
	/**是否初始化本月读数  0  不初始化；1 初始化*/
	private Integer initPreMonthDegrees;

	private String productor ;

	public Integer getInitPreMonthDegrees() {
		return initPreMonthDegrees;
	}



	public void setInitPreMonthDegrees(Integer initPreMonthDegrees) {
		this.initPreMonthDegrees = initPreMonthDegrees;
	}



	public Integer getPasswordFlag() {
		return passwordFlag;
	}



	public void setPasswordFlag(Integer passwordFlag) {
		this.passwordFlag = passwordFlag;
	}



	public Integer getTempPasswordFlag() {
		return tempPasswordFlag;
	}



	public void setTempPasswordFlag(Integer tempPasswordFlag) {
		this.tempPasswordFlag = tempPasswordFlag;
	}



	public Float getBaseDegrees() {
		return baseDegrees;
	}



	public void setBaseDegrees(Float baseDegrees) {
		this.baseDegrees = baseDegrees;
	}



	public Date getPwdBeginDate() {
		return pwdBeginDate;
	}



	public void setPwdBeginDate(Date pwdBeginDate) {
		this.pwdBeginDate = pwdBeginDate;
	}



	public Date getPwdEndDate() {
		return pwdEndDate;
	}



	public void setPwdEndDate(Date pwdEndDate) {
		this.pwdEndDate = pwdEndDate;
	}



	public Date getTempPwdBeginDate() {
		return tempPwdBeginDate;
	}



	public void setTempPwdBeginDate(Date tempPwdBeginDate) {
		this.tempPwdBeginDate = tempPwdBeginDate;
	}



	public Date getTempPwdEndDate() {
		return tempPwdEndDate;
	}



	public void setTempPwdEndDate(Date tempPwdEndDate) {
		this.tempPwdEndDate = tempPwdEndDate;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getTempPassword() {
		return tempPassword;
	}



	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}



	public Long getBindRoomId() {
		return bindRoomId;
	}



	public void setBindRoomId(Long bindRoomId) {
		this.bindRoomId = bindRoomId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getGatewayId() {
		return gatewayId;
	}



	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}



	public String getPtDeviceId() {
		return ptDeviceId;
	}



	public void setPtDeviceId(String ptDeviceId) {
		this.ptDeviceId = ptDeviceId;
	}



	public String getDeviceName() {
		return deviceName;
	}



	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}



	public Long getManagerId() {
		return managerId;
	}



	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}



	public Long getDistrictId() {
		return districtId;
	}



	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}



	public Long getPersonId() {
		return personId;
	}



	public void setPersonId(Long personId) {
		this.personId = personId;
	}



	public Integer getSignalFlag() {
		return signalFlag;
	}



	public void setSignalFlag(Integer signalFlag) {
		this.signalFlag = signalFlag;
	}



	public Integer getBattery() {
		return battery;
	}



	public void setBattery(Integer battery) {
		this.battery = battery;
	}



	public Integer getOpenStatus() {
		return openStatus;
	}



	public void setOpenStatus(Integer openStatus) {
		this.openStatus = openStatus;
	}



	public Integer getBindStatus() {
		return bindStatus;
	}



	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}



	public Integer getDeviceType() {
		return deviceType;
	}



	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}



	public Float getCurrentDegrees() {
		return currentDegrees;
	}



	public void setCurrentDegrees(Float currentDegrees) {
		this.currentDegrees = currentDegrees;
	}



	public Float getCurrentPower() {
		return currentPower;
	}



	public void setCurrentPower(Float currentPower) {
		this.currentPower = currentPower;
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
	public static void checkRole(Device device,PersonVo sessionPersonVo, ResourceBundle rb){
		if(device.getPersonId() == null || 
				device.getPersonId().longValue() != sessionPersonVo.getId().longValue()){
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
	public static void checkSecondAdminRole(Device device,PersonVo sessionPersonVo, ResourceBundle rb){
		List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
		if ((device.getPersonId() == null)
				|| (device.getPersonId().longValue() != sessionPersonVo.getId().longValue()
				&& !idList.contains(device.getDistrictId()))) {
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}


	public Integer getDevicesubtype() {
		return devicesubtype;
	}



	public void setDevicesubtype(Integer devicesubtype) {
		this.devicesubtype = devicesubtype;
	}



	public String getProductor()
	{
		return productor;
	}



	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	public boolean isUseSpecifiyUsercode()
	{
		if ( StringUtils.isBlank(this.productor))
			return true ;
		
		return !(this.productor.startsWith("021c") || this.productor.startsWith("10"));
	}



	public Integer getSignalstrength() {
		return signalstrength;
	}



	public void setSignalstrength(Integer signalstrength) {
		this.signalstrength = signalstrength;
	}
	
	
}