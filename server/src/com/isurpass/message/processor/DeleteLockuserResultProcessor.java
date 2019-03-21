package com.isurpass.message.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class DeleteLockuserResultProcessor implements IMessageProcessor{
	private static Log log = LogFactory.getLog(DeleteLockuserResultProcessor.class);
	
	@Resource
	private HiLockPasswordService hilockpasswordService;
	@Resource
	private HiDeviceService hideviceservice;
	
	@Override
	public void process(JSONObject message) {
		
		LockUserResultVo lockuserresult = JSON.parseObject(message.getString("objparam"),LockUserResultVo.class);

		String zwavedeviceid = message.getString("zwavedeviceid");
		int usercode = message.getIntValue("intparam");
		int resultCode = lockuserresult.getResultCode();
		String tid = lockuserresult.getTid();
		Device device = hideviceservice.findByPtDeviceId(zwavedeviceid);

		int status;
		switch(resultCode){
			case 1:
				status = 9;
				break;
			case 2:
				status = 7;
				break;
			default:
				status = 7;
				break;
		}
		LockPassword lockpassword = null;
		List<LockPassword> lockpasswordlist = new ArrayList<LockPassword>();
		if(StringUtils.isNotBlank(tid)){
			lockpassword = hilockpasswordService.findByTidAndZwavedeviceid(tid, device.getId().intValue());
			if(lockpassword!=null){
				lockpassword.setStatus(status);
				lockpassword.setUsercode(usercode);
				if(status==9){
					lockpassword.setDeletetime(new Date());
				}
				lockpassword.setSynstatus(1);
				hilockpasswordService.update(lockpassword);
			}else{
				log.error("Can not find the lockpassword with the info from IRemote Server !zwavedeviceid:"+zwavedeviceid+";dvcid:"+device.getId().intValue()+";tid:"+tid);
			}
		}else{
			lockpasswordlist = hilockpasswordService.findByZwavedeviceidAndUsercodeThroughAPP(device.getId().intValue(), usercode);
			if(lockpasswordlist!=null){
				lockpasswordlist.get(0).setStatus(status);
				lockpasswordlist.get(0).setUsercode(usercode);
				lockpasswordlist.get(0).setSynstatus(1);
				if(status==9){
					lockpasswordlist.get(0).setDeletetime(new Date());
				}
				hilockpasswordService.update(lockpasswordlist.get(0));
			}
		}
	}
}