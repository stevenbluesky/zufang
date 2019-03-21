package com.isurpass.service;

import java.util.List;

import com.ant.business.DoorlockPasswordRule;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class HiPasswordRuleService extends BaseService<DoorlockPasswordRule>{

	/**
	 * 查询小区的密码生成规则
	 */
	public DoorlockPasswordRule findByDistrictId(Long districtId){
		if(districtId==null){
			return null;
		}
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.eq("districtid", districtId));
		if(cw.list()!=null&&cw.list().size()>0){
			return (DoorlockPasswordRule) cw.list().get(0);
		}
		return null;
	} 
	
	/**
	 * 查询所有在用的规则
	 */
	public List<DoorlockPasswordRule> findAllUseRule(){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("isuse", 1));
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}
}
