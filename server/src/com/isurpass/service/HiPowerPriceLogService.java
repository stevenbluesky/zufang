package com.isurpass.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ant.business.Device;
import com.ant.business.District;
import com.ant.business.PowerPriceLog;
import com.ant.vo.PowerPriceLogVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;
import com.isurpass.common.hibernate.HqlWrap;

/**
 * 电价日志表service
 *
 */
public class HiPowerPriceLogService extends BaseService<PowerPriceLog> 
{

	/**
	 * 保存电价日志
	 * @param dbDistrict
	 * @param dbDevice
	 */
	public float savePowerPriceLog(District dbDistrict,Device dbDevice,String month,Float currentDegrees)
	{
		Integer price = 0 ;
		if ( dbDevice.getDeviceType() == 1 )
			price = dbDistrict.getPrice();
		else if ( dbDevice.getDeviceType() == 17 )
		{
			if ( dbDevice.getDevicesubtype() == 1 )
				price = dbDistrict.getWaterprice();
			else if ( dbDevice.getDevicesubtype() == 2 )
				price = dbDistrict.getHotwaterprice();
		}
			
		/**相减大于0且小于阀值才保存，否则当换了电表*/
		PowerPriceLog dbPowerPriceLog = this.findPowerPriceLog(dbDevice.getId(), month);
		if(dbPowerPriceLog == null){
			/**保存电价日志*/
			dbPowerPriceLog = new PowerPriceLog();
			dbPowerPriceLog.setBaseDegrees(dbDevice.getBaseDegrees());
			dbPowerPriceLog.setCurrentDegrees(currentDegrees);
			dbPowerPriceLog.setDeviceId(dbDevice.getId());
			dbPowerPriceLog.setInputDate(new Date());
			dbPowerPriceLog.setMonth(month);
			dbPowerPriceLog.setPersonId(dbDevice.getPersonId());
			if(dbDevice.getInitPreMonthDegrees() != null && dbDevice.getInitPreMonthDegrees() == 1){
				/**需要初始化月初讀數*/
				dbPowerPriceLog.setPreMonthDegrees(dbDevice.getBaseDegrees());
			}else{
				dbPowerPriceLog.setPreMonthDegrees(dbDevice.getCurrentDegrees());//第一次保存，则上个月读数为设备以前的读数
			}
			dbPowerPriceLog.setPrice(price);
			dbPowerPriceLog.setMonthDegrees(currentDegrees - dbPowerPriceLog.getPreMonthDegrees());	//当月电量
			dbPowerPriceLog.setShareAmount(dbDistrict.getShareAmount());
			dbPowerPriceLog.setSumDegrees(currentDegrees - dbDevice.getBaseDegrees());
			dbPowerPriceLog.setSumAmount(new BigDecimal(dbPowerPriceLog.getMonthDegrees() * dbPowerPriceLog.getPrice() ).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
			
			super.save(dbPowerPriceLog);
		}else{
			dbPowerPriceLog.setBaseDegrees(dbDevice.getBaseDegrees());
			dbPowerPriceLog.setCurrentDegrees(currentDegrees);
			dbPowerPriceLog.setInputDate(new Date());
			if(dbDevice.getInitPreMonthDegrees() != null && dbDevice.getInitPreMonthDegrees() == 1){
				/**需要初始化月初讀數*/
				dbPowerPriceLog.setPreMonthDegrees(dbDevice.getBaseDegrees());
			}
			dbPowerPriceLog.setMonthDegrees(currentDegrees - dbPowerPriceLog.getPreMonthDegrees());	//当月电量
			dbPowerPriceLog.setPrice(price);	//重新更新单价
			dbPowerPriceLog.setShareAmount(dbDistrict.getShareAmount());	//重新更新均摊费用
			dbPowerPriceLog.setSumDegrees(currentDegrees - dbDevice.getBaseDegrees());
			dbPowerPriceLog.setSumAmount(new BigDecimal(dbPowerPriceLog.getMonthDegrees() * dbPowerPriceLog.getPrice()).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
			super.update(dbPowerPriceLog);
		}
		HiRoomFeeService hiRoomFeeService = super.createService(HiRoomFeeService.class);
		hiRoomFeeService.updateRoomFee(dbDistrict, dbDevice, Integer.valueOf(month),dbPowerPriceLog.getPreMonthDegrees() ,currentDegrees, price,  dbPowerPriceLog.getSumAmount());
		
		return dbPowerPriceLog.getMonthDegrees();
	}
	
	/**
	 * 查询电价日志
	 * @param deviceId
	 * @param month
	 * @return
	 */
	public PowerPriceLog findPowerPriceLog(Long deviceId,String month)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("deviceId", deviceId));
		cw.add(ExpWrap.eq("month", month));
		return cw.uniqueResult();

	}
	
	/**
	 * 分页查询读数变更
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<PowerPriceLogVo> findPage(PowerPriceLogVo powerPriceLogVo,BasicScroll scroll)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select ppl , d , r , di ");
		hw.add("from PowerPriceLog ppl , Device d , Room r , District di " );
		hw.add("where d.bindRoomId = r.id and r.districtId = di.id and ppl.deviceId = d.id ");
		hw.addifnotnull("and d.personId = ? " , powerPriceLogVo.getPersonId() );
		hw.addifnotnull("and d.devicesubtype = ? " , powerPriceLogVo.getDevicesubtype() );
		hw.addifnotnull("and d.deviceType = ? " , powerPriceLogVo.getDevicetype() );
		hw.addifnotnull("and ppl.month = ? " , powerPriceLogVo.getMonth() );
		hw.addifnotnull("and r.districtId = ? " , powerPriceLogVo.getDistrictId() );
		hw.addifnotnull("and r.id = ? " , powerPriceLogVo.getRoomId() );
		hw.addifnotnull("and r.build = ? " , powerPriceLogVo.getBuild() );
		hw.addifnotnull("and r.unit = ? " , powerPriceLogVo.getUnit() );
		hw.addifnotnull("and r.floor = ? " , powerPriceLogVo.getFloor() );
		hw.add("order by ppl.month desc ");
		hw.setScroll(scroll);
		
		List<Object[]> lst = hw.list();
		return ServiceHelper.createPowerPriceLogVo(lst);
	}
	
	/**
	 * 查询电价
	 * @Description: 
	 * @author aijian
	 * @date 2016-6-7 上午12:30:11
	 * @version V1.0
	 */
	public List<Map<String , Object>> findPowerPriceLog(int month)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select di.districtName as districtName ,r.build as build ,r.unit as unit ,r.floor as floor ,r.roomName as roomName,");
		hw.add("ppl.preMonthDegrees as preMonthDegrees ,ppl.monthDegrees as monthDegrees , ppl.sumAmount as sumAmount ,r.grantUserName as grantUserName ,r.id as id" );
		hw.add("from PowerPriceLog ppl , Device d , Room r , District di ");
		hw.add("where ppl.month = ? " , month );
		hw.add("r.grantUserName is not null ");
		hw.setResultBeanClass(Map.class);
		return hw.list();
	}
	
}
