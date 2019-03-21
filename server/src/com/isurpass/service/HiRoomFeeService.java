package com.isurpass.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


import com.ant.business.Device;
import com.ant.business.District;
import com.ant.business.RoomFee;
import com.ant.util.Utils;
import com.ant.vo.RoomFeeVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;
import com.isurpass.common.hibernate.HqlWrap;

public class HiRoomFeeService extends BaseService<RoomFee> {

	public void updateRoomFee(District dbDistrict, Device dbDevice, int month, Float premonthdegree, Float currentdegree, int price , Integer fee ) 
	{
		RoomFee rf = findbyRoomidMonth(dbDevice.getBindRoomId() , month);
		boolean newrf = false ;
		
		if ( rf == null )
		{
			newrf = true ;
			rf = new RoomFee();
			rf.setMonth(month);
			rf.setRoomid(dbDevice.getBindRoomId());
		}
		
		if ( dbDevice.getDeviceType() == 1 )
		{
			rf.setElectricityfee(fee);
			rf.setElectricitypremonthdegree(premonthdegree);
			rf.setElectricitycurrentdegrees(currentdegree);
			rf.setElectricityprice(price);
		}
		else if ( dbDevice.getDeviceType() == 17 && dbDevice.getDevicesubtype() == 1)
		{
			rf.setWaterfee(fee);
			rf.setWaterpremonthdegree(premonthdegree);
			rf.setWatercurrentdegrees(currentdegree);
			rf.setWaterprice(price);
		}
		else if ( dbDevice.getDeviceType() == 17 && dbDevice.getDevicesubtype() == 2)
		{
			rf.setHotwaterfee(fee);
			rf.setHotwaterpremonthdegree(premonthdegree);
			rf.setHotwatercurrentdegrees(currentdegree);
			rf.setHotwaterprice(price);
		}
		rf.setPooledfee(dbDistrict.getShareAmount());
		
		rf.setTotalamount(rf.getWaterfee() + rf.getHotwaterfee() + rf.getElectricityfee() + rf.getPooledfee());
		
		if ( newrf == true )
			this.save(rf);
		else 
			this.update(rf);
	}

	public void updateRentFee(RoomFee rf , int rentfee)
	{
		RoomFee rfdb = findbyRoomidMonth(rf.getRoomid() , rf.getMonth());
		if ( rfdb == null )
			return ;
		rfdb.setRentfee(rentfee);
		this.update(rfdb);
	}
	
	public List<RoomFeeVo> findPage(RoomFeeVo roomfeevo, BasicScroll scroll) 
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select rf.month as month ,rf.electricityfee as electricityfee ,rf.waterfee as waterfee ,rf.hotwaterfee as hotwaterfee,");
		hw.add("rf.pooledfee as pooledfee ,rf.totalamount as totalamount ,di.districtName as districtName ,r.districtId as districtId ,");
		hw.add("r.build as build ,r.unit as unit ,r.floor as floor ,r.roomName as roomName ,rf.roomid as roomid , rf.rentfee as rentfee ");
		hw.add("from RoomFee rf , Room r , District di ");
		hw.add("where rf.roomid = r.id and r.districtId = di.id ");
		hw.addifnotnull("and di.personId = ? ", roomfeevo.getPersonId());
		hw.addifnotnull("and rf.month = ? ", roomfeevo.getMonth());
		hw.addifnotnull("and r.districtId = ? ", roomfeevo.getDistrictId());
		hw.addifnotnull("and r.id = ? ", roomfeevo.getRoomid());
		hw.addifnotnull("and r.build = ? ", roomfeevo.getBuild());
		hw.addifnotnull("and r.unit = ? ", roomfeevo.getUnit());
		hw.addifnotnull("and r.floor = ? ", roomfeevo.getFloor());
		hw.setScroll(scroll);
		hw.setResultBeanClass(RoomFeeVo.class);
		return hw.list();
	}
	
	public RoomFee findbyRoomidMonth(long roomid , String month)
	{
		return findbyRoomidMonth(roomid , Integer.valueOf(month));
	}
	
	public RoomFee findbyRoomidMonth(long roomid , int month)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("roomid", roomid));
		cw.add(ExpWrap.eq("month", month));
		return cw.uniqueResult();
	}

	public List<Map<String , Object>> findbyMonth(String month)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select rf.roomfeeid as roomfeeid , rf.roomid as roomid , rf.month as month , rf.electricitypremonthdegree as electricitypremonthdegree , rf.electricitycurrentdegrees as electricitycurrentdegrees ," ); 
		hw.add("rf.electricityprice as electricityprice , rf.electricityfee as electricityfee , rf.waterfee as waterfee , rf.waterpremonthdegree as waterpremonthdegree , rf.watercurrentdegrees as watercurrentdegrees , " );
		hw.add("rf.waterprice as  waterprice, rf.hotwaterfee as  hotwaterfee, rf.hotwaterpremonthdegree as hotwaterpremonthdegree , rf.hotwatercurrentdegrees as hotwatercurrentdegrees , rf.hotwaterprice as hotwaterprice , " );
		hw.add("rf.pooledfee as pooledfee , rf.totalamount as totalamount , " );
		hw.add("di.districtName as districtName ,r.build as build,r.unit as unit,r.floor as floor,r.roomName as roomName ,rp.grantUserName as grantUserName ,r.id as id");
		hw.add("where  rf.roomid = r.id and r.districtId = di.id and r.id = rp.roomId  ");
		hw.add("and rf.month = ? " , month);
		
		Calendar c = Calendar.getInstance();
		Utils.parseTime(month , "yyyyMM");
		c.setTime(Utils.parseTime(month , "yyyyMM"));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		String d = Utils.formatTime(c.getTime());

		hw.add("and rp.grantBeginDate <= ? " , d);
		hw.add("and rp.grantEndDate >= ? " , d);
		hw.add("rp.grantUserName is not null ");
		hw.setResultBeanClass(Map.class);
		
		return hw.list();

	}

}
