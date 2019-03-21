package com.ant.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.Customer;
import com.ant.vo.PersonVo;
import com.isurpass.service.HiCustomerService;

/**
 * 通讯记录
 *
 */
@Controller
@RequestMapping(value="/service/customer")
@Scope(value = "prototype")
public class CustomerController extends BaseController{

	private static Log log = LogFactory.getLog(CommuLogController.class);
	@Resource
	private HiCustomerService hiCustomerService;
	
	@RequestMapping(value="/findInfo")
	public @ResponseBody List<Customer> findInfo(@ModelAttribute("customer")Customer customer){
		PersonVo sessionPersonVo = super.getCurrentUser();
		if(sessionPersonVo.getType()==1&&sessionPersonVo.getSuperpersonid()!=null&&sessionPersonVo.getSuperpersonid()!=0){
			return hiCustomerService.findInfo(sessionPersonVo.getSuperpersonid().longValue(),customer.getCustomerinfo());
		}else{
			return hiCustomerService.findInfo(sessionPersonVo.getId(),customer.getCustomerinfo());
		}
	}
	
	
}
