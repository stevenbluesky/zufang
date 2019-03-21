package com.isurpass.common.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.Map;
import org.hibernate.transform.Transformers;


public class HqlWrap implements QueryWrap{

	private SessionFactory sessionFactory;
	private List<HqlClause> hqlClause = new ArrayList<HqlClause>();
	private int first = 0 ;
	private int maxResult = 0 ;
	private Class beanclass ;
	private BasicScroll scroll;
	
	public HqlWrap(SessionFactory sessionFactory)
	{
		super();
		this.sessionFactory = sessionFactory;
	}

	public HqlWrap add(String hql)//ok
	{ 		
		if ( hql == null || hql.length() == 0 )
			return this;
		
		hqlClause.add(new HqlClause(hql));
		return this ;
	}
	
	public HqlWrap add(String hql , String para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap add(String hql , Integer para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap add(String hql , Long para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap add(String hql , Collection para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap addLikeClauseifnotnull(String hql , String para)
	{
		if ( para == null || para.length() == 0 )
			return this ;
		if ( hql == null || hql.length() == 0 )
			return this;
		para = para.toLowerCase();
		if ( !para.startsWith("%"))
			para = "%" + para;
		if ( !para.endsWith("%"))
			para += "%";
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap addifnotnull(String hql , Date para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		if ( para == null )
			return this ;

		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap addifnotnull(String hql , Long para)
	{
		if ( para == null )
			return this ;
		return add(hql , para);
	}
	
	public HqlWrap addifnotnull(String hql , Integer para)
	{
		if ( para == null )
			return this ;
		return add(hql , para);
	}
	
	public HqlWrap addifnotnull(String hql , Collection para)
	{
		if ( para == null )
			return this ;
		return add(hql , para);
	}
	
	public HqlWrap addifnotnull(String hql , Object para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		if ( para == null )
			return this ;
		
		if ( para instanceof String )
		{
			String str = (String) para;
			if ( str.length() == 0 )
				return this ;
		}
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrap setScroll( BasicScroll scroll)
	{
		this.scroll = scroll;
		return this ;
	}
	
	
	public void setResultBeanClass(Class clz)
	{
		this.beanclass = clz ;
	}
	
	private String CreateHQLString()
	{
		StringBuffer hql = new StringBuffer();
		
		for ( HqlClause clause : this.hqlClause )
			hql.append(clause.getHqlClause()).append(" ");
		
		return hql.toString();
	}
	
	private String CreateCountHQLString()
	{
		String hql = CreateHQLString();
		return " select count(*) " + hql.substring(hql.indexOf("from"));
		//return hql.replaceAll("^(^from)*", " select count(*) ");
	}
	
	private Query CreateQuery(String hql)
	{		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		int index  = 0 ;
		for ( HqlClause clause : this.hqlClause )
			index  = clause.setParameter(query, index);

		return query ;
	}
	
	public int count()
	{
		Query query = CreateQuery(CreateCountHQLString());
		
		Long i = (Long)query.uniqueResult();
		return i.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> list()
	{	
		Query query = CreateQuery(CreateHQLString());
		
		if ( scroll != null )
		{
			scroll.compute(this.count());
			query.setFirstResult(scroll.getFrom() - 1);
			query.setMaxResults(scroll.getPageSize());
		}
		if ( beanclass != null )
		{
			if ( beanclass.equals(Map.class) || beanclass.equals(java.util.Map.class))
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			else 
				query.setResultTransformer(Transformers.aliasToBean(beanclass));
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T uniqueResult()
	{
		Query query = CreateQuery(CreateHQLString());
		return (T)query.uniqueResult();
	}
	
	public int executeUpdate()
	{
		Query query = CreateQuery(CreateHQLString());
		return query.executeUpdate();
	}
	
	protected static class HqlClause
	{
		
		private String hqlClause ;
		private Object para ;
		private Date datepara;
		private Long longpara ;
		private Integer intpara;
		private String strpara ;
		private Collection collectionparam;

		public HqlClause(String hqlClause, Date para)
		{
			super();
			this.hqlClause = hqlClause;
			this.datepara = para;
		}
		
		public HqlClause(String hqlClause, Long para)
		{
			super();
			this.hqlClause = hqlClause;
			this.longpara = para;
		}
		
		public HqlClause(String hqlClause, Integer para)
		{
			super();
			this.hqlClause = hqlClause;
			this.intpara = para;
		}
		
		public HqlClause(String hqlClause, String para)
		{
			super();
			this.hqlClause = hqlClause;
			this.strpara = para;
		}
		
		public HqlClause(String hqlClause, Object para) {
			super();
			this.hqlClause = hqlClause;
			this.para = para;
		}
		
		public HqlClause(String hqlClause, Collection para) {
			super();
			this.hqlClause = hqlClause;
			this.collectionparam = para;
		}
		
		public HqlClause(String hqlClause) {
			super();
			this.hqlClause = hqlClause;
		}
		
		public int setParameter(Query query , int index)
		{
			if ( query == null )
				return index;
			
			if ( this.datepara != null )
				query.setDate(index ++ , this.datepara);
			else if ( this.longpara != null )
				query.setLong(index ++ , this.longpara);
			else if ( this.intpara != null )
				query.setInteger(index++, this.intpara);
			else if ( this.strpara != null )
				query.setString(index ++ , this.strpara);
			else if ( this.collectionparam != null )
				query.setParameterList((index ++)+"" , this.collectionparam);
			else if ( this.para != null )
				query.setParameter(index ++ , this.para);
			
			return index ;
		}

		public String getHqlClause() {
			return hqlClause;
		}
		public void setHqlClause(String hqlClause) {
			this.hqlClause = hqlClause;
		}

		public Object getPara() {
			return para;
		}

		public void setPara(Object para) {
			this.para = para;
		}

	}
}
