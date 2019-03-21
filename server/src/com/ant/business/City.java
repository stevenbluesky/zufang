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
@Table(name="city" )
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	/**省代码*/
	private String provinceCode;

	/**市代码*/
	private String cityCode;
	
	/**市名称*/
	private String cityName;
	
	/**市名称拼音首字母*/
	private String firstPinyin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getFirstPinyin() {
		return firstPinyin;
	}

	public void setFirstPinyin(String firstPinyin) {
		this.firstPinyin = firstPinyin;
	}

}
