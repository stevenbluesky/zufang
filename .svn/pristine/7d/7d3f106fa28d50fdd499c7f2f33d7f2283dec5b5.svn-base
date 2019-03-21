package com.ant.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import com.isurpass.common.exception.BusinessException;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.ant.config.MjConfig;
import com.ant.restful.service.RestfulUtil;
import com.isurpass.service.HiRoomFeeService;

@Scope
public class SendDegreePriceController extends BaseController{

	@Resource
	private HiRoomFeeService hiRoomFeeService;


	public void sendDegreePrice(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String month = new SimpleDateFormat("yyyyMM").format(c.getTime());
		String preMonthFirstDay = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());//上个月第一天
		c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		String preMonthLastDay = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());//上个月最后一天
		
		List<Map<String , Object>> list = hiRoomFeeService.findbyMonth(month);
		for(Map<String , Object> tempMap : list){
			Map<String , Object> pmap = new HashMap<String , Object>();
			pmap.put("countrycode", "86");
			pmap.put("phonenumber",tempMap.get("grantUserName").toString());
			if( StringUtil.checkNotNull(tempMap.get("grantUserName")))
			{
				StringBuffer sb = new StringBuffer();
				sb.append(String.format(getRB().getString("sdpc1"), tempMap.get("districtName"),tempMap.get("build"),tempMap.get("unit"),tempMap.get("floor"),tempMap.get("roomName")));
				
				if ( tempMap.get("electricityfee") != null && !"0".equals(tempMap.get("electricityfee").toString()) )
					sb.append(String.format(getRB().getString("sdpc2"), 
							preMonthFirstDay,tempMap.get("electricitypremonthdegree").toString(),
							preMonthLastDay,tempMap.get("electricitycurrentdegrees").toString(), 
							((Float)tempMap.get("electricitycurrentdegrees") - (Float)tempMap.get("electricitypremonthdegree")),
							((Integer)tempMap.get("electricityfee")) / 100 ));
				
				if ( tempMap.get("hotwaterfee") != null && !"0".equals(tempMap.get("hotwaterfee").toString()) )
					sb.append(String.format(getRB().getString("sdpc3"), 
							preMonthFirstDay,(Float)tempMap.get("hotwaterpremonthdegree"),
							preMonthLastDay,(Float)tempMap.get("hotwatercurrentdegrees"), 
							((Float)tempMap.get("hotwatercurrentdegrees") - (Float)tempMap.get("hotwaterpremonthdegree")),
							((Integer)tempMap.get("hotwaterfee")) / 100 ));
				
				if ( tempMap.get("waterfee") != null && !"0".equals(tempMap.get("waterfee").toString()) )
					sb.append(String.format(getRB().getString("sdpc4"), 
							preMonthFirstDay,(Float)tempMap.get("waterpremonthdegree"),
							preMonthLastDay,(Float)tempMap.get("watercurrentdegrees"), 
							((Float)tempMap.get("watercurrentdegrees") - (Float)tempMap.get("waterpremonthdegree")),
							((Integer)tempMap.get("waterfee")).intValue() / 100 ));
					
				if ( tempMap.get("pooledfee") != null && !"0".equals(tempMap.get("pooledfee").toString()) )
					sb.append(String.format(getRB().getString("sdpc5"), ((Integer)tempMap.get("pooledfee")) / 100 ));
				
				sb.append(String.format(getRB().getString("sdpc6") , ((Integer)tempMap.get("totalamount")) / 100 ));
				
//				String text = "您的【" + tempMap.get("districtName").toString() + "】小区" + tempMap.get("build").toString() + "栋" + 
//					tempMap.get("unit").toString() + "单元" + tempMap.get("floor").toString() + "楼" + tempMap.get("roomName").toString() + "房间" + 
//					preMonthFirstDay + "电表读数" + tempMap.get("preMonthDegrees").toString() + "，" + preMonthLastDay + "电表读数" + 
//					(Float.parseFloat(tempMap.get("preMonthDegrees").toString()) + Float.parseFloat(tempMap.get("monthDegrees").toString()))  + 
//					"，上个月用电量" + tempMap.get("monthDegrees").toString() + "度，电费共计" + 
//					Integer.parseInt(tempMap.get("sumAmount").toString()) / 100 + "元";
				
				
				String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/sendsms", pmap,getRB());
				Map resultMap = JSON.parseObject(str,HashMap.class);
				if(StringUtil.checkNull(resultMap.get("resultCode"))){
					throw new BusinessException(getRB().getString("seecib"));
				}
				int resultCode = Integer.parseInt(resultMap.get("resultCode").toString());
				if(resultCode != 0){
					throw new BusinessException(getRB().getString("seeci") + resultCode);
				}
			}
		}
	}
	
}
