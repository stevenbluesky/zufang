package com.ant.service.impl;

import com.isurpass.service.HiManagerService;
import com.isurpass.test.util.TestUtil;

public class ManagerServiceImplTest
{

	public static void main(String arg[])
	{
		TestUtil.initSpring();
		
		for ( int i = 0 ; i < 100 ; i ++ )
		{
			Thread t = new Thread(new Runnable(){

				@Override
				public void run()
				{
					HiManagerService svr = (HiManagerService)TestUtil.getBean("hiManagerService");
					System.out.println(svr.getManagerId());
				}});
			t.start();
		}
		
	}
}
