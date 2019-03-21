package com.ant.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.business.CommuLog;
import com.ant.business.District;
import com.ant.business.DistrictPaymentSetting;
import com.ant.business.Room;
import com.ant.business.Transaction;
import com.ant.constant.TransactionStatus;
import com.ant.constant.TransactionType;
import com.isurpass.service.DistrictPaymentSettingService;
import com.isurpass.service.HiDistrictService;
import com.isurpass.service.HiRoomFeeService;
import com.isurpass.service.HiRoomPrivilegeService;
import com.isurpass.service.HiRoomService;
import com.isurpass.service.RoomFeeCalculatorService;
import com.isurpass.service.TransactionService;
import com.ant.vo.PersonVo;
import com.ant.vo.RoomVo;
import com.isurpass.common.exception.BusinessException;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.winxipay.ResponsetoCharge;
import com.isurpass.common.winxipay.ResponsetoWeixin;
import com.isurpass.common.winxipay.WeixinPayCall;

@Controller
@RequestMapping(value="/service/roomfee")
@Scope(value = "prototype")
public class RoomFeeController extends BaseController
{
	private static Log log = LogFactory.getLog(RoomFeeController.class);
	
	@Resource
	private HiDistrictService districtService;
	@Resource
	private HiRoomService roomService;
	@Resource
	private HiRoomFeeService roomFeeService;
	@Resource
	private HiRoomPrivilegeService roomPrivilegeService;
	@Resource
	private TransactionService transactionService;
	@Resource
	private RoomFeeCalculatorService roomFeeCalculatorService;
	@Resource
	private DistrictPaymentSettingService districtPaymentSettingService;
	
	public void setRoomService(HiRoomService roomService) {
		this.roomService = roomService;
	}

	@RequestMapping(value="/recharge")
	public @ResponseBody Map<String , Object> recharge(@ModelAttribute("room")Room room , Integer rechargetype)
	{
		try
		{
			if(room.getId() == null )
				throw new BusinessException(getRB().getString("roomidcnbb"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			roomService.recharge(room, rechargetype , sessionPersonVo);

			return createResponse(1 , getRB().getString("rfctopups") , null);
		}		
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/withdraw")
	public @ResponseBody Map<String , Object> withdraw(@ModelAttribute("room")Room room)
	{
		try
		{
			if(room.getId() == null )
				throw new BusinessException(getRB().getString("roomidcnbb"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			roomService.withdraw(room, sessionPersonVo,getRB());
			return createResponse(0 , getRB().getString("rfcrs") , null);
		}		
		catch (BusinessException e )
		{
			log.error(e.getMessage());
			return super.createErrorResponse(-1, e.getMessage());
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/charge")
	public @ResponseBody Map<String , Object> charge(Integer roomid)
	{
		if ( roomid == null )
			return createResponse(-1 , getRB().getString("roomidcnbb") , null);
		
		if ( roomFeeCalculatorService.chargeroom(roomid))
			return createResponse(0 , getRB().getString("rfccs") , null);
		return createResponse(-1 , getRB().getString("rfccf") , null);
	}
	
	@RequestMapping(value="/querytransactionlist")
	public @ResponseBody Map<String , Object> querytransactionlist(Integer roomid , Integer page , Integer rows)
	{
		if ( roomid == null )
			return createResponse(-1 , getRB().getString("roomidcnbb") , null);
		
		BasicScroll scroll = new BasicScroll(page, rows);
		List<Transaction> lst = transactionService.queryTransaction(roomid, scroll);
		for(Transaction l:lst){
			switch(l.getTransactiontype()){
			case 1:
				l.setDescription(getRB().getString("t1"));
				break;
			case 2:
				l.setDescription(getRB().getString("t2"));
				break;
			case 3:
				l.setDescription(getRB().getString("t3"));
				break;
			case 4:
				l.setDescription(getRB().getString("t4"));
				break;
			case 5:
				l.setDescription(getRB().getString("t5"));
				break;
			case 6:
				l.setDescription(getRB().getString("t6"));
				break;
			case 7:
				l.setDescription(getRB().getString("t7"));
				break;
			case 8:
				l.setDescription(getRB().getString("t8"));
				break;
			default:
				break;
			}
		}
		return createResponse(0 , "" , scroll.getRows(), lst);
	}
	
	@RequestMapping(value="/queryuserrooms")
	public String queryuserrooms(String phonenumber , ModelMap map )
	{
		if ( StringUtils.isBlank(phonenumber))
			return "fee/selfcharge/inputphonenumber";
		
		map.put("phonenumber", phonenumber);
		
		List<RoomVo> lst = this.roomService.queryFeeRoombyPhonenumber(phonenumber);
		
		if ( lst == null || lst.size() == 0 )
		{
			map.put("errormsg", "noroom");
			return "fee/selfcharge/inputphonenumber";
		}
		
		if ( lst.size() == 1 )
		{
			map.put("room", lst.get(0));
			return "fee/selfcharge/charge";
		}
		else 
		{
			map.put("rooms", lst);
			return "fee/selfcharge/roomlist";
		}
	}
	
	@RequestMapping(value="/roomcharge")
	public String roomcharge(int roomid, ModelMap map)
	{
		RoomVo rv = new RoomVo();
		Room r = roomService.findById(new Long(roomid));
		if ( r == null )
			return "fee/selfcharge/inputphonenumber";
		
		District d = districtService.findById(r.getDistrictId());
		
		try {
			PropertyUtils.copyProperties(rv, r);
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		} 
		rv.setDistrictName(d.getDistrictName());
		
		map.put("room", rv);
		return "fee/selfcharge/charge";
	}
	
	
	@RequestMapping(value="/queryroomchargeresult")
	public String queryroomchargeresult(int roomid, ModelMap map)
	{
		map.put("askchargeresult", "true");
		return roomcharge(roomid , map);
	}
	
	@RequestMapping(value="/queryroombalance")
	public @ResponseBody Map<String , Object> queryRoomBalance(@ModelAttribute("roomVo")RoomVo roomVo , Boolean arrearageonly )
	{
		PersonVo sessionPersonVo = super.getCurrentUser();
		roomVo.setPersonId(sessionPersonVo.getId());
		List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
		List<RoomVo> lst = roomService.queryRoomFee(roomVo, arrearageonly,idList);
		int totalbalance = 0 ;
		int totalarrearage = 0 ;
		
		for ( RoomVo r : lst)
		{
			totalbalance += r.getBalance();
			totalarrearage += r.getArrearage();
		}
		
		Map<String , Object> map = new HashMap<String , Object>();

		map.put("success", 0);
		map.put("rooms", lst);
		map.put("totalbalance", totalbalance);
		map.put("totalarrearage", totalarrearage);
		
		return map;
	}
	
	@RequestMapping(value="/chargeallroom")
	public @ResponseBody Map<String , Object> chargeallroom()
	{
		PersonVo sessionPersonVo = super.getCurrentUser();
				
		roomFeeCalculatorService.notifyarrearageUser(sessionPersonVo.getId(), false);
		
		return createResponse(0 , getRB().getString("rfccs") , null);
	}
	
	@RequestMapping(value="/weixincharge")
	@ResponseBody
	public ResponsetoCharge weixincharge(int roomid , float amount)
	{
		int at = (int)amount * 100 ;  //TODO

		Room r = roomService.findById(new Long(roomid));
		if ( r == null )
			return new ResponsetoCharge(1 , null ) ;
		District d =  districtService.findById(r.getDistrictId());
		if ( d == null )
			return new ResponsetoCharge(1 , null ) ;
		
		DistrictPaymentSetting dsp =  this.districtPaymentSettingService.querybydistrictid(r.getDistrictId().intValue());
		if ( dsp == null )
			return new ResponsetoCharge(1 , null ) ;
		
		String dscr = String.format(getRB().getString("dongchongzhi"), d.getDistrictName(), r.getBuild() , r.getRoomName());
		
		WeixinPayCall wfu = createWeixinPayCall(dsp);
		
		Transaction t = transactionService.createTransaction(roomid , at , TransactionType.wechatpay , TransactionStatus.unpaid); 

		JSONObject atcj = new JSONObject();
		atcj.put("trascationid", t.getTransactionid());
		atcj.put("districtid", d.getId().intValue());
		atcj.put("roomid", r.getId());
		
		String rturl = wfu.unifiedorder(dscr, getIpAddr(), at , t.getTransactionid(), atcj);
		
		ResponsetoCharge rst = null ;
		if ( StringUtils.isBlank(rturl))
			rst = new ResponsetoCharge(1 , null ) ;
		else
		{
			try {
				rst = new ResponsetoCharge(0 , rturl + "&redirect_url=" + URLEncoder.encode( createRedirectPath(roomid) , "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage() , e);
			}
		}
		return rst ;
	}
	
	
	
	@RequestMapping(value="/winxinnotify")
	@ResponseBody
	public ResponsetoWeixin winxinnotify(@RequestBody byte[]bytes)
	{
		try {
			String rqstr = new String(bytes , "UTF-8");
			log.info(rqstr);
			
			Map<String , String> pm = WeixinPayCall.parseResult(rqstr);

			String attach = pm.get("attach");
			if ( StringUtils.isBlank(attach))
				return new ResponsetoWeixin() ;
			
			JSONObject json = JSON.parseObject(attach);
			if ( !json.containsKey("trascationid") || !json.containsKey("districtid"))
				return new ResponsetoWeixin() ;
			
			int districtid= json.getIntValue("districtid");
			
			WeixinPayCall wfu = createWeixinPayCall(districtid);
			
			if ( wfu == null )
				return new ResponsetoWeixin() ;
			
			if ( wfu.checkSing(pm) == false )
				return new ResponsetoWeixin();
			
			int transactionid = json.getIntValue("trascationid");
			
			transactionService.transactionFinished(transactionid, json , pm);
			
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		}
		
		return new ResponsetoWeixin();

	}
	
	private WeixinPayCall createWeixinPayCall(int districtid)
	{
		DistrictPaymentSetting dsp =  this.districtPaymentSettingService.querybydistrictid(districtid);
		return this.createWeixinPayCall(dsp);
	}
	
	private WeixinPayCall createWeixinPayCall(DistrictPaymentSetting d)
	{
		if ( d == null )
			return null ;
		return new WeixinPayCall(d.getWexinpayappid() , d.getWexinpaymchid() , d.getWexinpayapikey() ,getServerName() , getContextPath() ,d.getWexinpayappname());
	}

	public void setRoomPrivilegeService(HiRoomPrivilegeService roomPrivilegeService) {
		this.roomPrivilegeService = roomPrivilegeService;
	}

	public void setRoomFeeService(HiRoomFeeService roomFeeService) {
		this.roomFeeService = roomFeeService;
	}
	
	public static String getIpAddr()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	private String createRedirectPath(int roomid)
	{
		StringBuffer sb = new StringBuffer();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		sb.append("http://").append(request.getServerName()) ;
		sb.append(request.getContextPath());
		sb.append("/service/roomfee/queryroomchargeresult?roomid=").append(roomid);

		return sb.toString();
	}

	private String getContextPath()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getContextPath();
	}
	
	private String getServerName()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getServerName();
	}
	
	public void setDistrictService(HiDistrictService districtService) {
		this.districtService = districtService;
	}

	public void setTransactionService(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}

	public void setRoomFeeCalculatorService(RoomFeeCalculatorService roomFeeCalculatorService)
	{
		this.roomFeeCalculatorService = roomFeeCalculatorService;
	}

	public void setDistrictPaymentSettingService(DistrictPaymentSettingService districtPaymentSettingService)
	{
		this.districtPaymentSettingService = districtPaymentSettingService;
	}
}
