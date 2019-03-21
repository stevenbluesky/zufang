package com.isurpass.service;

import com.ant.business.SystemParameter;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class SystemParameterService extends BaseService<SystemParameter>{

	public void saveSystemParameter(SystemParameter sp) {
		super.save(sp);
	}
	public void saveUpdateSystemParameter(SystemParameter sp) {
		super.saveOrUpdate(sp);
	}
	public SystemParameter findByStrkey(String key){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("strkey",key));
		return cw.uniqueResult();	
	}
}
