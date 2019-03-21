package com.ant.business;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity
@Table(name="doorlockpasswordruledtl")
public class DoorlockPasswordRuleDtl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long doorlockpasswordruledtlid;
	
	private Long doorlockpasswordruleid;
	
	private Long typeinpersonid;
	
	private String starttime;
	
	private int duration;
	
	private int weekday;
	
	private Date createtime;

	public Long getDoorlockpasswordruledtlid() {
		return doorlockpasswordruledtlid;
	}

	public void setDoorlockpasswordruledtlid(Long doorlockpasswordruledtlid) {
		this.doorlockpasswordruledtlid = doorlockpasswordruledtlid;
	}

	public Long getDoorlockpasswordruleid() {
		return doorlockpasswordruleid;
	}

	public void setDoorlockpasswordruleid(Long doorlockpasswordruleid) {
		this.doorlockpasswordruleid = doorlockpasswordruleid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
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
