package com.ant.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.Areas;
import com.ant.business.City;
import com.ant.business.Province;
import com.isurpass.service.HiProvinceService;


@Controller
@RequestMapping(value="/service/province")
@Scope(value = "prototype")
public class ProvinceController extends BaseController {

	private static Log log = LogFactory.getLog(ProvinceController.class);
	
	@Resource
	private HiProvinceService hiProvinceService;
	
	/**
	  * @Description: 获取省份数据
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Create Date: 2015-1-8下午2:27:17
	  * @Parameters:@return
	 */
	@RequestMapping(value = "/findProvince")
	public @ResponseBody Map<String , Object> findProvince(@ModelAttribute("province") Province province) 
	{
		try
		{
			List<Province> provinceList = hiProvinceService.findProvince();
			return createResponse(1 , getRB().getString("jsuccess") , provinceList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据省份获取市数据
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Create Date: 2015-1-8下午2:27:17
	  * @Parameters:@return
	 */
	@RequestMapping(value = "/findCity")
	public @ResponseBody Map<String , Object>  findCity(@ModelAttribute("city") City city) 
	{
		try
		{
			List<City> cityList = hiProvinceService.findCity(city);
			return createResponse(1 , getRB().getString("jsuccess") , cityList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据市获取区县数据
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Create Date: 2015-1-8下午2:27:17
	  * @Parameters:@return
	 */
	@RequestMapping(value = "/findAreas")
	public @ResponseBody Map<String , Object>  findAreas(@ModelAttribute("areas") Areas areas) {
		try{
			List<Areas> areasList = hiProvinceService.findAreas(areas);
			return createResponse(1 , getRB().getString("jsuccess") , areasList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}

	
}
