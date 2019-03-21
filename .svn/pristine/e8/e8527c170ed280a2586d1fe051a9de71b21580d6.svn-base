package com.isurpass.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Resource;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.scheduling.annotation.Async;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.business.Device;
import com.ant.business.District;
import com.ant.business.LockPassword;
import com.ant.business.Room;
import com.ant.config.MjConfig;
import com.ant.constant.CommonConstant;
import com.ant.restful.service.RestfulUtil;
import com.ant.util.AES;
import com.ant.util.ConstDefine;
import com.ant.util.StringUtil;
import com.ant.util.Utils;
import com.ant.vo.LockPasswordVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;
import com.isurpass.common.sms.AliSmsSender;
import com.isurpass.common.sms.MessageParser;

public class HiLockPasswordService extends BaseService<LockPassword> {
	private HiDeviceService HiDeviceService;
	
	@Resource
	public void setHiDeviceService(HiDeviceService hiDeviceService) {
		HiDeviceService = hiDeviceService;
	}
	private static Log log = LogFactory.getLog(HiLockPasswordService.class);
	
	public void save(LockPasswordVo lockpassword,PersonVo sessionPersonVo, ResourceBundle rb,long personid) {
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device device = hiDeviceService.findById(new Long(lockpassword.getDvcid()));	
		if(lockpassword.getPasswordFlag()==2){
			savePresetPassword(device,lockpassword,sessionPersonVo,rb,personid);
		}else{
			if ( lockpassword.getPasswordFlag() == 1 ){
				Random r = new Random(System.currentTimeMillis());
				lockpassword.setPassword(String.format("%06d", r.nextInt(1000000)));
			}
			String tid = StringUtil.getUUID();
			lockpassword.setThirdsendid(tid);
			if(isZwaveLock(device)){
				lockpassword.setStatus(0);
				lockpassword.setSynstatus(1);
			}else{
				lockpassword.setStatus(1);
			}
			sendpassword(lockpassword,device,rb);
			if(lockpassword.getHotaliduser()==null){
				sendSms(lockpassword,device,rb);
			}else{
				//给酒店客户发短信  您已成功预订${hotal}${room}房间，入住时间：${starttime}-${endtime}，请于${passwordtime}后到${hotal}刷身份证获取开门密码。
				log.info("您已成功预订房间，请到店刷身份证获取开门密码。");
				sendSms(lockpassword,device);
			}
			
			LockPassword lp = new LockPassword();
			try {
				PropertyUtils.copyProperties(lp, lockpassword);
			} catch (Throwable e) {
				log.error(e.getMessage() , e);
			}
			String p = AES.encrypt2Str(lp.getPassword());
			lp.setPassword(p);
			lp.setTypeinpersonid(personid);
			lp.setUsestatus(1);
			super.save(lp);
		}
	}

	private void savePresetPassword(Device device, LockPasswordVo lockpassword, PersonVo sessionPersonVo,ResourceBundle rb,long personid) {
		List<LockPassword> lockpasswordlist = findByDvcid(device.getId());
		long curtime = new Date().getTime();
		List<LockPassword> validfromeqthroughgtlist = new ArrayList<LockPassword>();
		List<LockPassword> validfromeqthroughltlist = new ArrayList<LockPassword>();
		List<LockPassword> validfromltthroughgtlist = new ArrayList<LockPassword>();
		List<LockPassword> validfromltthroughltlist = new ArrayList<LockPassword>();
		List<LockPassword> validfromfarlist = new ArrayList<LockPassword>();
		List<Long> tempvalidtimelist = new ArrayList<Long>();
		Date validfrom = lockpassword.getValidfrom();
		Date validthrough = lockpassword.getValidthrough();
		for(LockPassword l : lockpasswordlist){
			//有效期开始、结束时间都相同，未被使用，状态正常
			if (l.getValidfrom().getTime() == validfrom.getTime() && l.getValidthrough().getTime() == validthrough.getTime()
				&& l.getUsestatus() == 0 && l.getStatus() == 0) {
				//给顾客发送短信,更新下数据库用户信息，结束
				l.setUsername(lockpassword.getUsername());
				l.setPhonenumber(lockpassword.getPhonenumber());
				l.setUsestatus(1);
				l.setTypeinpersonid(personid);
				this.update(l);
				lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
				sendPresetPasswordSms(lockpassword,device,1);
				return ;
			//有效期开始时间与用户预订时间相同，有效期比结束时间晚
			}else if(l.getValidfrom().getTime()==validfrom.getTime() && l.getValidthrough().getTime()>validthrough.getTime()
					&& l.getUsestatus() == 0 && l.getStatus()==0){
				validfromeqthroughgtlist.add(l);
				tempvalidtimelist.add(l.getValidthrough().getTime());
			//有效期开始时间与用户预订时间相同，有效期比结束时间早
			}else if(l.getValidfrom().getTime()==validfrom.getTime() && l.getValidthrough().getTime()<validthrough.getTime()
					&& l.getUsestatus() == 0 && l.getStatus()==0){
				validfromeqthroughltlist.add(l);
				tempvalidtimelist.add(l.getValidthrough().getTime());
			//预订时间为当前时间或早于当前时间
			}else if(validfrom.getTime()<=curtime){
				if(l.getValidfrom().getTime()<curtime && l.getValidthrough().getTime()==validthrough.getTime()
						&& l.getUsestatus() == 0 && l.getStatus()== 0 ){
					l.setValidfrom(validfrom);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setTypeinpersonid(personid);
					this.update(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,1);
					return ;
				}else if(l.getValidfrom().getTime()<curtime && l.getValidthrough().getTime()>validthrough.getTime()
						&& l.getUsestatus() == 0 && l.getStatus()== 0 ){
					validfromltthroughgtlist.add(l);
					tempvalidtimelist.add(l.getValidthrough().getTime());
				}else if(l.getValidfrom().getTime()<curtime && l.getValidthrough().getTime()<validthrough.getTime()
						&& l.getValidthrough().getTime()>curtime && l.getUsestatus() == 0 && l.getStatus()== 0 ){
					validfromltthroughltlist.add(l);
					tempvalidtimelist.add(l.getValidthrough().getTime());
				}
			}
		}
		//挑选开始时间相同，有效期最短的，调用接口修改有效期，发送短信
		if(validfromeqthroughgtlist.size()>0){
			Collections.sort(tempvalidtimelist);
			for(LockPassword l : validfromeqthroughgtlist){
				if(l.getValidthrough().getTime()==tempvalidtimelist.get(0)){
					l.setValidthrough(validthrough);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setStatus(2);
					l.setSynstatus(2);
					l.setTypeinpersonid(personid);
					this.update(l);
					updateLockUserValidTimeAsynchronous(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,1);
					return;
				}
			}
		}
		//挑选开始时间相同，有效期最长的，调用接口修改有效期，发送短信
		if(validfromeqthroughltlist.size()>0){
			Collections.sort(tempvalidtimelist);
			Collections.reverse(tempvalidtimelist);
			for(LockPassword l : validfromeqthroughltlist){
				if(l.getValidthrough().getTime()==tempvalidtimelist.get(0)){
					l.setValidthrough(validthrough);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setStatus(2);
					l.setSynstatus(2);
					l.setTypeinpersonid(personid);
					this.update(l);
					updateLockUserValidTimeAsynchronous(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,1);
					return;
				}
			}
		}
		//挑选开始时间早于当前时间，结束时间晚于退房时间，结束时间最接近退房时间，调用接口修改有效期，发送短信
		if(validfromltthroughgtlist.size()>0){
			Collections.sort(tempvalidtimelist);
			for(LockPassword l : validfromltthroughgtlist){
				if(l.getValidthrough().getTime()==tempvalidtimelist.get(0)){
					l.setValidfrom(validfrom);
					l.setValidthrough(validthrough);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setStatus(2);
					l.setSynstatus(2);
					l.setTypeinpersonid(personid);
					this.update(l);
					updateLockUserValidTimeAsynchronous(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,1);
					return;
				}
			}
		}
		//挑选开始时间早于当前时间，结束时间早于退房时间，结束时间最接近退房时间，调用接口修改有效期，发送短信
		if(validfromltthroughltlist.size()>0){
			Collections.sort(tempvalidtimelist);
			Collections.reverse(tempvalidtimelist);
			for(LockPassword l : validfromltthroughltlist){
				if(l.getValidthrough().getTime()==tempvalidtimelist.get(0)){
					l.setValidfrom(validfrom);
					l.setValidthrough(validthrough);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setStatus(2);
					l.setSynstatus(2);
					l.setTypeinpersonid(personid);
					this.update(l);
					updateLockUserValidTimeAsynchronous(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,1);
					return;
				}
			}
		}
		//预订开始时间为当前时间或早于当前时间 || 预订时间在24小时以内
		if(validfrom.getTime()<=new Date().getTime()||(validfrom.getTime()-curtime)<24*60*60*1000){
			for(LockPassword l : lockpasswordlist){
				//存在过期且未使用的密码
				if(l.getValidthrough().getTime()<curtime
					&& l.getUsestatus()==0 && (l.getStatus()==0||l.getStatus()==4)){
					l.setValidfrom(validfrom);
					l.setValidthrough(validthrough);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setStatus(2);
					l.setSynstatus(2);
					l.setTypeinpersonid(personid);
					this.update(l);
					updateLockUserValidTimeAsynchronous(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,2);
					return;
				}
			}
			//在211到230之间存在可以使用的usercode
			Set<Integer> usercodeset = getAvailableUsercode(device);
			if(usercodeset.size()>0){
				lockpassword.setUsercode(usercodeset.iterator().next());
				List<String> passwordlist = this.findUserdPasswordByDvcid(device.getId().intValue());
				String temp = createunrepeatpassword();
				while(passwordlist!=null&&passwordlist.contains(AES.encrypt2Str(temp))){
					temp = createunrepeatpassword();
				}
				lockpassword.setPassword(temp);
				createNewPassword(lockpassword,device,1,personid);
				sendPresetPasswordSms(lockpassword,device,2);
				return;
			}
			for(LockPassword l : lockpasswordlist){
				if(l.getUsestatus()==0&&(l.getStatus()==0||l.getStatus()==4)){
					//存在未使用的密码
					validfromfarlist.add(l);
					tempvalidtimelist.add(l.getValidfrom().getTime());
				}
			}
			//利用未使用的密码
			if(validfromfarlist.size()>0){
				Collections.sort(tempvalidtimelist);
				Collections.reverse(tempvalidtimelist);
				for(LockPassword l : validfromfarlist){
					if(l.getValidfrom().getTime()==tempvalidtimelist.get(0)){
						l.setValidfrom(validfrom);
						l.setValidthrough(validthrough);
						l.setUsername(lockpassword.getUsername());
						l.setPhonenumber(lockpassword.getPhonenumber());
						l.setUsestatus(1);
						l.setStatus(2);
						l.setSynstatus(2);
						l.setTypeinpersonid(personid);
						this.update(l);
						updateLockUserValidTimeAsynchronous(l);
						lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
						sendPresetPasswordSms(lockpassword,device,2);
						return;
					}
				}
			}
			throw new BusinessException(rb.getString("passwordfull"));
		}
		//预订开始时间在24小时以上
		if((validfrom.getTime()-curtime)>=24*60*60*1000){
			for(LockPassword l : lockpasswordlist){
				//存在过期且未使用的密码
				if(l.getValidthrough().getTime()<curtime
					&& l.getUsestatus()==0 && (l.getStatus()==0||l.getStatus()==4)){
					l.setValidfrom(validfrom);
					l.setValidthrough(validthrough);
					l.setUsername(lockpassword.getUsername());
					l.setPhonenumber(lockpassword.getPhonenumber());
					l.setUsestatus(1);
					l.setStatus(2);
					l.setSynstatus(2);
					l.setTypeinpersonid(personid);
					this.update(l);
					updateLockUserValidTimeAsynchronous(l);
					lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
					sendPresetPasswordSms(lockpassword,device,1);
					return;
				}
			}
			//在211到230之间存在可以使用的usercode
			Set<Integer> usercodeset = getAvailableUsercode(device);
			if(usercodeset.size()>0){
				lockpassword.setUsercode(usercodeset.iterator().next());
				List<String> passwordlist = this.findUserdPasswordByDvcid(device.getId().intValue());
				String temp = createunrepeatpassword();
				while(passwordlist!=null&&passwordlist.contains(AES.encrypt2Str(temp))){
					temp = createunrepeatpassword();
				}
				lockpassword.setPassword(temp);
				createNewPassword(lockpassword,device,1,personid);
				sendPresetPasswordSms(lockpassword,device,1);
				return;
			}
			for(LockPassword l : lockpasswordlist){
				if(l.getUsestatus()==0&&(l.getStatus()==0||l.getStatus()==4)){
					//存在未使用的密码
					validfromfarlist.add(l);
					tempvalidtimelist.add(l.getValidfrom().getTime());
				}
			}
			//利用未使用的密码
			if(validfromfarlist.size()>0){
				Collections.sort(tempvalidtimelist);
				Collections.reverse(tempvalidtimelist);
				for(LockPassword l : validfromfarlist){
					if(l.getValidfrom().getTime()==tempvalidtimelist.get(0)){
						l.setValidfrom(validfrom);
						l.setValidthrough(validthrough);
						l.setUsername(lockpassword.getUsername());
						l.setPhonenumber(lockpassword.getPhonenumber());
						l.setUsestatus(1);
						l.setStatus(2);
						l.setSynstatus(2);
						l.setTypeinpersonid(personid);
						this.update(l);
						updateLockUserValidTimeAsynchronous(l);
						lockpassword.setPassword(AES.decrypt2Str(l.getPassword()));
						sendPresetPasswordSms(lockpassword,device,1);
						return;
					}
				}
			}
			//提示密码满，预订失败
			throw new BusinessException(rb.getString("passwordfull"));
		}
	}
	
	private void createNewPassword(LockPasswordVo lockPasswordVo, Device lock,int usestatus,long personid) {
		
		lockPasswordVo.setDvcid(lock.getId().intValue());
		String tid = StringUtil.getUUID();
		lockPasswordVo.setThirdsendid(tid);
		sendpassword(lockPasswordVo,lock);
		
		LockPassword lp = new LockPassword();
		lp.setDvcid(lockPasswordVo.getDvcid());
		lp.setUsertype(21);
		lp.setUsercode(lockPasswordVo.getUsercode());
		lp.setUsername(lockPasswordVo.getUsername());
		lp.setPhonenumber(lockPasswordVo.getPhonenumber());
		lp.setValidfrom(lockPasswordVo.getValidfrom());
		lp.setValidthrough(lockPasswordVo.getValidthrough());
		lp.setCreatetime(new Date());
		lp.setStatus(1);
		lp.setUsestatus(usestatus);
		lp.setSynstatus(0);
		lp.setPasswordtype(0);
		lp.setTypeinpersonid(personid);
		lp.setPassword(AES.encrypt2Str(lockPasswordVo.getPassword()));
		lp.setThirdsendid(lockPasswordVo.getThirdsendid());
		
		this.save(lp);
	}
	
	private String createunrepeatpassword(){
		Random ra = new Random();
		Random r = new Random(System.currentTimeMillis()+ra.nextInt(100));
		return String.format("%06d", r.nextInt(1000000));
	}
	
	private Set<Integer> getAvailableUsercode(Device lock){
		Set<Integer> usercodeset = new TreeSet<Integer>();
		//获取可用usecode集合
		for(int i=211;i<230;i++){
			usercodeset.add(i);
		}
		int[] status = new int[] { ConstDefine.LOCKPASSWORD_STATUS_NORMAL, ConstDefine.LOCKPASSWORD_STATUS_SENDING,
				ConstDefine.LOCKPASSWORD_STATUS_SENDINGTIME,ConstDefine.LOCKPASSWORD_STATUS_SENDTIMEFAILED,
				ConstDefine.LOCKPASSWORD_STATUS_DELETEFAILED,ConstDefine.LOCKPASSWORD_STATUS_DELETING};
		int[] usedstatus = new int[]{0,1};
		List<LockPassword> lockpasswordlist = this.findUsedPasswordByDvcid(lock.getId(),status,usedstatus);
		List<LockPassword> normallockpasswordlist = this.findNormalUserdPasswordByDvcid(lock.getId());
		if(lockpasswordlist!=null){
			for(LockPassword pas : lockpasswordlist){
				if(usercodeset.contains(pas.getUsercode())){
					usercodeset.remove(pas.getUsercode());
				}
			}
		}
		if(normallockpasswordlist!=null){
			for(LockPassword pas : normallockpasswordlist){
				if(usercodeset.contains(pas.getUsercode())){
					usercodeset.remove(pas.getUsercode());
				}
			}
		}
		return usercodeset;
	}

	private List<LockPassword> findNormalUserdPasswordByDvcid(Long id) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", id.intValue()));
		cw.add(ExpWrap.eq("passwordtype", 0));
		cw.add(ExpWrap.lt("status", ConstDefine.LOCKPASSWORD_STATUS_DELEET));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	public void saveLockCardUser(LockPasswordVo lockpassword,PersonVo sessionPersonVo, ResourceBundle rb){
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device device = hiDeviceService.findById(new Long(lockpassword.getDvcid()));
		String tid = StringUtil.getUUID();
		lockpassword.setThirdsendid(tid);
		if(isZwaveLock(device)){
			lockpassword.setStatus(0);
			lockpassword.setSynstatus(1);
		}else{
			lockpassword.setStatus(1);
		}
		sendcardinfo(lockpassword,device,rb);
		LockPassword lp = new LockPassword();
		try {
			PropertyUtils.copyProperties(lp, lockpassword);
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		} 
		lp.setTypeinpersonid(sessionPersonVo.getId());
		lp.setUsestatus(1);
		lp.setPassword("");
		super.save(lp);
	}
	
	public void saveFingerprintUser(LockPasswordVo lockpassword,PersonVo sessionPersonVo, ResourceBundle rb){
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device device = hiDeviceService.findById(new Long(lockpassword.getDvcid()));
		String tid = StringUtil.getUUID();
		lockpassword.setThirdsendid(tid);
		if(isZwaveLock(device)){
			lockpassword.setStatus(0);
			lockpassword.setSynstatus(1);
		}else{
			lockpassword.setStatus(1);
		}
		sendfingerprint(lockpassword,device,rb);
		//sendSms(lockpassword,device,rb);无短信模板 
		
		LockPassword lp = new LockPassword();
		try {
			PropertyUtils.copyProperties(lp, lockpassword);
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		} 
		lp.setTypeinpersonid(sessionPersonVo.getId());
		lp.setUsestatus(1);
		lp.setPassword("");
		super.save(lp);
	}
	
	public void delete(long lockpasswordid, ResourceBundle rb) {
		LockPassword lockpassword = super.query(lockpasswordid);
		if ( lockpassword == null )
			return ;
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device device = hiDeviceService.findById(new Long(lockpassword.getDvcid()));
		sendDeletepassword(lockpassword , device,rb,1);
		if(lockpassword.getPasswordtype()==0&&isZwaveLock(device)){
			lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_DELEET);
			lockpassword.setSynstatus(1);
		}else{
			lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_DELETING);
			lockpassword.setSynstatus(0);
		}

		lockpassword.setDeletetime(new Date());
		
		super.update(lockpassword);
	}
	
	public void deleteAsyn(long lockpasswordid, ResourceBundle rb) {
		LockPassword lockpassword = super.query(lockpasswordid);
		if ( lockpassword == null )
			return ;
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device device = hiDeviceService.findById(new Long(lockpassword.getDvcid()));
		sendDeletepassword(lockpassword , device,rb,0);
		lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_DELETING);
		lockpassword.setSynstatus(0);
		lockpassword.setDeletetime(new Date());
		super.update(lockpassword);
	}
	
	public void abandon(long lockpasswordid, ResourceBundle rb) {
		LockPassword lockpassword = super.query(lockpasswordid);
		if ( lockpassword == null )
			return ;
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device device = hiDeviceService.findById(new Long(lockpassword.getDvcid()));
		sendDeletepassword(lockpassword , device,rb,0);

		lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_DELETING);
		lockpassword.setSynstatus(0);
		lockpassword.setUsestatus(9);
		super.update(lockpassword);
	}
	
	private void sendpassword(LockPasswordVo lockpassword,Device device, ResourceBundle rb){
		if(CommonConstant.restFlag == 1){
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("zwavedeviceid", device.getPtDeviceId());
			map.put("password", lockpassword.getPassword());
			map.put("usercode", lockpassword.getUsercode());
			map.put("username", lockpassword.getUsername());
			if(isZwaveLock(device)){
				map.put("asynch", 1);
			}else{
				map.put("asynch", 0);
			}
			map.put("tid", lockpassword.getThirdsendid());
			if(lockpassword.getWeekday()!=0){//星期勾选了才传送这三个参数
				map.put("weekday", lockpassword.getWeekday());
				map.put("starttime",lockpassword.getStarttime());
				map.put("endtime",lockpassword.getEndtime());
			}
			if(lockpassword.getValidfrom() == null){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				lockpassword.setValidfrom(c.getTime());
			}
			if(lockpassword.getValidthrough() == null){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				lockpassword.setValidthrough(c.getTime());
			}
			map.put("validfrom", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidfrom()));
			map.put("validthrough", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidthrough()));

			String str = "" ;
			if ( lockpassword.getUsercode() != ConstDefine.LOCKPASSWORD_USERCODE_TEMP )
				str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/setlockuserpassword", map,rb);//下发用户密码
			else {
				List<LockPassword> temppl = findTempPasswordByDvicdAndUserCodeAndStatus(device.getId().intValue());
				if(temppl!=null&&temppl.size()>0){
					throw new BusinessException(CommonConstant.getErrorMsg(30512,rb));
				}
				str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/setlockpassword", map,rb);//下发临时密码
			}
			Utils.checkResult(str,rb);
			
			try {
				JSONObject json = JSON.parseObject(str);
				if ( json.containsKey("usercode"))
					lockpassword.setUsercode(json.getIntValue("usercode"));
				if(json.getIntValue("resultCode")==30580)
					lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_SENDTIMEFAILED);
			} catch (Exception e) {
				throw new BusinessException(rb.getString("seecib"));
			}
			 
		}
	}
	
	private void sendpassword(LockPasswordVo lockpassword,Device device){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("zwavedeviceid", device.getPtDeviceId());
		map.put("password", lockpassword.getPassword());
		map.put("usercode", lockpassword.getUsercode());
		map.put("asynch", 0);
		map.put("tid", lockpassword.getThirdsendid());
		if(lockpassword.getValidfrom() == null){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			lockpassword.setValidfrom(c.getTime());
		}
		if(lockpassword.getValidthrough() == null){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			lockpassword.setValidthrough(c.getTime());
		}
		map.put("validfrom", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidfrom()));
		map.put("validthrough", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidthrough()));
		
		String str = "" ;
		if ( lockpassword.getUsercode() != ConstDefine.LOCKPASSWORD_USERCODE_TEMP )
			str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/setlockuserpassword", map);//下发用户密码
		Utils.checkResult(str);
		try {
			JSONObject json = JSON.parseObject(str);
			if ( json.containsKey("usercode"))
				lockpassword.setUsercode(json.getIntValue("usercode"));
			if(json.getIntValue("resultCode")==30580)
				lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_SENDTIMEFAILED);
		} catch (Exception e) {
			throw new BusinessException("系统错误");
		}
		 
	}
	
	private void updateLockUserValidTimeAsynchronous(LockPassword lockPassword) {
		Device device = HiDeviceService.findById((long)lockPassword.getDvcid());
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("zwavedeviceid", device.getPtDeviceId());
		map.put("tid", lockPassword.getThirdsendid());
		map.put("validfrom", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockPassword.getValidfrom()));
		map.put("validthrough", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockPassword.getValidthrough()));
		
		String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/updatelockuservalidtimeasynchronous", map);
		Utils.checkResult(str);
	}
	
	private void sendcardinfo(LockPasswordVo lockpassword,Device device, ResourceBundle rb){
		if(CommonConstant.restFlag == 1){
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("zwavedeviceid", device.getPtDeviceId());
			map.put("cardname", lockpassword.getUsername());
			map.put("cardinfo", lockpassword.getCardinfo());
			map.put("cardtype", lockpassword.getCardtype());
			if(isZwaveLock(device)){
				map.put("asynch", 1);
			}else{
				map.put("asynch", 0);
			}
			map.put("tid", lockpassword.getThirdsendid());
			if(lockpassword.getValidfrom() == null){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				lockpassword.setValidfrom(c.getTime());
			}
			if(lockpassword.getValidthrough() == null){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				lockpassword.setValidthrough(c.getTime());
			}
			if(lockpassword.getWeekday()!=0){//星期勾选了才传送这三个参数
				map.put("weekday", lockpassword.getWeekday());
				map.put("starttime",lockpassword.getStarttime());
				map.put("endtime",lockpassword.getEndtime());
			}
			map.put("validfrom", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidfrom()));
			map.put("validthrough", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidthrough()));

			String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/addDoorlockCard", map,rb);
			Utils.checkResult(str,rb);
			
			JSONObject json = JSON.parseObject(str);
			if ( json.containsKey("usercode"))
				lockpassword.setUsercode(json.getIntValue("usercode"));
			if(json.getIntValue("resultCode")==30580)
				lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_SENDTIMEFAILED);
		}
	}
	
	private void sendfingerprint(LockPasswordVo lockpassword,Device device, ResourceBundle rb){
		if(CommonConstant.restFlag == 1){
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("zwavedeviceid", device.getPtDeviceId());
			map.put("fingerprint", lockpassword.getFingerprint());
			map.put("username", lockpassword.getUsername());
			if(isZwaveLock(device)){
				map.put("asynch", 1);
			}else{
				map.put("asynch", 0);
			}
			map.put("tid", lockpassword.getThirdsendid());
			if(lockpassword.getValidfrom() == null){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				lockpassword.setValidfrom(c.getTime());
			}
			if(lockpassword.getValidthrough() == null){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				lockpassword.setValidthrough(c.getTime());
			}
			if(lockpassword.getWeekday()!=0){//星期勾选了才传送这三个参数
				map.put("weekday", lockpassword.getWeekday());
				map.put("starttime",lockpassword.getStarttime());
				map.put("endtime",lockpassword.getEndtime());
			}
			map.put("validfrom", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidfrom()));
			map.put("validthrough", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockpassword.getValidthrough()));
			
			String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/setlockfingerprintuserinfo", map,rb);
			Utils.checkResult(str,rb);
			
			JSONObject json = JSON.parseObject(str);
			if ( json.containsKey("usercode"))
				lockpassword.setUsercode(json.getIntValue("usercode"));
			if(json.getIntValue("resultCode")==30580)
				lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_SENDTIMEFAILED);
		}
	}
	
	private void sendDeletepassword(LockPassword lockpassword,Device device, ResourceBundle rb,int syntype){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("zwavedeviceid", device.getPtDeviceId());
		map.put("usercode", lockpassword.getUsercode());
		if(syntype==1&&isZwaveLock(device)&&lockpassword.getPasswordtype()==0){
			map.put("asynch", 1);
		}else{
			map.put("asynch", 0);
		}
		map.put("tid", lockpassword.getThirdsendid());
		String str ;
		
		if ( lockpassword.getUsertype() == 32 ){
			str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/deleteDoorlockCard", map,rb);
		}else if ( lockpassword.getUsertype() == 22 ){
			str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/deletelockfingerprintuser", map,rb);
		}else if ( lockpassword.getUsercode() != ConstDefine.LOCKPASSWORD_USERCODE_TEMP ){
			str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/deletelockuserpassword", map,rb);
		}else{ 
			str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/deletelockpassword", map,rb);
		}
		//Utils.checkResult(str,rb);
		JSONObject resultMap = null;
		try {
			resultMap = JSON.parseObject(str);
		} catch (Exception e) {
			throw new BusinessException(rb.getString("scf"));
		}
		if(StringUtil.checkNull(resultMap.getString("resultCode"))){
			throw new BusinessException(rb.getString("seecib"));
		}
		int resultCode = resultMap.getIntValue("resultCode");
		if(resultCode==10005){
			throw new BusinessException(rb.getString("deleteAdminPasswordFailed"));
		}
		if(resultCode != 0 &&  resultCode != 30580){
			throw new BusinessException(CommonConstant.getErrorMsg(resultCode,rb));
		}
	} 
	@Async
	public void sendSms(LockPasswordVo lockpassword,Device dbDevice, ResourceBundle rb){
		JSONObject json = new JSONObject();
		
		String templatecode = "SMS_36100250";
		if(dbDevice.getBindRoomId() != null){
			HiRoomService hiRoomService = super.createService(HiRoomService.class);
			Room dbRoom = hiRoomService.findById(dbDevice.getBindRoomId());
			HiDistrictService hiDistrictService = super.createService(HiDistrictService.class);
			District dbDistrict = hiDistrictService.findById(dbRoom.getDistrictId());
//			text = "您的【" + dbDistrict.getDistrictName() + "】小区" + dbRoom.getBuild() + "栋" + dbRoom.getUnit() + 
//			"单元" + dbRoom.getFloor() + "楼" + dbRoom.getRoomName() + "房间" + dbDevice.getDeviceName() + "锁";
			 
			json.put("districtName", dbDistrict.getDistrictName());
			json.put("build", dbRoom.getBuild());
			json.put("unit", dbRoom.getUnit());
			json.put("floor", dbRoom.getFloor());
			json.put("roomName", dbRoom.getRoomName());
		}else{
			//text = "您的" + dbDevice.getDeviceName() + "锁";
			templatecode = "SMS_36110165";
		}
		
		json.put("deviceName", dbDevice.getDeviceName());
		json.put("password", lockpassword.getPassword());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		json.put("validday", getWeeklist(lockpassword.getWeekday()));
		if(ConstDefine.LOCKPASSWORD_USERCODE_TEMP != lockpassword.getUsercode()){
			json.put("passwordtype", rb.getString("yonghu"));
			//text = text + "被设置用户密码为：" + lockpassword.getPassword() + "";
		}else{
			json.put("passwordtype", rb.getString("linshi"));
			//text = text + "被设置临时密码为：" + lockpassword.getPassword() + "";
		}
		if("SMS_36100250".equals(templatecode)){
			Map<String , Object> pmap = new HashMap<String , Object>();
			String text = "您的"+json.getString("districtName")+"小区"+json.getString("build")+"栋"+json.getString("unit")+"单元"
					+json.getString("floor")+"楼"+json.getString("roomName")+"房间"+json.getString("deviceName")+"锁被设置"+json.getString("passwordtype")
					+"密码为："+json.getString("password")+"，有效时间为：从"+sdf.format(lockpassword.getValidfrom())+
					"到"+sdf.format(lockpassword.getValidthrough())+"，周"+json.getString("validday")+"有效";
			pmap.put("countrycode", 86);
			pmap.put("message", text);
			pmap.put("phonenumber", lockpassword.getPhonenumber());
			RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/sendsms", pmap);
		}
		//"您的【${districtName}】小区${build}栋${unit}单元${floor}楼${roomName}房间${deviceName}锁被设置${passwordtype}密码为：${password}"，
		//有效时间为：从${starttime}到${endtime}，周${validday}有效
		else{
			MessageParser mp = new MessageParser(null ,templatecode , json);
			//【管家提醒】"${districtName}小区${build}栋${unit}单元${floor}楼${roomName}房间${deviceName}锁已添加您的指纹，您可在${validfrom}至${validthrough}内开门"
			AliSmsSender sender = new AliSmsSender();
			if(lockpassword.getCountrycode()!=null&&!"".equals(lockpassword.getCountrycode())&&!"86".equals(lockpassword.getCountrycode())){
				sender.sendSMS(lockpassword.getCountrycode(), lockpassword.getPhonenumber(), mp);
			}else{
				sender.sendSMS("86", lockpassword.getPhonenumber(), mp);
			}
		}
	}
	private String getWeeklist(int weekday) {
		String validday = "";
		if(((weekday)%(Math.pow(2, 6)))/(Math.pow(2, 5)) >= 1.0){
			validday +="一、";
		}
		if(((weekday)%(Math.pow(2, 5)))/(Math.pow(2, 4)) >= 1.0){
			validday +="二、";
		}
		if(((weekday)%(Math.pow(2, 4)))/(Math.pow(2, 3)) >= 1.0){
			validday +="三、";
		}
		if(((weekday)%(Math.pow(2, 3)))/(Math.pow(2, 2)) >= 1.0){
			validday +="四、";
		}
		if(((weekday)%(Math.pow(2, 2)))/(Math.pow(2, 1)) >= 1.0){
			validday +="五、";
		}
		if(((weekday)%(Math.pow(2, 1)))/(Math.pow(2, 0)) >= 1.0){
			validday +="六、";
		}
		if(((weekday)%(Math.pow(2, 7)))/(Math.pow(2, 6)) >= 1.0){
			validday +="日、";
		}
		if(weekday==0){
			validday = "一、二、三、四、五、六、日、";
		}
		return validday.substring(0,validday.lastIndexOf("、"));
	}

	@Async
	public void sendSms(LockPasswordVo lockpassword,Device dbDevice){
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
		String templatecode = "SMS_142946625";
		if(dbDevice.getBindRoomId() == null){
			return ;
		}
		HiRoomService hiRoomService = super.createService(HiRoomService.class);
		Room dbRoom = hiRoomService.findById(dbDevice.getBindRoomId());
		HiDistrictService hiDistrictService = super.createService(HiDistrictService.class);
		District dbDistrict = hiDistrictService.findById(dbRoom.getDistrictId());
		 
		json.put("hotal", dbDistrict.getDistrictName());
		json.put("room", dbRoom.getRoomName());
		json.put("starttime",sdf.format(lockpassword.getValidfrom()));
		json.put("endtime", sdf.format(lockpassword.getValidthrough()));
		json.put("passwordtime", sdf.format(new Date(lockpassword.getValidfrom().getTime() - (long) 2 * 60 * 60 * 1000)));

		//您已成功预订${hotal}酒店${room}房间，入住时间：从${starttime}到${endtime}，请于${passwordtime}后到店刷身份证获取开门密码。
		MessageParser mp = new MessageParser(null ,templatecode , json);
		AliSmsSender sender = new AliSmsSender();

		sender.sendSMS("86", lockpassword.getPhonenumber(), mp);
	}
	
	@Async
	public void sendPresetPasswordSms(LockPasswordVo lockpassword,Device dbDevice,int type){
		if(dbDevice.getBindRoomId() == null){
			return ;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HiRoomService hiRoomService = super.createService(HiRoomService.class);
		Room dbRoom = hiRoomService.findById(dbDevice.getBindRoomId());
		HiDistrictService hiDistrictService = super.createService(HiDistrictService.class);
		District dbDistrict = hiDistrictService.findById(dbRoom.getDistrictId());

		String text = "您已成功预订"+dbDistrict.getDistrictName()+"，"+dbRoom.getRoomName()+"房间，入住时间为：从"+sdf.format(lockpassword.getValidfrom())+
				"到"+sdf.format(lockpassword.getValidthrough())+"，开门密码为"+lockpassword.getPassword();
		if(type==2){
			text += "。若密码不能开锁，请唤醒门锁并等待一分钟后再试";
		}
		//您已成功预订${hotal}，${room}房间，入住时间为：从${starttime}到${endtime}，开门密码为${password}。
		Map<String , Object> pmap = new HashMap<String , Object>();
		pmap.put("countrycode", 86);
		pmap.put("message", text);
		pmap.put("phonenumber", lockpassword.getPhonenumber());
		String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/sendsms", pmap);
		Map resultMap = JSON.parseObject(str,HashMap.class);
		if(StringUtil.checkNull(resultMap.get("resultCode"))){
			throw new BusinessException("发送短信错误，错误代码为空。");
		}
		int resultCode = Integer.parseInt(resultMap.get("resultCode").toString());
		if(resultCode != 0){
			throw new BusinessException("发送短信错误，错误代码：" + resultCode);
		}
	}
	
	private boolean isZwaveLock(Device device){
		if(device!=null&&StringUtils.isNotBlank(device.getProductor())&&device.getProductor().length()>4){
			return true;
		}
		return false;
	}
	
	public void deleteAllPasswordUnderLock(long deviceid, ResourceBundle rb) {
		List<LockPassword> lockpasswordlist = this.findNeedDeleteByDvcid(deviceid);
		if(lockpasswordlist!=null){
			for(LockPassword l : lockpasswordlist){
				this.deleteAsyn(l.getLockpasswordid(), rb);
			}
		}
	}
	
	@Deprecated
	public void hidden(long lockpasswordid, ResourceBundle rb) {
		LockPassword lockpassword = super.query(lockpasswordid);
		if ( lockpassword == null )
			return ;
		lockpassword.setStatus(ConstDefine.LOCKPASSWORD_STATUS_DELEET);
		lockpassword.setDeletetime(new Date());
		super.update(lockpassword);
	}
	
	public List<LockPassword> findPage(int lockid, BasicScroll scroll) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", lockid));
		cw.add(ExpWrap.lt("status", ConstDefine.LOCKPASSWORD_STATUS_DELEET));
		cw.add(ExpWrap.or(ExpWrap.eq("passwordtype", 0), ExpWrap.and(ExpWrap.eq("usestatus", 1),
				ExpWrap.and(ExpWrap.eq("passwordtype", 1), ExpWrap.gt("validthrough", new Date())))));
		cw.setScroll(scroll);
		return cw.list();
	}
	
	private List<LockPassword> findByDvcid(Long id) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", id.intValue()));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	private List<LockPassword> findNeedDeleteByDvcid(Long id) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", id.intValue()));
		cw.add(ExpWrap.eq("passwordtype", 1));
		cw.add(ExpWrap.in("status", new int[]{0,1,2,4}));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<LockPassword> findPasswordPage(int lockid, BasicScroll scroll){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", lockid));
		cw.add(ExpWrap.lt("status", ConstDefine.LOCKPASSWORD_STATUS_DELEET));
//		cw.add(ExpWrap.in("usestatus", new int[]{0,1}));
		cw.add(ExpWrap.eq("passwordtype", 1));
		cw.add(ExpWrap.eq("usertype", 21));
		cw.add(ExpWrap.gt("validthrough", new Date()));
		cw.addOrder(Order.asc("validfrom"));
		cw.setScroll(scroll);
		return cw.list();
	}
	
	public List<LockPassword> findPasswordPage(int lockid){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", lockid));
		cw.add(ExpWrap.lt("status", ConstDefine.LOCKPASSWORD_STATUS_DELEET));
//		cw.add(ExpWrap.in("usestatus", new int[]{0,1}));
		cw.add(ExpWrap.eq("passwordtype", 1));
		cw.add(ExpWrap.eq("usertype", 21));
		cw.add(ExpWrap.gt("validthrough", new Date()));
		cw.addOrder(Order.asc("validfrom"));
		return cw.list();
	}
	
	public LockPassword findByUsertypeUserCode(int dvcid , int usertype , int usercode){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", dvcid));
		cw.add(ExpWrap.eq("usertype", usertype));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.eq("status", 0));
		cw.add(ExpWrap.ge("validthrough", new Date()));
		return cw.uniqueResult();
	}
	
	public List<LockPassword> findTempPasswordByDvicdAndUserCodeAndStatus(int dvcid){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", dvcid));
		cw.add(ExpWrap.eq("usertype", 21));
		cw.add(ExpWrap.eq("usercode", 242));
		cw.add(ExpWrap.in("status", new int[]{0,1,2}));
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}
	
	public List<LockPassword> findUsedPasswordByDvcid(Long id,int[] status,int[] usestatus) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", id.intValue()));
		cw.add(ExpWrap.in("status", status));
		cw.add(ExpWrap.in("usestatus", usestatus));
		cw.add(ExpWrap.or(ExpWrap.gt("validthrough", new Date()),ExpWrap.eq("passwordtype", 0)));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<LockPassword> findOverduePasswordByDvcid(Long id,int[] status,int[] usestatus,int[] synstatus) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", id.intValue()));
		cw.add(ExpWrap.in("status", status));
		cw.add(ExpWrap.in("usestatus", usestatus));
		cw.add(ExpWrap.in("synstatus", synstatus));
		cw.add(ExpWrap.eq("passwordtype", 1));
		cw.add(ExpWrap.lt("validthrough", new Date()));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<LockPassword> findValidPasswordByDvcid(Long id,int[] status,int[] usestatus) {//该有效期内存在一条有效密码
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", id.intValue()));
		cw.add(ExpWrap.in("status", status));
		cw.add(ExpWrap.in("usestatus", usestatus));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	public LockPassword findByTidAndZwavedeviceid(String tid,int zwavedeviceid){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", zwavedeviceid));
		cw.add(ExpWrap.eq("thirdsendid", tid));
		return cw.uniqueResult();
	}
	
	public List<LockPassword> findByZwavedeviceidAndUsercodeThroughAPP(int zwavedeviceid,int usercode){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.in("status", new int[]{ConstDefine.LOCKPASSWORD_STATUS_NORMAL,ConstDefine.LOCKPASSWORD_STATUS_NORMAL}));
		cw.addOrder(Order.desc("lockpasswordid"));
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}
	
	public void deleteByZwavedeviceid(int zwavedeviceid){
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", zwavedeviceid));
		List<LockPassword> list = cw.list();
		if(list!=null&&list.size()>0){
			for(LockPassword l : list){
				l.setStatus(ConstDefine.LOCKPASSWORD_STATUS_DELEET);
				l.setDeletetime(new Date());
			}
		}
	}

	public List<String> findUserdPasswordByDvcid(int dvcid) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("dvcid", dvcid));
		List<LockPassword> llist= cw.list();
		List<String> plist = new ArrayList<String>();
		if(llist!=null&&llist.size()>0){
			for(LockPassword l : llist){
				plist.add(l.getPassword());
			}
			return plist;
		}
		return null;
	}

	public LockPassword findByLockPasswordId(Long lockpasswordid) {
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("lockpasswordid", lockpasswordid));
		return cw.uniqueResult();
	}
}
