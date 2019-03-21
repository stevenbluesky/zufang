package com.ant.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.District;
import com.ant.business.DistrictPaymentSetting;
import com.ant.util.StringUtil;
import com.ant.vo.DistrictVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.DistrictPaymentSettingService;
import com.isurpass.service.HiDistrictService;

/**
 * 小区
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/district")
@Scope(value = "prototype")
public class DistrictController extends BaseController{

	private static Log log = LogFactory.getLog(DistrictController.class);
	@Resource
	private HiDistrictService hiDistrictService;
	@Resource
	private DistrictPaymentSettingService districtPaymentSettingService;

	/**
	  * @Description: 新增小区
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/saveDistrict")
	public @ResponseBody Map<String , Object> saveDistrict(@ModelAttribute("district")District district){
		try{
			if(StringUtil.checkNull(district.getDistrictName()) || StringUtil.checkNull(district.getAddress())
					|| district.getPrice() == null || district.getShareAmount() == null){
				throw new BusinessException(getRB().getString("jcommunitynameetc"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(sessionPersonVo.getType()==1&&sessionPersonVo.getSuperpersonid()!=null&&sessionPersonVo.getSuperpersonid()!=0){
				throw new BusinessException("没有权限哦");
			}
			
			hiDistrictService.saveDistrict(district, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jaddsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 修改小区
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/updateDistrict")
	public @ResponseBody Map<String , Object> updateDistrict(@ModelAttribute("district")District district){
		try{
			if(district.getId()== null || StringUtil.checkNull(district.getDistrictName()) || StringUtil.checkNull(district.getAddress())){
				throw new BusinessException(getRB().getString("jidetc"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			hiDistrictService.updateDistrict(district, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jsave") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 分页查询小区
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("districtVo")DistrictVo districtVo){
		try{
			if(districtVo.getPage() == null || districtVo.getRows() == null){
				throw new BusinessException(getRB().getString("pagerows"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			districtVo.setPersonId(sessionPersonVo.getId());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			BasicScroll scroll = new BasicScroll(districtVo.getPage(), districtVo.getRows());
			List<District> lst = hiDistrictService.findPage(districtVo, scroll,idList);
			
			boolean isSecondAdmin = false;
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				isSecondAdmin = true;
			}
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst,isSecondAdmin);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 查询小区详细信息
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findDistrictById")
	public @ResponseBody Map<String , Object> findDistrictById(@ModelAttribute("districtVo")DistrictVo districtVo){
		try{
			if(districtVo.getId() == null){
				throw new BusinessException(getRB().getString("id"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			District dbDistrict = hiDistrictService.findById(districtVo.getId());
			District.checkSecondAdminRole(dbDistrict, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , dbDistrict);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	
	/**
	  * @Description: 查询小区列表
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findDistrict")
	public @ResponseBody Map<String , Object> findDistrict(@ModelAttribute("districtVo")DistrictVo districtVo){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<District> districtList = hiDistrictService.findByPersonId(sessionPersonVo.getId(),idList);
			
			return createResponse(1 , getRB().getString("jquerysuccess") , districtList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 删除小区
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/deleteDistrict")
	public @ResponseBody Map<String , Object> deleteDistrict(@ModelAttribute("districtVo")DistrictVo districtVo){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(districtVo.getId()==null){
				throw new BusinessException(getRB().getString("id"));
			}
			if(sessionPersonVo.getType()==1&&sessionPersonVo.getSuperpersonid()!=null&&sessionPersonVo.getSuperpersonid()!=0){
				throw new BusinessException("没有权限哦");
			}
			hiDistrictService.deleteDistrict(districtVo.getId(), sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jdelete"),null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/paymentsetting")
	public String paymentsetting(int districtid, ModelMap map)
	{
		DistrictPaymentSetting dps = this.districtPaymentSettingService.querybydistrictid(districtid);
		if ( dps == null )
		{
			dps = new DistrictPaymentSetting();
			dps.setDistrictid(districtid);
		}
		
		District d = this.hiDistrictService.query(districtid);
		
		map.put("dps", dps);
		map.put("district", d);
		
		return "district/districtpaymentsetting";
	}

	@RequestMapping(value="/updatedistrictpaymentsetting")
	public @ResponseBody Map<String , Object> updateDistrictPaymentSetting(@ModelAttribute("DistrictPaymentSetting")DistrictPaymentSetting dps)
	{
		District d = this.hiDistrictService.query(dps.getDistrictid());
		
		PersonVo sessionPersonVo = super.getCurrentUser();
		if ( d == null )
			return  createResponse(-1 , getRB().getString("jnoauth"),null);
		
		District.checkSecondAdminRole(d, sessionPersonVo,getRB());
		this.districtPaymentSettingService.saveDistrictPaymentSetting(dps);
		
		return  createResponse(0 , getRB().getString("modifysuccess"),null);
	}
	
	
	public void setDistrictPaymentSettingService(DistrictPaymentSettingService districtPaymentSettingService)
	{
		this.districtPaymentSettingService = districtPaymentSettingService;
	}
	
}
