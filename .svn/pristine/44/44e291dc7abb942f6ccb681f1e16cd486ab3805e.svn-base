package com.isurpass.service;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import com.isurpass.common.exception.BusinessException;

import com.ant.business.District;
import com.ant.config.MjConfig;
import com.ant.controller.PersonController;
import com.ant.vo.DistrictVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;
import com.isurpass.common.hibernate.HqlWrap;

/**
 * 小区service
 * @author aijian
 *
 */
public class HiDistrictService extends BaseService<District>
{
	@Resource
	private HiProvinceService hiProvinceService ;
	@Resource
	private HiOperateLogService hiOperateLogService;

	/**
	 * 保存小区信息
	 * @param district
	 * @param rb 
	 */
	public void saveDistrict(District district,PersonVo sessionPersonVo, ResourceBundle rb){
		district.setPersonId(sessionPersonVo.getId());
		
		if(StringUtil.checkNotNull(district.getProvinceCode())){
			district.setProvinceName(hiProvinceService.getProvinceNameByCode(district.getProvinceCode()));
		}
		if(StringUtil.checkNotNull(district.getCityCode())){
			district.setCityName(hiProvinceService.getCityNameByCode(district.getCityCode()));
		}
		if(StringUtil.checkNotNull(district.getAreasCode())){
			district.setAreasName(hiProvinceService.getAreasNameByCode(district.getAreasCode()));
		}
		if(StringUtil.checkNull(district.getDistrictImg())){
			district.setDistrictImg(MjConfig.get("districtDefaultImgUrl"));
		}
		if(district.getPrice() == null)
			district.setPrice(0);
		if ( district.getWaterprice() == null )
			district.setWaterprice(0);
		if ( district.getHotwaterprice() == null )
			district.setHotwaterprice(0);
		if(district.getShareAmount() == null)
			district.setShareAmount(0);
		district.setManagerCount(0);
		district.setRoomCount(0);
		district.setInputDate(new Date());
		super.save(district);
		long personid = 0;
		int operatepersonid = sessionPersonVo.getId().intValue();
		if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
			personid = sessionPersonVo.getSuperpersonid().longValue();
		}else{
			personid = sessionPersonVo.getId();
		}
		/**保存日志*/
		hiOperateLogService.saveOperateLog(1, "添加小区【" + district.getDistrictName() + "】", sessionPersonVo.getRealName(), personid,operatepersonid, 
				sessionPersonVo.getIp(), district.getId(),district.getDistrictName(),null,null);
	}

	/**
	 * 修改小区信息
	 * @param district
	 * @param rb 
	 */
	public void updateDistrict(District district,PersonVo sessionPersonVo, ResourceBundle rb){

		District dbDistrict = this.findById(district.getId());
		
		District.checkSecondAdminRole(dbDistrict, sessionPersonVo,rb);
		dbDistrict.setProvinceCode(district.getProvinceCode());
		dbDistrict.setCityCode(district.getCityCode());
		dbDistrict.setAreasCode(district.getAreasCode());
		if(StringUtil.checkNotNull(dbDistrict.getProvinceCode())){
			dbDistrict.setProvinceName(hiProvinceService.getProvinceNameByCode(dbDistrict.getProvinceCode()));
		}
		if(StringUtil.checkNotNull(dbDistrict.getCityCode())){
			dbDistrict.setCityName(hiProvinceService.getCityNameByCode(dbDistrict.getCityCode()));
		}
		if(StringUtil.checkNotNull(dbDistrict.getAreasCode())){
			dbDistrict.setAreasName(hiProvinceService.getAreasNameByCode(dbDistrict.getAreasCode()));
		}
		dbDistrict.setDistrictImg(district.getDistrictImg());
		if(StringUtil.checkNull(dbDistrict.getDistrictImg())){
			dbDistrict.setDistrictImg(MjConfig.get("districtDefaultImgUrl"));
		}
		dbDistrict.setDistrictName(district.getDistrictName());
		dbDistrict.setRemark(district.getRemark());
		dbDistrict.setAddress(district.getAddress());
		dbDistrict.setShareAmount(district.getShareAmount());
		dbDistrict.setPrice(district.getPrice());
		dbDistrict.setWaterprice(district.getWaterprice());
		dbDistrict.setHotwaterprice(district.getHotwaterprice());
		super.update(dbDistrict);
		long personid = 0;
		int operatepersonid = sessionPersonVo.getId().intValue();
		if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
			personid = sessionPersonVo.getSuperpersonid().longValue();
		}else{
			personid = sessionPersonVo.getId();
		}
		/**保存日志*/
		hiOperateLogService.saveOperateLog(3, "修改小区【" + district.getDistrictName() + "】", sessionPersonVo.getRealName(), personid,operatepersonid, 
				sessionPersonVo.getIp(), district.getId(),district.getDistrictName(),null,null);
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public District findById(Long id)
	{
		return super.query(id);
	}
	
	/**
	 * 根据id分页查询
	 */
	public List<District> findByPersonId(Long personId, BasicScroll scroll,List<Long> ids)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.in("ids", ids));
		cw.setScroll(scroll);
		return cw.list();
	} 
	
	
	/**
	 * 分页查询
	 * @param district
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<District> findPage(DistrictVo districtVo,BasicScroll scroll,List<Long> idList)
	{
		if(idList.size()==0)
			idList.add((long)-1);
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.in("id", idList));
		cw.addifNotNull(ExpWrap.eq("provinceCode", districtVo.getProvinceCode()));
		cw.addifNotNull(ExpWrap.eq("cityCode", districtVo.getCityCode()));
		cw.addifNotNull(ExpWrap.eq("areasCode", districtVo.getAreasCode()));
		cw.addifNotNull(ExpWrap.like("districtName", districtVo.getDistrictName()));
		cw.setScroll(scroll);
		return cw.list();
	}
	
	/**
	 * 更新房间数量
	 * @param districtId
	 * @param value 新增的值
	 */
	public void updateRoomCount(Long districtId,int value){
		District dbDistrict = this.findById(districtId);
		if(dbDistrict.getRoomCount() == null){
			dbDistrict.setRoomCount(0);
		}
		dbDistrict.setRoomCount(dbDistrict.getRoomCount() + value);
		super.update(dbDistrict);
	}
	
	/**
	 * 更新管理员数量
	 * @param districtId
	 * @param value 新增的值
	 */
	public void updateManagerCount(Long districtId,int value){
		District dbDistrict = this.findById(districtId);
		if(dbDistrict.getManagerCount() == null){
			dbDistrict.setManagerCount(0);
		}
		dbDistrict.setManagerCount(dbDistrict.getManagerCount() + value);
		super.update(dbDistrict);
	}
	
	/**
	 * 查询用户的小区列表
	 * @param personId
	 * @return
	 */
	public List<District> findByPersonId(Long personId)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.eq("personId", personId));
		return cw.list();
	} 
	public List<District> findByPersonId(Long personId,List<Long> idList)
	{
		if(idList.size()==0)
			idList.add((long)-1);
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.in("id", idList));
		return cw.list();
	} 
	
	/**
	 * 删除小区
	 * @param id
	 * @param sessionPersonVo
	 * @param rb 
	 */
	public void deleteDistrict(Long id,PersonVo sessionPersonVo, ResourceBundle rb){
		District dbDistrict = this.findById(id);
		District.checkRole(dbDistrict, sessionPersonVo,rb);
		if(dbDistrict.getRoomCount() > 0){
			throw new BusinessException(rb.getString("hdsdeleteroomfirst"));
		}
		super.delete(dbDistrict);
		long personid = 0;
		int operatepersonid = sessionPersonVo.getId().intValue();
		if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
			personid = sessionPersonVo.getSuperpersonid().longValue();
		}else{
			personid = sessionPersonVo.getId();
		}
		/**保存日志*/
		hiOperateLogService.saveOperateLog(2, "删除小区【" + dbDistrict.getDistrictName() +"】", sessionPersonVo.getRealName(), personid,operatepersonid,
				sessionPersonVo.getIp(), dbDistrict.getId(),dbDistrict.getDistrictName(),null,null);
	}
	
	/**
	 * 根据房间id查询小区信息
	 * @param roomId
	 * @return
	 */
	public District findDistrictByRoomId(Long roomId)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select d " );
		hw.add("from District d , Room r ");
		hw.add("where d.id=r.districtId and r.id = ? " , roomId );
		
		return hw.uniqueResult();
	}
	
}
