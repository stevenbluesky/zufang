package com.isurpass.service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import com.ant.business.Gateway;
import com.ant.business.Manager;
import com.ant.business.Person;
import com.ant.vo.GatewayVo;
import com.ant.vo.PtGatewayVo;
import com.isurpass.common.exception.BusinessException;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;
import com.isurpass.common.hibernate.HqlWrapnew;

/**
 * 网关service
 *
 */
public class HiGatewayService extends BaseService<Gateway> 
{
	@Resource
	private HiPersonService hiPersonService ;
	@Resource
	private HiOperateLogService hiOperateLogService ;

	/**
	 * 同步网关和设备信息
	 * @param sessionPersonVo
	 */
	public void syncGatewayAndDevice(PtGatewayVo ptGatewayVo)
	{
		HiManagerService hiManagerService = super.createService(HiManagerService.class);
		Manager dbManager = hiManagerService.findByLoginName(ptGatewayVo.getLoginname());
		if(dbManager == null){
			throw new BusinessException("管理员账号错误。");
		}
		
		/**保存网关信息*/
		Gateway dbGateway = this.saveGateway(ptGatewayVo, dbManager);
		
		/**保存设备信息*/
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		hiDeviceService.saveDevice(ptGatewayVo, dbManager, dbGateway);
		
		/**保存日志*/
		Person dbPerson = hiPersonService.findById(dbGateway.getPersonId());
		hiOperateLogService.saveOperateLog(12, "编辑【" + dbGateway.getGatewayName() + "】的信息", 
				"", dbPerson.getId(), null,
				"", dbGateway.getId(),dbGateway.getGatewayName(),null,null);
	}
	
	/**
	 * 同步网关和设备信息
	 * @param sessionPersonVo
	 */
	public void syncGatewayAndDevice(PtGatewayVo ptGatewayVo,boolean ifChangeSingalFlag)
	{
		HiManagerService hiManagerService = super.createService(HiManagerService.class);
		Manager dbManager = hiManagerService.findByLoginName(ptGatewayVo.getLoginname());
		if(dbManager == null){
			throw new BusinessException("管理员账号错误。");
		}
		
		/**保存网关信息*/
		Gateway dbGateway = this.saveGateway(ptGatewayVo, dbManager,ifChangeSingalFlag);
		
		/**保存设备信息*/
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		hiDeviceService.saveDevice(ptGatewayVo, dbManager, dbGateway);
		
		/**保存日志*/
		Person dbPerson = hiPersonService.findById(dbGateway.getPersonId());
		hiOperateLogService.saveOperateLog(12, "编辑【" + dbGateway.getGatewayName() + "】的信息", 
				"", dbPerson.getId(), null,
				"", dbGateway.getId(),dbGateway.getGatewayName(),null,null);
	}
	/**
	 * 保存网关信息
	 * @param ptGatewayVo
	 * @param sessionPersonVo
	 */
	public Gateway saveGateway(PtGatewayVo ptGatewayVo,Manager dbManager,boolean ifChangeSingalFlag){
		
		/**按平台id查询网关信息*/
		Gateway dbGateway = this.findByPtGatewayid(ptGatewayVo.getDeviceid());
		if(dbGateway == null){
			/**新增*/
			Gateway newGateway = new Gateway();
			newGateway.setDistrictId(dbManager.getDistrictId());
			newGateway.setGatewayName(ptGatewayVo.getName());
			newGateway.setInputDate(new Date());
			newGateway.setManagerId(dbManager.getId());
			newGateway.setPersonId(dbManager.getPersonId());
			newGateway.setPtGatewayid(ptGatewayVo.getDeviceid());
			if(ifChangeSingalFlag){
				newGateway.setSignalFlag(ptGatewayVo.getGatewayonline()==0?1:0);
			}else{
				newGateway.setSignalFlag(0);
			}
			super.save(newGateway);
			return newGateway;
		}else{
			/**修改*/
			dbGateway.setDistrictId(dbManager.getDistrictId());
			dbGateway.setGatewayName(ptGatewayVo.getName());
			if(ifChangeSingalFlag){
				dbGateway.setSignalFlag(ptGatewayVo.getGatewayonline()==0?1:0);
			}
			super.update(dbGateway);
			return dbGateway;
		}
	}
	/**
	 * 保存网关信息
	 * @param ptGatewayVo
	 * @param sessionPersonVo
	 */
	public Gateway saveGateway(PtGatewayVo ptGatewayVo,Manager dbManager){
		
		/**按平台id查询网关信息*/
		Gateway dbGateway = this.findByPtGatewayid(ptGatewayVo.getDeviceid());
		if(dbGateway == null){
			/**新增*/
			Gateway newGateway = new Gateway();
			newGateway.setDistrictId(dbManager.getDistrictId());
			newGateway.setGatewayName(ptGatewayVo.getName());
			newGateway.setInputDate(new Date());
			newGateway.setManagerId(dbManager.getId());
			newGateway.setPersonId(dbManager.getPersonId());
			newGateway.setPtGatewayid(ptGatewayVo.getDeviceid());
			newGateway.setSignalFlag(0);	//默认在线
			super.save(newGateway);
			return newGateway;
		}else{
			/**修改*/
			dbGateway.setDistrictId(dbManager.getDistrictId());
			dbGateway.setGatewayName(ptGatewayVo.getName());
			super.update(dbGateway);
			return dbGateway;
		}
	}
	
	/**
	 * 根据平台网关id查询网关信息
	 * @param ptGatewayid
	 * @return
	 */
	public Gateway findByPtGatewayid(String ptGatewayid)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("ptGatewayid", ptGatewayid));
		return cw.uniqueResult();
	}
	
	/**
	 * 分页查询网关
	 * @param gatewayVo
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<GatewayVo> findPage(GatewayVo gatewayVo,BasicScroll scroll,List<Long> idList)
	{
		if(idList.size()==0)
			idList.add((long)-1);
		HqlWrapnew hw = new HqlWrapnew(sessionFactory);
		hw.add("from Gateway g , District d ");
		hw.add("where g.districtId = d.id ");
		hw.addifnotnull("and g.signalFlag = :signalFlag ","signalFlag", gatewayVo.getSignalFlag());
		hw.addifnotnull("and g.districtId = :districtId  ","districtId", gatewayVo.getDistrictId());
		hw.addifnotnull("and d.provinceCode = :provinceCode ","provinceCode", gatewayVo.getProvinceCode());
		hw.addifnotnull("and d.cityCode = :cityCode ","cityCode", gatewayVo.getCityCode());
		hw.addifnotnull("and d.areasCode = :areasCode ","areasCode", gatewayVo.getAreasCode());
		hw.addifnotnull("and d.address = :address ","address", gatewayVo.getAddress());
		hw.addifnotnull("and d.provinceCode = :provinceCode ","provinceCode", gatewayVo.getProvinceCode());
		hw.addifnotnull("and d.id in :idList ","idList", idList);
		hw.setScroll(scroll);
		List<Object[]> ol = hw.list();
		return ServiceHelper.createGatewayVoList(ol);
	}
	
	/**
	 * 查询网关详情
	 * @param id
	 * @return
	 */
	public Gateway findById(Long id)
	{
		return super.query(id);
	}
	
	/**
	 * 更新信号状态
	 * @param ptGatewayid
	 * @param signFlag
	 */
	public void updateSingnalFlag(String ptGatewayid,Integer signalFlag){
		Gateway dbGateway = this.findByPtGatewayid(ptGatewayid);
		dbGateway.setSignalFlag(signalFlag);
		if(signalFlag == 1){
			/**离线，保存日志*/
			hiOperateLogService.saveOperateLog(18, "【" + dbGateway.getGatewayName() + "】网关离线" , 
					"", dbGateway.getPersonId(),null, "" , dbGateway.getId(),dbGateway.getGatewayName(),null,null);
		}else if(signalFlag == 0){
			/**上线，保存日志*/
			hiOperateLogService.saveOperateLog(19, "【" + dbGateway.getGatewayName() + "】网关上线" , 
					"", dbGateway.getPersonId(),null, "" , dbGateway.getId(),dbGateway.getGatewayName(),null,null);
		}
		super.update(dbGateway);
	}
	
	
	/**
	 * 获取用户设备数量
	 * @param personId
	 * @return
	 */
	public Integer findGatewayCount(Long personId,List<Long> idList)
	{
		return findCountByPersonId(personId,idList);
	}
	
	/**
	 * 删除网关
	 * @param ptDeviceId
	 * @param signFlag
	 */
	public void deleteGateway(String ptGatewayId){
		Gateway dbGateway = this.findByPtGatewayid(ptGatewayId);
		if(dbGateway != null)
			super.delete(dbGateway);
	}
	
	/**
	 * 根据用户id查询数量
	 * @param personId
	 * @return
	 */
	public int findCountByPersonId(Long personId,List<Long> idList)
	{
		if(idList.size()==0)
			idList.add((long)-1);
		CriteriaWrap cw = super.createCriteriaWrap();
		//cw.add(ExpWrap.eq("personId", personId));
		cw.add(ExpWrap.in("districtId", idList));
		return cw.count();
	}
	
}
