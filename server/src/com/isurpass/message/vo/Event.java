package com.isurpass.message.vo;

import java.util.Date;

public class Event
{
	private int id ;
	private int thirdpartid;
	private String type;
	private String deviceid ;
	private int zwavedeviceid ;
	private int intparam;
	private Float floatparam;
	private String objparam;
	private Date eventtime;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getThirdpartid()
	{
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid)
	{
		this.thirdpartid = thirdpartid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getDeviceid()
	{
		return deviceid;
	}
	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}
	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	public int getIntparam()
	{
		return intparam;
	}
	public void setIntparam(int intparam)
	{
		this.intparam = intparam;
	}
	public Float getFloatparam()
	{
		return floatparam;
	}
	public void setFloatparam(Float floatparam)
	{
		this.floatparam = floatparam;
	}
	public String getObjparam()
	{
		return objparam;
	}
	public void setObjparam( String objparam)
	{
		this.objparam = objparam;
	}
	public Date getEventtime()
	{
		return eventtime;
	}
	public void setEventtime(Date eventtime)
	{
		this.eventtime = eventtime;
	}

}
