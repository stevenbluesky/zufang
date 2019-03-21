package com.ant.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ant.config.MjConfig;

/**
 * 公共
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/common")
@Scope(value = "prototype")
public class CommonController extends BaseController {
	
	private static Log log = LogFactory.getLog(CommonController.class);

	/**
	 * 上传文件
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value = "/uploadFile")
	public @ResponseBody Map<String , Object> uploadFile(@RequestParam("file") MultipartFile file){
		try{
	        
	        Map map = this.upload(file);
			return createResponse(1 , getRB().getString("uploadsuccess") , map);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 获取服务器id
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value = "/findServiceId")
	public @ResponseBody Map<String , Object> findServiceId(){
		try{
	        return createResponse(1 , getRB().getString("checksuccess") , MjConfig.get("serviceId"));
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	public Map upload(MultipartFile file) throws Exception{
		String fileFix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//文件扩展名
		
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + fileFix;
        String folder = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        String dir =  MjConfig.get("filePath") + folder;
        File dirFile = new File(dir);
        if(!dirFile.exists()){
        	dirFile.mkdir();
        }
		FileUtils.writeByteArrayToFile(new File(dir,filename), 
				file.getBytes());
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("filePath", folder + filename);

        return map;
	}
	
}
