package com.isurpass.common.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;

public class CriteriaWrap implements QueryWrap{
	
	private SessionFactory sessionFactory;
	private String className;
	private String alias ;
	private List<Criterion>  criterion = new ArrayList<Criterion>();
	private List<Order> order = new ArrayList<Order>();
	private List<String> field = new ArrayList<String>();
	private BasicScroll scroll;
	
	public CriteriaWrap(String clsName , String alias , SessionFactory sessionFactory)
	{
		this.className = clsName ;
		this.alias = alias ;
		this.sessionFactory = sessionFactory;
	}
	
	public CriteriaWrap(String clsName , SessionFactory sessionFactory)
	{
		this.className = clsName ;
		this.sessionFactory = sessionFactory;
	}
	
	public CriteriaWrap addifNotNull(Criterion cri)
	{
		if ( cri == null )
			return this ;
		criterion.add(cri);
		return this ;
	}
	
	public CriteriaWrap add(Criterion cri)
	{
		criterion.add(cri);
		return this ;
	}
	
	public CriteriaWrap addOrder(Order order)
	{
		this.order.add(order);
		return this ;
	}
	
	public CriteriaWrap setScroll( BasicScroll scroll)
	{
		this.scroll = scroll;
		return this ;
	}
	
	public CriteriaWrap addFields(String[] field)
	{
		if ( field == null || field.length == 0 )
			return this;
		for ( int i = 0 ; i < field.length ; i ++ )
			this.field.add(field[i]);
		return this ;
	}
	
	private ProjectionList createProjectionList()
	{
		if ( this.field == null || this.field.size() == 0 )
			return null;
		ProjectionList pl = Projections.projectionList();
		
		for(String f : this.field)
			pl.add(Property.forName(f).as(f));
		
		return pl ;
	}
	
	private Criteria CreateCriteria()
	{
		Criteria cri = null ;
		
		if ( this.alias == null || this.alias.length() == 0 )
			cri = sessionFactory.getCurrentSession().createCriteria(this.className);
		else 
			cri = sessionFactory.getCurrentSession().createCriteria(this.className , this.alias);
		
		if ( this.field != null && this.field.size() > 0 )
			cri.setProjection(createProjectionList());
		for(Criterion crt: criterion)
			cri.add(crt);
		for(Order ord : order)
			cri.addOrder(ord);
		return cri ;
	}
	
	public  <T> T uniqueResult()
	{
		Object o = CreateCriteria().uniqueResult() ;
		if ( o == null )
			return null ;
		return (T)o;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> List<T> list( Class clz)
	{
		Criteria cri = CreateCriteria();
		
		if ( scroll != null )
		{	
			scroll.compute(this.count());
			cri.setFirstResult(scroll.getFrom() - 1);
			cri.setMaxResults(scroll.getPageSize());
		}
		else 
			cri.setFirstResult(0);

		if ( clz != null )
			cri.setResultTransformer(Transformers.aliasToBean(clz));
		return cri.list() ;
	}
	
	public <T> List<T> list()
	{
		return list(null);
	}
	
	public <T> T first()
	{
		List<T> lst = list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.iterator().next();
	}
	
	public int count()
	{
		Criteria cri = CreateCriteria();
		Object obj = cri.setProjection(Projections.rowCount()).uniqueResult();
		
		if ( obj instanceof Long )
			return ((Long)obj).intValue();

		return ((Integer)obj).intValue();
	}

	public List<Criterion> getCriterion() {
		return criterion;
	}

	public String getAlias() {
		return alias;
	}
	
}
