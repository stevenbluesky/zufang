package com.isurpass.service;

import org.apache.commons.lang.StringUtils;

import com.ant.business.DistrictPaymentSetting;

public class DistrictPaymentSettingService  extends BaseService<DistrictPaymentSetting>
{

	public DistrictPaymentSetting querybydistrictid(int districtid)
	{
		return super.findOneByProperty("districtid", districtid);
	}
	
	public void saveDistrictPaymentSetting(DistrictPaymentSetting setting)
	{
		DistrictPaymentSetting dps = this.querybydistrictid(setting.getDistrictid());
		if ( dps == null )
		{
			dps = new DistrictPaymentSetting();
			dps.setDistrictid(setting.getDistrictid());
		}
		
		dps.setAutofee(setting.getAutofee());
		
		if ( StringUtils.isNotBlank(setting.getWexinpayapikey()))
			dps.setWexinpayapikey(setting.getWexinpayapikey());
		
		if ( StringUtils.isNotBlank(setting.getWexinpayappid()))
			dps.setWexinpayappid(setting.getWexinpayappid());
		
		if ( StringUtils.isNotBlank(setting.getWexinpayappname()))
			dps.setWexinpayappname(setting.getWexinpayappname());
		
		if ( StringUtils.isNotBlank(setting.getWexinpaymchid()))
			dps.setWexinpaymchid(setting.getWexinpaymchid());
		
		super.saveOrUpdate(dps);
	}
}
