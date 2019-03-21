package com.isurpass.message.processor;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.business.Device;
import com.ant.business.LockPassword;
import com.ant.vo.LockUserResultVo;
import com.isurpass.service.HiDeviceService;
import com.isurpass.service.HiLockPasswordService;

public class AddLockuserResultProcessor implements IMessageProcessor {
	private static Log log = LogFactory.getLog(AddLockuserResultProcessor.class);

	@Resource
	private HiLockPasswordService hilockpasswordService;
	@Resource
	private HiDeviceService hideviceservice;

	@Override
	public void process(JSONObject message) {
		LockUserResultVo lockuserresult = JSON.parseObject(message.getString("objparam"),LockUserResultVo.class);
		
		String zwavedeviceid = message.getString("zwavedeviceid");
		int usercode = message.getIntValue("intparam");
		String username = lockuserresult.getUsername();
		int usertype = lockuserresult.getUsertype();
		Date validfrom = lockuserresult.getValidfrom();
		Date validthrough = lockuserresult.getValidthrough();
		int resultCode = lockuserresult.getResultCode();
		String tid = lockuserresult.getTid();
		Device device = hideviceservice.findByPtDeviceId(zwavedeviceid);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, 1);
		if(usertype!=0&&StringUtils.isBlank(tid)&&resultCode==1){
			LockPassword lockPassword = new LockPassword();
			lockPassword.setDvcid(device.getId().intValue());
			lockPassword.setPhonenumber("");
			lockPassword.setUsertype(usertype);
			lockPassword.setUsercode(usercode);
			lockPassword.setUsername(username);
			lockPassword.setValidfrom(validfrom==null?date:validfrom);
			lockPassword.setValidthrough(validthrough==null?cal.getTime():validthrough);
			lockPassword.setCreatetime(new Date());
			lockPassword.setUsestatus(1);
			lockPassword.setSynstatus(1);
			hilockpasswordService.save(lockPassword);
		}else{
			if(StringUtils.isBlank(tid)){
				return;
			}
			int status;
			switch(resultCode){
			case 1:
				status = 0;
				break;
			case 2:
				status = 4;
				break;
			case 3:
				status = 0;
				break;
			case 4:
				status = 3;
				break;
			case 5:
				status = 3;
				break;
			default:
				status = 3;
				break;
			}
			
			LockPassword lockpassword = hilockpasswordService.findByTidAndZwavedeviceid(tid, device.getId().intValue());
			if(lockpassword!=null){
				lockpassword.setStatus(status);
				if(status==4){
					lockpassword.setSynstatus(2);
				}else{
					lockpassword.setSynstatus(1);
				}
				lockpassword.setUsercode(usercode);
				hilockpasswordService.update(lockpassword);
			}else{
				log.error("Can not find the lockpassword with the info from IRemote Server ! zwavedeviceid:"+zwavedeviceid+";dvcid:"+device.getId().intValue()+";tid:"+tid);
			}
		}
	}

}