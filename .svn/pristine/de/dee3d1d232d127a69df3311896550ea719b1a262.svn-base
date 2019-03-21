package com.isurpass.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ant.business.Room;
import com.ant.vo.RoomVo;
import com.isurpass.test.util.TestUtil;

public class HiRoomServiceTest
{
	public static void main(String arg[])
	{
		HiRoomService rs = (HiRoomService) TestUtil.getBean("HiRoomService");
		RoomVo roomVo = new RoomVo();
		roomVo.setDistrictId(162L);
		List<Room> lst = rs.findRoom(roomVo);
		
		for ( Room rv : lst )
			System.out.println(JSON.toJSONString(rv));;
		
		System.out.println(JSON.toJSONString(lst));
	}
}
