package com.ant.business;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity
@Table(name="dataprivilegegrant" )
public class DataPrivilegeGrant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")
	private Integer dataprivilegegrantid;
	private Integer personid;
	private Long districtid;
	private Date createtime;
	private Integer status;
	private Date deletetime;
	public Integer getDataprivilegegrantid() {
		return dataprivilegegrantid;
	}
	public void setDataprivilegegrantid(Integer dataprivilegegrantid) {
		this.dataprivilegegrantid = dataprivilegegrantid;
	}
	public Integer getPersonid() {
		return personid;
	}
	public void setPersonid(Integer personid) {
		this.personid = personid;
	}

	public Long getDistrictid() {
		return districtid;
	}
	public void setDistrictid(Long districtid) {
		this.districtid = districtid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getDeletetime() {
		return deletetime;
	}
	public void setDeletetime(Date deletetime) {
		this.deletetime = deletetime;
	}
	
	
}
