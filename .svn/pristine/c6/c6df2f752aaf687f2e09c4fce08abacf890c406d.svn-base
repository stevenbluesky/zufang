package com.isurpass.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ant.business.Device;
import com.ant.business.DoorlockPasswordRuleDtl;
import com.ant.business.LockPassword;
import com.ant.util.AES;
import com.ant.util.ConstDefine;
import com.ant.util.StringUtil;
import com.ant.vo.LockPasswordVo;

public class AutoGenerationPasswordExecuteService {

	private HiLockPasswordService HiLockPasswordService;
	@Resource
	public void setHiLockPasswordService(HiLockPasswordService hiLockPasswordService) {
		HiLockPasswordService = hiLockPasswordService;
	}
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AutoGenerationPasswordExecuteService.class);
	
	@Transactional(propagation=Propagation.REQUIRES_NEW , isolation = Isolation.DEFAULT)
	public void generationPasswordByLock(Device lock,List<DoorlockPasswordRuleDtl> dtllist,List<LockPasswordVo> waitToSendPasswordList,List<LockPassword> waitToUpdateValidTimeList){
		Set<Integer> usercodeset = new TreeSet<Integer>();//可用usercode缓存
		List<LockPassword> usedpasswordlist = new ArrayList<LockPassword>();//过期密码记录缓存
		Set<Integer> cannotusecodelist = new TreeSet<Integer>();//不可用usercode
		Date date = new Date();
		
		//获取可用usecode集合(新写记录)
		for(int i=11;i<211;i++){
			usercodeset.add(i);
		}
		int[] status = new int[] { ConstDefine.LOCKPASSWORD_STATUS_NORMAL, ConstDefine.LOCKPASSWORD_STATUS_SENDING,
				ConstDefine.LOCKPASSWORD_STATUS_SENDINGTIME,ConstDefine.LOCKPASSWORD_STATUS_SENDFAILED,ConstDefine.LOCKPASSWORD_STATUS_SENDTIMEFAILED,
				ConstDefine.LOCKPASSWORD_STATUS_DELETEFAILED,ConstDefine.LOCKPASSWORD_STATUS_DELETING};
//		int[] status = new int[] {ConstDefine.LOCKPASSWORD_STATUS_DELETING};
		int[] usedstatus = new int[]{ConstDefine.LOCKPASSWORD_USESTATUS_UNUSE,ConstDefine.LOCKPASSWORD_USESTATUS_USED,ConstDefine.LOCKPASSWORD_USESTATUS_ABANDON};
		//查找不可用的usecode
		List<LockPassword> lockpasswordlist = HiLockPasswordService.findUsedPasswordByDvcid(lock.getId(),status,usedstatus);
		if(lockpasswordlist!=null){
			for(LockPassword pas : lockpasswordlist){
				if(usercodeset.contains(pas.getUsercode())){
					usercodeset.remove(pas.getUsercode());
					cannotusecodelist.add(pas.getUsercode());
				}
			}
		}
		
		//获取过期密码缓存
		List<LockPassword> overduelockpasswordlist = HiLockPasswordService.findOverduePasswordByDvcid(lock.getId(),
				new int[] { 0,1,2,3, 4 ,7}, new int[] { 0, 1 }, new int[] { 1, 2 });
		if(overduelockpasswordlist!=null){
			for(LockPassword l : overduelockpasswordlist){
				if(!cannotusecodelist.contains(l.getUsercode())){
					usedpasswordlist.add(l);
					usercodeset.remove(l.getUsercode());
				}
			}
		}
		for(DoorlockPasswordRuleDtl dtl : dtllist){//遍历密码规则
			int duration = dtl.getDuration();
			int durationhour = duration/60;
			int durationmin = duration%60;
			String starttime = dtl.getStarttime();
			int starthour = Integer.parseInt(starttime.split(":")[0]);
			int startmin = Integer.parseInt(starttime.split(":")[1]);
			List<Integer> weeklist = getWeeklist(dtl.getWeekday());
			//能下发今天至后3天的密码
			for(int i=0;i<3;i++){
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(new Date());
		        calendar.add(Calendar.DAY_OF_MONTH, i);
		        int weekday = calendar.get(Calendar.DAY_OF_WEEK);//周日:1 周一:2 周二:3 周三:4 周四:5 周五:6 周六为7
				if(!weeklist.contains(weekday)){
					continue;
				}
		        calendar.set(Calendar.HOUR_OF_DAY, 0);
		        calendar.set(Calendar.MINUTE, 0);
		        calendar.set(Calendar.SECOND, 0);
		        calendar.set(Calendar.MILLISECOND, 0);
				calendar.add(Calendar.HOUR_OF_DAY, starthour);
				calendar.add(Calendar.MINUTE, startmin);
				Date validfrom = calendar.getTime();
				calendar.add(Calendar.HOUR_OF_DAY, durationhour);
				calendar.add(Calendar.MINUTE, durationmin);
				Date validthrough = calendar.getTime();
				if(validthrough.getTime()>=date.getTime()&&!checkvalidtime(lock,validfrom,validthrough)){
					//有效期结束时间早于当前时间or该有效期内已经存在一条密码 该时间段已经被预订    直接下一条规则
					if(usedpasswordlist.size()>0){
						//过期密码缓存不为空
						LockPassword lockPassword = usedpasswordlist.get(0);
						usedpasswordlist.remove(lockPassword);
						if(lockPassword.getUsestatus()==1){
							lockPassword.setStatus(9);
							lockPassword.setSynstatus(1);
							lockPassword.setDeletetime(new Date());
							HiLockPasswordService.update(lockPassword);
							LockPasswordVo lockPasswordVo = new LockPasswordVo();
							lockPasswordVo.setUsercode(lockPassword.getUsercode());
							lockPasswordVo.setValidfrom(validfrom);
							lockPasswordVo.setValidthrough(validthrough);
//							createNewPassword(lockPasswordVo,lock);
							lockPasswordVo.setDvcid(lock.getId().intValue());
							List<String> passwordlist = HiLockPasswordService.findUserdPasswordByDvcid(lock.getId().intValue());
							String temp = createunrepeatpassword();
							while(passwordlist!=null&&passwordlist.contains(AES.encrypt2Str(temp))){
								temp = createunrepeatpassword();
							}
							lockPasswordVo.setPassword(temp);
							String tid = StringUtil.getUUID();
							lockPasswordVo.setThirdsendid(tid);
							lockPasswordVo.setZwavedeviceid(Integer.parseInt(lock.getPtDeviceId()));
							saveNewPassword(lockPasswordVo);
							waitToSendPasswordList.add(lockPasswordVo);
						}else if(lockPassword.getStatus()==3||lockPassword.getStatus()==1){
							List<String> passwordlist = HiLockPasswordService.findUserdPasswordByDvcid(lock.getId().intValue());
							String temp = createunrepeatpassword();
							while(passwordlist!=null&&passwordlist.contains(AES.encrypt2Str(temp))){
								temp = createunrepeatpassword();
							}
							lockPassword.setPassword(AES.encrypt2Str(temp));
							lockPassword.setStatus(1);
							lockPassword.setUsestatus(0);
							lockPassword.setSynstatus(0);
							lockPassword.setValidfrom(validfrom);
							lockPassword.setValidthrough(validthrough);
							lockPassword.setCreatetime(new Date());
							String tid = StringUtil.getUUID();
							lockPassword.setThirdsendid(tid);
							HiLockPasswordService.update(lockPassword);
							
							LockPasswordVo lockPasswordVo = new LockPasswordVo();
							lockPasswordVo.setUsercode(lockPassword.getUsercode());
							lockPasswordVo.setValidfrom(validfrom);
							lockPasswordVo.setValidthrough(validthrough);
							lockPasswordVo.setDvcid(lock.getId().intValue());
							lockPasswordVo.setPassword(temp);
							lockPasswordVo.setThirdsendid(tid);
							lockPasswordVo.setZwavedeviceid(Integer.parseInt(lock.getPtDeviceId()));
							waitToSendPasswordList.add(lockPasswordVo);
						}else{
							lockPassword.setStatus(2);
							lockPassword.setSynstatus(2);
							lockPassword.setValidfrom(validfrom);
							lockPassword.setValidthrough(validthrough);
							lockPassword.setCreatetime(new Date());
							HiLockPasswordService.update(lockPassword);
							//异步下发密码有效期
//							updateLockUserValidTimeAsynchronous(lockPassword);
							waitToUpdateValidTimeList.add(lockPassword);
						}
					}else{
						//过期密码缓存为空
						LockPasswordVo lockPasswordVo = new LockPasswordVo();
						//可用usercode缓存不为空
						if(usercodeset.size()>0){
							//取一个usercode生成新密码
							for(Iterator<Integer> it = usercodeset.iterator();it.hasNext();){
								lockPasswordVo.setUsercode(it.next());
								it.remove();
								break;
							}
							lockPasswordVo.setValidfrom(validfrom);
							lockPasswordVo.setValidthrough(validthrough);
//							createNewPassword(lockPasswordVo,lock);
							lockPasswordVo.setDvcid(lock.getId().intValue());
							List<String> passwordlist = HiLockPasswordService.findUserdPasswordByDvcid(lock.getId().intValue());
							lockPasswordVo.setDvcid(lock.getId().intValue());
							String temp = createunrepeatpassword();
							while(passwordlist!=null&&passwordlist.contains(AES.encrypt2Str(temp))){
								temp = createunrepeatpassword();
							}
							lockPasswordVo.setPassword(temp);
							String tid = StringUtil.getUUID();
							lockPasswordVo.setThirdsendid(tid);
							lockPasswordVo.setZwavedeviceid(Integer.parseInt(lock.getPtDeviceId()));
							saveNewPassword(lockPasswordVo);
							waitToSendPasswordList.add(lockPasswordVo);
						}
					}
				}
			}
		}
	}

	private void saveNewPassword(LockPasswordVo lockPasswordVo){
		LockPassword lp = new LockPassword();
		lp.setDvcid(lockPasswordVo.getDvcid());
		lp.setUsertype(21);
		lp.setUsercode(lockPasswordVo.getUsercode());
		lp.setUsername("Automatically created");
		lp.setPhonenumber("");
		lp.setValidfrom(lockPasswordVo.getValidfrom());
		lp.setValidthrough(lockPasswordVo.getValidthrough());
//		lp.setCreatetime(new Date());
		lp.setStatus(1);
		lp.setUsestatus(0);
		lp.setSynstatus(0);
		lp.setPasswordtype(1);
		lp.setPassword(AES.encrypt2Str(lockPasswordVo.getPassword()));
		lp.setThirdsendid(lockPasswordVo.getThirdsendid());
		HiLockPasswordService.save(lp);
	}

	private String createunrepeatpassword(){
		Random r = new Random(System.nanoTime());
		return String.format("%06d", r.nextInt(1000000));
	}
	
	private List<Integer> getWeeklist(int weekday) {
		List<Integer> weeklist = new ArrayList<Integer>();
		for(int i =1;i<8;i++){
			if(((weekday)%(Math.pow(2, i)))/(Math.pow(2, i-1)) >= 1.0){
				weeklist.add(8-i);
			}
		}
		return weeklist;
	}
	//该有效期内已经存在一条密码 该时间段已经被预订
	private boolean checkvalidtime(Device lock ,Date validfrom ,Date validthrough){//该有效期已经存在一条有效密码或已被预订
		List<LockPassword> passwordlist = HiLockPasswordService.findValidPasswordByDvcid(lock.getId(), new int[]{0,1}, new int[]{0,1});
		if(passwordlist==null)
			return false;
		for(LockPassword dp : passwordlist){
			if(dp.getValidfrom().getTime()==validfrom.getTime()&&dp.getValidthrough().getTime()==validthrough.getTime()){
				return true;
			}
/*			if(dp.getValidfrom().getTime()<=validfrom.getTime()&&dp.getValidthrough().getTime()>=validthrough.getTime()&&dp.getUsestatus()==1){
				return true;
			}*/
		}
		return false;
	}

}
