package com.isurpass.message.processor;

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

public class SetLockuserPasswordResultProcessor implements IMessageProcessor {
	private static Log log = LogFactory.getLog(SetLockuserPasswordResultProcessor.class);

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
		if(StringUtils.isBlank(tid)){
			return;
		}
		int status = 0;
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
		}
		Device device = hideviceservice.findByPtDeviceId(zwavedeviceid);
		LockPassword lockpassword = hilockpasswordService.findByTidAndZwavedeviceid(tid, device.getId().intValue());
		if(lockpassword!=null){
			lockpassword.setStatus(status);
			lockpassword.setUsercode(usercode);
			lockpassword.setSynstatus(resultCode==2?2:1);
			hilockpasswordService.update(lockpassword);
		}else{
			log.error("Can not find the lockpassword with the info from IRemote Server ! zwavedeviceid:"+zwavedeviceid+";dvcid:"+device.getId().intValue()+";tid:"+tid);
		}
		
	}

}
