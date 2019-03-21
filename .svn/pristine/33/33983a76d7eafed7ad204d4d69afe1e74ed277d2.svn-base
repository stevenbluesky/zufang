package com.ant.business;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity
@Table(name="doorlockpasswordrule")
public class DoorlockPasswordRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long doorlockpasswordruleid;
	
	private Long districtid;
	
	private int isuse;
	
	private Long typeinpersonid;
	
	private Date createtime;

	public Long getDoorlockpasswordruleid() {
		return doorlockpasswordruleid;
	}

	public void setDoorlockpasswordruleid(Long doorlockpasswordruleid) {
		this.doorlockpasswordruleid = doorlockpasswordruleid;
	}

	public Long getDistrictid() {
		return districtid;
	}

	public void setDistrictid(Long districtid) {
		this.districtid = districtid;
	}

	public int getIsuse() {
		return isuse;
	}

	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getTypeinpersonid() {
		return typeinpersonid;
	}

	public void setTypeinpersonid(Long typeinpersonid) {
		this.typeinpersonid = typeinpersonid;
	}
	
}
