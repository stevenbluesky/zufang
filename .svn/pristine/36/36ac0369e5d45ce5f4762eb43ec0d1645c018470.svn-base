package com.ant.service.impl;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ant.business.LastTimeLog;
import com.isurpass.service.HiLastTimeLogService;


public class LastTimeLogServiceHibernateImplTest
{
    private ApplicationContext context=null;
    private HiLastTimeLogService svr=null;
    
    {
         context= new ClassPathXmlApplicationContext("applicationContext.xml");  
         svr=(HiLastTimeLogService)context.getBean("HiLastTimeLogService");
    }
    
 

    public void test2()
    {
        Long ld=svr.findLastTime();
        System.out.println(ld);
        
        svr.saveLastTime(ld + 1 );
        
        LastTimeLog ltl = svr.query(2);
        
        System.out.println(ltl.getLastId());
    }
    
}
