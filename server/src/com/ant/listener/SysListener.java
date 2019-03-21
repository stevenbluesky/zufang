/**
 * 
 */
package com.ant.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ant.constant.CommonConstant;
import com.isurpass.message.client.MessageClient;


/**
 * @Description:
 * @Author: aijian
 * @Version: V1.00 
 * @Create Date: 2014-2-20下午3:01:22
 * @Parameters:
 */
public class SysListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		CommonConstant.realPath =  context.getServletContext().getRealPath("/") ;
		CommonConstant.webPath = context.getServletContext().getContextPath();
		
		Thread thread = new Thread(new MessageClient());
		thread.start();
	}

}
