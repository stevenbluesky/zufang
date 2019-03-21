package com.ant.business;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity
@Table(name="districtpaymentsetting" )
public class DistrictPaymentSetting
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private int districtpaymentsettingid ;
	private int districtid;
	private int autofee;
	private String wexinpayappid ;
	private String wexinpaymchid  ;
	private String wexinpayapikey ;
	private String wexinpayappname;
	
	public int getDistrictpaymentsettingid()
	{
		return districtpaymentsettingid;
	}
	public void setDistrictpaymentsettingid(int districtpaymentsettingid)
	{
		this.districtpaymentsettingid = districtpaymentsettingid;
	}
	public int getAutofee()
	{
		return autofee;
	}
	public void setAutofee(int autofee)
	{
		this.autofee = autofee;
	}
	public String getWexinpayappid()
	{
		return wexinpayappid;
	}
	public void setWexinpayappid(String wexinpayappid)
	{
		this.wexinpayappid = wexinpayappid;
	}
	public String getWexinpaymchid()
	{
		return wexinpaymchid;
	}
	public void setWexinpaymchid(String wexinpaymchid)
	{
		this.wexinpaymchid = wexinpaymchid;
	}
	public String getWexinpayapikey()
	{
		return wexinpayapikey;
	}
	public void setWexinpayapikey(String wexinpayapikey)
	{
		this.wexinpayapikey = wexinpayapikey;
	}
	public String getWexinpayappname()
	{
		return wexinpayappname;
	}
	public void setWexinpayappname(String wexinpayappname)
	{
		this.wexinpayappname = wexinpayappname;
	}
	public int getDistrictid()
	{
		return districtid;
	}
	public void setDistrictid(int districtid)
	{
		this.districtid = districtid;
	}
	
	
}
