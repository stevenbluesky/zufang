package com.isurpass.service;

import java.util.ArrayList;
import java.util.List;

import com.ant.business.Customer;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class HiCustomerService extends BaseService<Customer> {

	public List<Customer> findInfo(Long id, String customerinfo) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("personid", id));
		cw.add(ExpWrap.like("customerinfo", customerinfo));
		List<Customer> list = cw.list();
		List<Customer> copylist = new ArrayList<Customer>();
		if(list.size()>100){
			for(int i =0;i<100;i++){
				copylist.add(list.get(i));
			}
			return copylist;
		}
		return list;
	}

}
