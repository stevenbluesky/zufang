package com.isurpass.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class BaseService<T> implements ApplicationContextAware
{
	protected ApplicationContext applicationContext;
	
	private Class<T> entityClass;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseService()
	{
    	Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
        entityClass = (Class) params[0];  
	}

	@Autowired
    protected SessionFactory sessionFactory;
    
	public void save(T t)
	{
		sessionFactory.getCurrentSession().save(t);
	}
	
	public void delete(T t)
	{
		sessionFactory.getCurrentSession().delete(t);
	}
	
	public void update(T t)
	{
		sessionFactory.getCurrentSession().update(t);
	}
	
	public void saveOrUpdate(T t)
	{
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}
	
	public T query(int id)
	{
		return query(new Long(id));
	}
	
	public T findOneByProperty(String property , Object value)
	{
		CriteriaWrap cw = this.createCriteriaWrap();
		cw.add(ExpWrap.eq(property, value));
		return cw.uniqueResult();
	}
	
	public List<T> findByProperty(String property , Object value)
	{
		CriteriaWrap cw = this.createCriteriaWrap();
		cw.add(ExpWrap.eq(property, value));
		return cw.list();
	}
	
	@SuppressWarnings("unchecked")
	public T query(Long id)
	{
		return (T)sessionFactory.getCurrentSession().get(getEntityClass(), id);
	}
	
	public List<T> findPage(Long personid , Long deviceid , Integer dayflag , String datafiled,BasicScroll scroll)
	{
		CriteriaWrap cw = new CriteriaWrap(getEntityClass().getName() , sessionFactory);
		cw.addifNotNull(ExpWrap.eq("personId", personid));
		cw.addifNotNull(ExpWrap.eq("deviceId", deviceid));
		
		if(dayflag != null)
		{
			Calendar date = Calendar.getInstance();
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);

			if(dayflag == 1) //1 : a week ; 0 : today , do nothing
				date.add(Calendar.DAY_OF_MONTH, -7);
			else if(dayflag == 2) // 2 : a month
				date.add(Calendar.MONTH, -1);
	
			cw.add(ExpWrap.ge(datafiled, date.getTime()));
		}
		cw.addOrder(Order.desc(datafiled));	
		cw.setScroll(scroll);
		
		return cw.list();
	}
	
	public <Y> Y createService(Class<Y> clz)
	{
		return (Y)this.applicationContext.getBean(clz);
	}
	
	protected Class<T> getEntityClass()
	{
		return entityClass;
	}
	
	protected CriteriaWrap createCriteriaWrap()
	{
		return new CriteriaWrap(getEntityClass().getName() , sessionFactory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
	}
}
