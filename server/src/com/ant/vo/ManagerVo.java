package com.ant.vo;

import java.util.Date;

public class ManagerVo extends CommonVo {
	
	/**id*/
	private Long id;
	
	/**用户id*/
	private Long personId;

	/**小区id*/
	private Long districtId;
	
	/**登录id*/
	private String loginName;
	
	/**密码*/
	private String loginPassword;
	
	/**姓名*/
	private String managerName;
	
	/**手机号*/
	private String phone;
	
	/**证件类型 0 身份证*/
	private Integer idType;
	
	/**证件号*/
	private String idNo;
	
	/**备注*/
	private String remark;
	
	/**插入时间*/
	private Date inputDate;

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


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLoginPassword() {
		return loginPassword;
	}


	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


	public String getManagerName() {
		return managerName;
	}


	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Integer getIdType() {
		return idType;
	}


	public void setIdType(Integer idType) {
		this.idType = idType;
	}


	public String getIdNo() {
		return idNo;
	}


	public void setIdNo(String idNo) {
		this.idNo = idNo;
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
}
