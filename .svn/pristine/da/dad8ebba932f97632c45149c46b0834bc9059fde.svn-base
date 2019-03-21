package com.isurpass.test.util;

import org.springframework.context.support.GenericXmlApplicationContext;

public class TestUtil
{
	private static GenericXmlApplicationContext context = null ;

	public static void initSpring()
	{
		if ( context != null )
			return ;
		context = new GenericXmlApplicationContext();  
        context.setValidating(false);  
        context.load("classpath*:applicationContext.xml");  
        context.refresh();  
	}
	
	public static Object getBean(String name)
	{
		initSpring();
		return context.getBean(name);
	}
}
