package com.ant.business;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity 
@Table(name="systemparameter" )
public class SystemParameter {
	@Id    
	@GenericGenerator(name = "generator", strategy = "assigned")   
	private String strkey;
	private String strvalue;
	private Integer intvalue;
	
	public SystemParameter() {
		super();
	}
	
	public SystemParameter(String strkey, String strvalue) {
		super();
		this.strkey = strkey;
		this.strvalue = strvalue;
	}

	public String getStrkey() {
		return strkey;
	}
	public void setStrkey(String strkey) {
		this.strkey = strkey;
	}
	public String getStrvalue() {
		return strvalue;
	}
	public void setStrvalue(String strvalue) {
		this.strvalue = strvalue;
	}
	public Integer getIntvalue() {
		return intvalue;
	}
	public void setIntvalue(Integer intvalue) {
		this.intvalue = intvalue;
	}
	
	
}
