package com.isurpass.service;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import com.ant.business.DoorlockPasswordRuleDtl;
import com.ant.vo.PasswordRuleDtlVO;
import com.ant.vo.PasswordRuleVO;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class HiPasswordRuleDtlService extends BaseService<DoorlockPasswordRuleDtl>{

	public List<DoorlockPasswordRuleDtl> findPage(PasswordRuleVO passwordrulevo,BasicScroll scroll){
		CriteriaWrap cw = new CriteriaWrap(DoorlockPasswordRuleDtl.class.getName() , sessionFactory);
		cw.add(ExpWrap.eq("doorlockpasswordruleid",  passwordrulevo.getDoorlockpasswordruleid()));

		cw.setScroll(scroll);
		cw.addOrder(Order.asc("starttime"));
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}

	public void savePasswordRuleDtl(PasswordRuleDtlVO passwordruledtlvo) {
		DoorlockPasswordRuleDtl dprd = new DoorlockPasswordRuleDtl();
		dprd.setDoorlockpasswordruleid(passwordruledtlvo.getDoorlockpasswordruleid());
		dprd.setTypeinpersonid(passwordruledtlvo.getTypeinpersonid());
		dprd.setStarttime(passwordruledtlvo.getStarttime());
		dprd.setDuration(passwordruledtlvo.getDuration());
		dprd.setWeekday(passwordruledtlvo.getWeekday());
		dprd.setCreatetime(new Date());
		super.save(dprd);
		
	}

	public DoorlockPasswordRuleDtl findByDoorlockPasswordRuleDtlId(Long doorlockpasswordruledtlid) {
		CriteriaWrap cw = new CriteriaWrap(DoorlockPasswordRuleDtl.class.getName() , sessionFactory);
		cw.add(ExpWrap.eq("doorlockpasswordruledtlid", doorlockpasswordruledtlid));
		return cw.uniqueResult();
	}
	
	public List<DoorlockPasswordRuleDtl> findByDoorlockPasswordRuleId(Long doorlockpasswordruleid) {
		CriteriaWrap cw = new CriteriaWrap(DoorlockPasswordRuleDtl.class.getName() , sessionFactory);
		cw.add(ExpWrap.eq("doorlockpasswordruleid", doorlockpasswordruleid));
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}
}
