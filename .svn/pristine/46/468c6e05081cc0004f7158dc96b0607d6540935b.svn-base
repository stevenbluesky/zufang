package com.isurpass.common.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

@SuppressWarnings("rawtypes")
public class DetachedCriteriaWrap extends CriteriaWrap {

	private Class cls ;
	
	public DetachedCriteriaWrap(Class cls , SessionFactory sessionFactory ) {
		super(cls.getName(),sessionFactory);
		this.cls = cls ;
	}
	
	public DetachedCriteriaWrap(Class cls , String alias , SessionFactory sessionFactory ) {
		super(cls.getName() , alias , sessionFactory);
		this.cls = cls ;
	}

	public DetachedCriteria createDetachedCriteria()
	{
		DetachedCriteria  dc = null ;
		if ( super.getAlias() == null || super.getAlias().length() == 0  )
			dc = DetachedCriteria.forClass(cls );
		else 
			dc = DetachedCriteria.forClass(cls , super.getAlias() );
		
		for(Criterion crt: super.getCriterion() )
			dc.add(crt);
		
		return dc ;
	}
	
}
