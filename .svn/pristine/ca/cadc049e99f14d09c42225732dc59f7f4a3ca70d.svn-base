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
 * 网关
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="gateway" )
public class Gateway {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**平台网关id*/
	private String ptGatewayid;

	/**网关名称*/
	private String gatewayName;
	
	/**管理员id*/
	private Long managerId;
	
	/**小区id*/
	private Long districtId;
	
	/**用户id*/
	private Long personId;
	
	/**信号强度 0 在线；1 离线*/
	private Integer signalFlag;

	/**插入时间*/
	private Date inputDate;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPtGatewayid() {
		return ptGatewayid;
	}



	public void setPtGatewayid(String ptGatewayid) {
		this.ptGatewayid = ptGatewayid;
	}



	public String getGatewayName() {
		return gatewayName;
	}



	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
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
	public static void checkRole(Gateway gateway,PersonVo sessionPersonVo, ResourceBundle rb){
		if(gateway.getPersonId() == null || 
				gateway.getPersonId().longValue() != sessionPersonVo.getId().longValue()){
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
	public static void checkSecondAdminRole(Gateway gateway,PersonVo sessionPersonVo, ResourceBundle rb){
		List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
		if ((gateway.getPersonId() == null)
				|| (gateway.getPersonId().longValue() != sessionPersonVo.getId().longValue()
				&& !idList.contains(gateway.getDistrictId()))) {
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
}
