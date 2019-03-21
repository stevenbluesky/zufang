package com.ant.business;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name="operateLog" )
public class OperateLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	private Long id;
	
	/**日志类型 0系统登录；1添加小区；2删除小区；3修改小区；4 添加管理员；5 修改管理员；6 删除管理员；7 添加房间；8 修改房间；
	 * 9 删除房间；10 授权用户；11 删除授权；12 同步设备；13 房间绑定设备；14 房间解除设备；15 电池电量低；16 设备离线；
	 * 17 设备被拆卸；18 网关离线；19 网关上线；20 设备上线；21 设备解除拆卸告警；22房间充值；23房间退款*/
	private Integer operateType;

	/**标题*/
	private String title;
	

	/**操作人*/
	private String operateName;

	/**用户id*/
	private Long personId;
	
	private Integer operatepersonid;
	
	/**ip*/
	private String ip;
	
	/**操作时间*/
	private Date inputDate;
	
	/**业务id*/
	private Long businessId;
	/**根据操作类型存不同的信息*/
	private String msg1;
	private String msg2;
	private String msg3;
	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String getMsg2() {
		return msg2;
	}

	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	public String getMsg3() {
		return msg3;
	}

	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getOperatepersonid() {
		return operatepersonid;
	}

	public void setOperatepersonid(Integer operatepersonid) {
		this.operatepersonid = operatepersonid;
	}
	
}