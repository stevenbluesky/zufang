package com.ant.business;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户
 * @author 艾建
 * @date  2015-1-8上午10:10:38
 * @version 1.0
 */
@javax.persistence.Entity 
@Table(name="areas" )
public class Areas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	/**区县代码*/
	private String areasCode;

	/**市代码*/
	private String cityCode;
	
	/**区县名称*/
	private String areasName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreasCode() {
		return areasCode;
	}

	public void setAreasCode(String areasCode) {
		this.areasCode = areasCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreasName() {
		return areasName;
	}

	public void setAreasName(String areasName) {
		this.areasName = areasName;
	}

}
