package com.isurpass.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ant.business.District;
import com.ant.business.DistrictPaymentSetting;
import com.ant.business.Room;
import com.ant.business.RoomFee;
import com.ant.business.RoomPrivilege;
import com.ant.business.SmsHistory;
import com.ant.business.Transaction;
import com.ant.config.MjConfig;
import com.ant.constant.CommonConstant;
import com.ant.constant.RoomFeeStatus;
import com.ant.constant.SmsStatus;
import com.ant.constant.TransactionStatus;
import com.ant.constant.TransactionType;
import com.ant.restful.service.RestfulUtil;

public class RoomFeeCalculatorService 
{
	private static Log log = LogFactory.getLog(RoomFeeCalculatorService.class);

	@Resource
	private HiRoomFeeService hiRoomFeeService;
	@Resource
	private HiRoomService hiRoomService;
	@Resource
	private HiDistrictService hiDistrictService;
	
	@Resource
	private HiRoomPrivilegeService hiRoomPrivilegeService;
	
	@Resource
	private SmsHistoryService smsHistoryService;
	
	@Resource
	private TransactionService transactionService;
	@Resource
	private DistrictPaymentSettingService districtPaymentSettingService;
	
	private int month;
	 
	private String SMS_TEMPLATE= "您的%s小区%s栋%s房间，%d用电%.2f度，电费%.2f元,用水%.2f吨，水费%.2f元，%s%s公摊%.2f元,房租%.2f元,合计%.2f元,扣费%.2f元，账户余额%.2f元，欠费%.2f元,点击自助充值%s";
	private String SMS_TEMPALTE_WITH_HOTWATER= "您的%s小区%s栋%s房间，%d用电%.2f度，电费%.2f元,用水%.2f吨，水费%.2f元，用热水%.2f吨，热水费%.2f元，公摊%.2f元,房租%.2f元,合计%.2f元,扣费%.2f元，账户余额%.2f元，欠费%.2f元,点击自助充值%s";;
	
	public void calculateRoomFee()
	{
		log.info("calculateRoomFee");
		List<District> lstdst = hiDistrictService.findByPersonId(null);
		
		for ( District d : lstdst )
		{
			DistrictPaymentSetting dps = this.districtPaymentSettingService.querybydistrictid(d.getId().intValue());
			if ( dps == null || dps.getAutofee() != 1 )
				continue;

			List<Room> rlst = queryRooms(d.getId());
			
			month = getPreMonth();
			
			for ( Room r : rlst)
			{
				List<RoomPrivilege> rplst = hiRoomPrivilegeService.findbyRoomid(r.getId());
				if ( rplst == null || rplst.size() == 0 )
					continue;

				RoomFee rf = hiRoomFeeService.findbyRoomidMonth(r.getId(), month);
				if ( rf != null && rf.getStatus() == RoomFeeStatus.hasbooked.ordinal())
					continue;
				
				if ( rf == null )
				{
					rf = new RoomFee();
					rf.setMonth(month);
					rf.setRoomid(r.getId());
					hiRoomFeeService.save(rf);
				}
				
				int actrualcharge = chargeroomfee(r , rf);
				String smsmsg = createSms(d , r , rf , actrualcharge );
				
				saveSms(d , r , smsmsg  , rplst);
			}
		}
	}
	
	public void notifyarrearageUser()
	{
		notifyarrearageUser(null , true);
	}
	
	public boolean chargeroom(int roomid )
	{
		Room r = hiRoomService.query(roomid);
		
		if ( r.getArrearage() == 0 || r.getArrearage() > r.getBalance())
			return false ;
		
		List<RoomPrivilege> rplst = queryRoomPrivilege(r.getId());
		if ( rplst == null || rplst.size() == 0 )
			return false;

		int actrualcharge = chargeroomfee(r);
		
		District d = this.hiDistrictService.findById(r.getDistrictId());
		month = getPreMonth();
		RoomFee rf = hiRoomFeeService.findbyRoomidMonth(r.getId(), month);
		if(rf==null){
			return false;
		}
		String smsmsg = createSms(d , r , rf , actrualcharge );
	
		saveSms(d ,r , smsmsg  , rplst);
		sendRoomFeeSms();
		return true;
	}
	
	public void notifyarrearageUser(Long personid , boolean sendmessagetoarrearageuser )
	{
		log.info("notifyarrearageUser");
		List<District> lstdst = hiDistrictService.findByPersonId(personid);
		
		for ( District d : lstdst )
		{
			DistrictPaymentSetting dps = this.districtPaymentSettingService.querybydistrictid(d.getId().intValue());
			if ( dps == null || dps.getAutofee() != 1 )
				continue;
			
			List<Room> rlst = queryRooms(d.getId());
			month = getPreMonth();
			
			for ( Room r : rlst)
			{
				if ( r.getArrearage() == 0 )
					continue;
				
				List<RoomPrivilege> rplst = queryRoomPrivilege(r.getId());
				if ( rplst == null || rplst.size() == 0 )
					continue;

				int actrualcharge = 0 ;
				if ( r.getBalance() >= r.getArrearage())
					actrualcharge = chargeroomfee(r);
				
				if ( sendmessagetoarrearageuser || actrualcharge > 0 )
				{
					RoomFee rf = hiRoomFeeService.findbyRoomidMonth(r.getId(), month);
					
					String smsmsg = createSms(d , r , rf , actrualcharge );
				
					saveSms(d ,r , smsmsg  , rplst);
				}
			}
		}
		sendRoomFeeSms();
	}
	
	public void sendRoomFeeSms()
	{
		log.info("sendRoomFeeSms");
		List<SmsHistory> lst = smsHistoryService.findnewSms();
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( SmsHistory sh : lst)
		{
			Map<String , Object> pmap = new HashMap<String , Object>();
			pmap.put("countrycode", sh.getCountrycode());
			pmap.put("phonenumber",sh.getPhonenumber());
			pmap.put("message",sh.getMessage());
			
			RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/sendsms", pmap);

			smsHistoryService.hasSent(sh);
		}
	}
	
	private List<RoomPrivilege> queryRoomPrivilege(long roomid)
	{
		List<RoomPrivilege> rplst = hiRoomPrivilegeService.findbyRoomid(roomid);
		if ( rplst == null || rplst.size() == 0 )
			return rplst ;
		
		Date now = new Date();
		for ( ListIterator<RoomPrivilege> it = rplst.listIterator() ; it.hasNext();)
		{
			RoomPrivilege rp = it.next();
			if ( now.after(rp.getGrantEndDate()))
				it.remove();
		}
		return rplst ;
			
	}
	
	private void saveSms(District d , Room r ,String smsmsg , List<RoomPrivilege> rplst)
	{
		if ( StringUtils.isBlank(smsmsg) || rplst == null || rplst.size() == 0 )
			return ;
		
		for ( RoomPrivilege rp : rplst )
		{
			SmsHistory sh = new SmsHistory();
			sh.setCountrycode(CommonConstant.DEFAULT_COUNTRYCODE);
			sh.setPhonenumber(rp.getGrantUserName());
			sh.setMessage(smsmsg);
			sh.setStatus(SmsStatus.newsms.getStatus());
			sh.setPersonId(r.getPersonId());
			sh.setDistrictId(r.getDistrictId());
			sh.setBuild(r.getBuild());
			sh.setUnit(r.getUnit());
			sh.setFloor(r.getFloor());
			sh.setRoomName(r.getRoomName());
			sh.setDistrictName(d.getDistrictName());
			
			smsHistoryService.saveSmsHistory(sh);
		}
	}
	
	private String createSms(District d , Room r , RoomFee rf , int actrualcharge)
	{
		Object[] pm = new Object[]{d.getDistrictName() , 
									r.getBuild(),
									r.getRoomName(),
									month,
									nulltoZero(rf.getElectricitycurrentdegrees()) - nulltoZero(rf.getElectricitypremonthdegree()),
									rf.getElectricityfee() * 1f/100,
									nulltoZero(rf.getWatercurrentdegrees()) - nulltoZero(rf.getWaterpremonthdegree()),
									rf.getWaterfee() * 1f/ 100,
									"" , "",
									rf.getPooledfee() * 1f/ 100,
									rf.getRentfee() * 1f/ 100,
									rf.getTotalamount() * 1f/ 100,
									actrualcharge * 1f/ 100,
									r.getBalance() * 1f/ 100,
									r.getArrearage() * 1f/ 100,
									MjConfig.get("selfrechargeurl")};
		
		String t = SMS_TEMPLATE ;
		if ( rf.getHotwaterfee() != 0 )
		{
			pm[8] = nulltoZero(rf.getWatercurrentdegrees()) - nulltoZero(rf.getWaterpremonthdegree());
			pm[9] = rf.getWaterfee() * 1f / 100 ;
			t = SMS_TEMPALTE_WITH_HOTWATER ;
		}
		
		String sms = String.format(t, pm);
		
		return sms ;
	}
	
	private float nulltoZero(Float f)
	{
		if ( f == null )
			return 0f ;
		return f ;
	}
	
	private int chargeroomfee(Room r )
	{
		int actrualcharge = r.getArrearage();
		
		Transaction t = transactionService.createTransaction(r.getId().intValue(), actrualcharge, TransactionType.charge, TransactionStatus.finished);
		t.setPrebalance(r.getBalance());
		
		r.setBalance( r.getBalance() - r.getArrearage());
		r.setArrearage(0);
		t.setPostbalance(r.getBalance());
		
		hiRoomService.update(r);
		return actrualcharge;

	}
	
	private int chargeroomfee(Room r , RoomFee rf)
	{
		if ( rf.getRentfee() == 0 )
		{
			rf.setRentfee(r.getRentfee());
			//hiRoomFeeService.updateRentFee(rf , rf.getRentfee());
		}	
		
		int actrualcharge = 0 ;
		if ( r.getBalance() >= rf.getTotalamount())
		{
			actrualcharge = rf.getTotalamount();
			
			Transaction t = transactionService.createTransaction(r.getId().intValue(), actrualcharge, TransactionType.charge, TransactionStatus.finished);
			t.setPrebalance(r.getBalance());
			r.setBalance(r.getBalance() - rf.getTotalamount());
			t.setPostbalance(r.getBalance());
		}
		else
			r.setArrearage( r.getArrearage() + rf.getTotalamount());
		rf.setStatus(RoomFeeStatus.hasbooked.ordinal());
		
		hiRoomService.update(r);
		return actrualcharge;

	}

	private List<Room> queryRooms(Long districtid)
	{
		List<Room> rlst = hiRoomService.findByDistrictId(districtid);

		for ( ListIterator<Room> it = rlst.listIterator() ; it.hasNext() ;)
		{
			Room r = it.next();
			if ( r.getGrantFlag() == null || r.getGrantFlag() == 0 )
				it.remove();
		}
		
		return rlst;
	}
	
	private Integer getPreMonth()
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Integer month = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(c.getTime()));
		return month;
	}

	public void setHiRoomFeeService(HiRoomFeeService hiRoomFeeService)
	{
		this.hiRoomFeeService = hiRoomFeeService;
	}

	public void setHiRoomService(HiRoomService hiRoomService)
	{
		this.hiRoomService = hiRoomService;
	}

	public void setHiDistrictService(HiDistrictService hiDistrictService)
	{
		this.hiDistrictService = hiDistrictService;
	}

	public void setHiRoomPrivilegeService(HiRoomPrivilegeService hiRoomPrivilegeService)
	{
		this.hiRoomPrivilegeService = hiRoomPrivilegeService;
	}

	public void setSmsHistoryService(SmsHistoryService smsHistoryService)
	{
		this.smsHistoryService = smsHistoryService;
	}

	public void setTransactionService(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}

	public void setDistrictPaymentSettingService(DistrictPaymentSettingService districtPaymentSettingService)
	{
		this.districtPaymentSettingService = districtPaymentSettingService;
	}




}
