package com.isurpass.service;

import java.util.List;

import com.ant.business.DataPrivilegeGrant;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class DataPrivilegeGrantService  extends BaseService<DataPrivilegeGrant>{

	/**
	 * 查询用户的小区列表
	 * @param personId
	 * @return
	 */
	public List<DataPrivilegeGrant> findByPersonId(Integer personId)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.eq("personid", personId));
		cw.add(ExpWrap.eq("status", 1));
		return cw.list();
	} 
}
