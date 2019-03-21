package com.isurpass.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.ant.business.Device;
import com.ant.business.District;
import com.ant.business.Gateway;
import com.ant.business.PowerPriceLog;
import com.ant.business.Room;
import com.ant.vo.DeviceVo;
import com.ant.vo.GatewayVo;
import com.ant.vo.PowerPriceLogVo;

public class ServiceHelper
{
	
	public static List<GatewayVo> createGatewayVoList(List<Object[]> lst)
	{
		List<GatewayVo> rl = new ArrayList<GatewayVo>();
		for (  Object[] oa : lst  )
			rl.add(createGatewayVo(find(District.class , oa) , find(Gateway.class , oa)));
		return rl ;
	}
	
	public static GatewayVo createGatewayVo(District di , Gateway g)
	{
		GatewayVo vo = new GatewayVo();
		if ( di != null )
			copyProperties(di , vo);
		
		if ( g != null  ) 
			copyProperties(g , vo);
		
		return vo ;
	}
	
	public static List<PowerPriceLogVo> createPowerPriceLogVo(List<Object[]> lst)
	{
		List<PowerPriceLogVo> rl = new ArrayList<PowerPriceLogVo>();
		for (  Object[] oa : lst  )
			rl.add(createPowerPriceLogVo(find(PowerPriceLog.class , oa) , find(Room.class , oa), find(District.class , oa)));
		return rl ;
	}
	
	public static PowerPriceLogVo createPowerPriceLogVo(PowerPriceLog ppl , Room r , District di)
	{
		PowerPriceLogVo vo = new PowerPriceLogVo();
		if ( r != null )
		{
			copyProperties(r , vo);
			vo.setRoomId(r.getId());
		}
		if ( ppl != null )
			copyProperties(ppl , vo);
		if ( di != null )
			vo.setDistrictName(di.getDistrictName());
		
		return vo ;
	}
	
	public static <T> T find(Class<T> clz , Object[] oa)
	{
		if ( oa == null )
			return null ;
		for ( int i = 0 ; i < oa.length ; i ++ )
			if ( clz.isInstance(oa[i]))
				return (T)oa[i] ;
		return null ;
	}
	
	public static DeviceVo createDeviceVo(Device d , District di , Gateway g , Room r  )
	{
		DeviceVo vo = new DeviceVo();
		
		if ( r != null )
			copyProperties(r , vo);
		
		if ( d != null )
			copyProperties(d , vo);

		if ( di != null )
			vo.setDistrictName(di.getDistrictName());
		
		if ( g != null && g.getSignalFlag() == 1 ) 
			vo.setSignalFlag(1);
			
		return vo ;
	}
	
	private static void copyProperties(Object src , Object dest)
	{
		try
		{
			PropertyUtils.copyProperties( dest , src);
		} 
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
}
