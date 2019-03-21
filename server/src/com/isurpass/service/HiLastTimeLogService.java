package com.isurpass.service;

import java.util.List;

import com.ant.business.LastTimeLog;
import com.isurpass.common.hibernate.CriteriaWrap;

public class HiLastTimeLogService extends BaseService<LastTimeLog> 
{

	public Long findLastTime()
	{
		CriteriaWrap cw = new CriteriaWrap(LastTimeLog.class.getName() , sessionFactory);
		List<LastTimeLog> lst = cw.list();
		
		if ( lst == null || lst.size() == 0)
			return 0L ;
		else 
		{
			LastTimeLog  ltl = lst.get(0);
			return ltl.getLastId();
		}
	}

	public void saveLastTime(Long lastId)
	{
		CriteriaWrap cw = new CriteriaWrap(LastTimeLog.class.getName() , sessionFactory);
		List<LastTimeLog> lst = cw.list();
		
		if ( lst == null || lst.size() == 0)
		{
			LastTimeLog ltl = new LastTimeLog();
			ltl.setId(lastId);
		}
		else 
		{
			LastTimeLog  ltl = lst.get(0);
			ltl.setLastId(lastId);
		}
	}
	
	public void save(LastTimeLog  ltl)
	{
		sessionFactory.getCurrentSession().save(ltl);
	}
	
}
