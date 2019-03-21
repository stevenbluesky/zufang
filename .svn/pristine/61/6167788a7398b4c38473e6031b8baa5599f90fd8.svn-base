package com.isurpass.common.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.Map;
import org.hibernate.transform.Transformers;

public class HqlWrapnew implements QueryWrap{

	private SessionFactory sessionFactory;
	private List<HqlClause> hqlClause = new ArrayList<HqlClause>();
	private Class beanclass ;
	private BasicScroll scroll;
	
	public HqlWrapnew() {
		super();
	}
	
	public HqlWrapnew(SessionFactory sessionFactory)
	{
		super();
		this.sessionFactory = sessionFactory;
	}
	


	public HqlWrapnew add(String hql)//ok
	{ 
		if ( hql == null || hql.length() == 0 )
			return this;
		
		hqlClause.add(new HqlClause(hql));
		return this ;
	}
	
	public HqlWrapnew addLikeClauseifnotnull(String hql , String para)
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
	
	public HqlWrapnew addifnotnull(String hql , String paraname , Collection<Long> para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		if ( para == null || para.size() == 0 )
			return this ;
		
		hqlClause.add(new HqlClause(hql ,paraname, para));
		return this ;
	}
	
	public HqlWrapnew addifnotnulla(String hql , String paraname , Collection<Long> para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		if ( para == null || para.size() == 0 )
			return this ;
		
		hqlClause.add(new HqlClause(hql ,paraname, para));
		return this ;
	}
	
	public HqlWrapnew addifnotnull(String hql , Collection<String> para)
	{
		if ( hql == null || hql.length() == 0 )
			return this;
		
		if ( para == null || para.size() == 0 )
			return this ;
		
		hqlClause.add(new HqlClause(hql , para));
		return this ;
	}
	
	public HqlWrapnew addifnotnull(String hql , String name, Object para)
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
		
		hqlClause.add(new HqlClause(hql , name , para));
		return this ;
	}
	
	public HqlWrapnew addifnotnull(String hql , Object para)
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
	public void setResultBeanClass(Class clz)
	{
		this.beanclass = clz ;
	}
	
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
	public HqlWrapnew setScroll( BasicScroll scroll)
	{
		this.scroll = scroll;
		return this ;
	}
	public int executeUpdate()
	{
		Query query = CreateQuery(CreateHQLString());
		return query.executeUpdate();
	}
	
	protected static class HqlClause
	{
		
		private String hqlClause ;
		private String name ;
		private Object para ;
		private Collection cp ;

		public HqlClause(String hqlClause, Object para) {
			super();
			this.hqlClause = hqlClause;
			this.para = para;
		}

		public HqlClause(String hqlClause, String name, Object para) {
			super();
			this.hqlClause = hqlClause;
			this.name = name;
			this.para = para;
		}

		public HqlClause(String hqlClause, Collection cp) {
			super();
			this.hqlClause = hqlClause;
			this.cp = cp;
		}

		public HqlClause(String hqlClause, String name, Collection cp) {
			super();
			this.hqlClause = hqlClause;
			this.name = name;
			this.cp = cp;
		}

		public HqlClause(String hqlClause) {
			super();
			this.hqlClause = hqlClause;
		}
		
		public int setParameter(Query query , int index)
		{
			if ( query == null )
				return index;
//			if ( this.para == null )
//				return index ;
			
			if ( this.para != null ){
				if ( org.apache.commons.lang.StringUtils.isBlank(name))
					query.setParameter(index ++ , this.para);
				else 
					query.setParameter(name , this.para);
			}
			else if ( this.cp != null )
			{
				query.setParameterList(name, this.cp);
			}
			
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
