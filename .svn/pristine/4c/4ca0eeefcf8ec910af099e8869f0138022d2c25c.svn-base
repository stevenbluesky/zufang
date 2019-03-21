package com.ant.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 操作日志
 * @author aijian
 *
 */
public class OperateLogVo extends CommonVo {
	
	/**id*/
	private Long id;
	
	/**日志类型 0系统登录；1添加小区；2删除小区；3修改小区；4 添加管理员；5 修改管理员；6 删除管理员；7 添加房间；8 修改房间；9 删除房间；10 授权用户；11 删除授权；12 同步设备；13 房间绑定设备；14 房间解除设备；15 电池电量低；16 设备离线；17 设备被拆卸；18 网关离线*/
	private Integer operateType;

	/**标题*/
	private String title;
	

	/**操作人*/
	private String operateName;

	/**用户id*/
	private Long personId;
	
	/**ip*/
	private String ip;
	
	/**操作时间*/
	private Date inputDate;
	
	/**业务id*/
	private Long businessId;
	
	/**开始时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginDate;
	
	/**结束时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;

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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
