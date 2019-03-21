package com.ant.business;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.annotations.GenericGenerator;

import com.ant.controller.PersonController;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 管理员
 * @author 艾建
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="manager" )
public class Manager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**用户id*/
	private Long personId;

	private Integer typeinpersonid;
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


	public Integer getTypeinpersonid() {
		return typeinpersonid;
	}


	public void setTypeinpersonid(Integer typeinpersonid) {
		this.typeinpersonid = typeinpersonid;
	}


	/**
	 * 校验权限
	 * @param inspirationCollect
	 * @param sessionPersonVo
	 */
	public static void checkRole(Manager manager,PersonVo sessionPersonVo, ResourceBundle rb){
		if(manager.getPersonId() == null || 
				manager.getPersonId().longValue() != sessionPersonVo.getId().longValue()){
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
	public static void checkSecondAdminRole(Manager manager,PersonVo sessionPersonVo, ResourceBundle rb){
		List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
		if ((manager.getPersonId() == null)
				|| (manager.getPersonId().longValue() != sessionPersonVo.getId().longValue()
				&& !idList.contains(manager.getDistrictId()))) {
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
}
