package com.ant.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.isurpass.common.exception.BusinessException;
import com.isurpass.common.hibernate.BasicScroll;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSON;
import com.ant.business.DataPrivilegeGrant;
import com.ant.business.District;
import com.ant.business.Person;
import com.ant.config.MjConfig;
import com.ant.restful.service.RestfulUtil;
import com.ant.util.CookiesUtils;
import com.ant.util.StringUtil;
import com.ant.vo.CommonVo;
import com.ant.vo.DistrictVo;
import com.ant.vo.PersonVo;
import com.isurpass.service.DataPrivilegeGrantService;
import com.isurpass.service.HiDistrictService;
import com.isurpass.service.HiOperateLogService;
import com.isurpass.service.HiPersonService;

/**
 * 用户
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/person")
@Scope(value = "prototype")
public class PersonController extends BaseController{

	private static Log log = LogFactory.getLog(PersonController.class);
	
	@Resource
	private HiPersonService hiPersonService;
	
	@Resource
	private HiOperateLogService hiOperateLogService;

	private static HiDistrictService hiDistrictService;
	
	private static DataPrivilegeGrantService privilegeService;
	/**
	 * 分页查询一级管理员下的二级管理员
	 * @param commonVo
	 * @return
	 */
	@RequestMapping(value="/findAdminPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("commonVo")CommonVo commonVo){
		try{
			PersonVo sessionPersonVo = (PersonVo)this.getCurrentUser();
			BasicScroll scroll = new BasicScroll(commonVo.getPage(), commonVo.getRows());
			List<HiPersonService> lst = hiPersonService.findPage(sessionPersonVo, scroll);			
			
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	/**
	 * 新增二级管理员
	 * @param person
	 * @return
	 */
	@RequestMapping(value="/saveadmin")
	public @ResponseBody Map<String , Object> saveAdmin(@ModelAttribute("person")Person person){
		try{
			PersonVo sessionPersonVo = (PersonVo)this.getCurrentUser();
			person.setSuperpersonid(sessionPersonVo.getId().intValue());
			person.setType(1);
			hiPersonService.savePerson(person,getRB());
			return createResponse(1 , getRB().getString("jsave") , null);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	@RequestMapping(value="/deleteadmin")
	public @ResponseBody Map<String , Object> deleteAdmin(@RequestParam(value = "ids[]") Long[] ids ,@RequestParam(value = "operate") Integer operate){
		try{
			for(int i=0;i<ids.length;i++){
				Person person = hiPersonService.findById(ids[i]);
				person.setStatus(operate);
				hiPersonService.saveOrUpdate(person);
			}
			return createResponse(1 , getRB().getString("jsave") , null);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	/**
	 * 根据id获取二级管理员信息到修改界面
	 * @param adminid
	 * @return
	 */
	@RequestMapping(value="/getadmininfo")
	public @ResponseBody Map<String , Object> getAdminInfo(@RequestParam(value = "adminid") Integer adminid ){
		try{
			Person person = hiPersonService.findById(adminid.longValue());
			return createResponse(1 , getRB().getString("jsave") , person);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/updateadminbaseinfo")
	public @ResponseBody Map<String , Object> updateAdminBaseInfo(@ModelAttribute("person")Person person){
		try{
			Person dbperson = hiPersonService.findById(person.getId());
			dbperson.setRealName(person.getRealName());
			dbperson.setPhone(person.getPhone());
			hiPersonService.saveOrUpdate(dbperson);
			return createResponse(1 , getRB().getString("jsave") , null);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	@RequestMapping(value="/updateadminpassword")
	public @ResponseBody Map<String , Object> updateAdminPassword(@ModelAttribute("person")Person person){
		try{
			Person dbperson = hiPersonService.findById(person.getId());
			dbperson.setPersonPassword(StringUtil.md5(person.getPersonPassword()));
			hiPersonService.saveOrUpdate(dbperson);
			return createResponse(1 , getRB().getString("jsave") , null);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/findprivilege")
	public @ResponseBody Map<String , Object> findPrivilege(@RequestParam(value = "adminid") Integer adminid){
		try{
			Person person = hiPersonService.findById(adminid.longValue());
			PersonVo sessionPersonVo = (PersonVo)this.getCurrentUser();
			List<District> lst = hiDistrictService.findByPersonId(sessionPersonVo.getId());
			List<DataPrivilegeGrant> privilegelst = privilegeService.findByPersonId(adminid);
			List<Long> districtidlist = new ArrayList<Long>();
			List<DistrictVo> llst = new ArrayList<DistrictVo>();

			for(DataPrivilegeGrant d : privilegelst){
				districtidlist.add(d.getDistrictid().longValue());
			}
			for(District d: lst){
				DistrictVo vo = new DistrictVo();
				PropertyUtils.copyProperties(vo, d);
				if(districtidlist.size()>0&&districtidlist.contains(d.getId())){
					vo.setChecked(1);
				}
				llst.add(vo);
			}
			return createResponse(1 , getRB().getString("jquerysuccess") , llst,person);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	@RequestMapping(value="/updateprivilege")
	public @ResponseBody Map<String , Object> updatePrivilege(@RequestParam(required=false,value = "ids[]")Long[] ids ,@RequestParam(value = "adminid") Integer adminid){
		try{
			List<DataPrivilegeGrant> oldprivilegelst = privilegeService.findByPersonId(adminid);
			if(ids==null){
				for(DataPrivilegeGrant d : oldprivilegelst){
					d.setStatus(9);
					d.setDeletetime(new Date());
					privilegeService.saveOrUpdate(d);
					}
				return createResponse(1 , getRB().getString("updatesuccess") , null);
			}
			
			List<Long> newdistrictid = new ArrayList<Long>(Arrays.asList(ids));
		
			for(DataPrivilegeGrant d : oldprivilegelst){
				if(newdistrictid.contains(d.getDistrictid())){
					newdistrictid.remove(d.getDistrictid());
				}else{
					d.setStatus(9);
					d.setDeletetime(new Date());
					privilegeService.saveOrUpdate(d);
				}
			}
			//新增
			for(Long i:newdistrictid){
				DataPrivilegeGrant d = new DataPrivilegeGrant();
				d.setPersonid(adminid);
				d.setDistrictid(i);
				d.setStatus(1);
				d.setCreatetime(new Date());
				privilegeService.save(d);
			}
			return createResponse(1 , getRB().getString("updatesuccess") , null);
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	/**
	  * @Description: 注册
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Create Date: 2015-2-8下午2:27:17
	  * @Parameters:@return
	 */
	@RequestMapping(value="/regist")
	public @ResponseBody Map<String , Object> regist(@ModelAttribute("person")Person person){
		try{
			/**校验验证码*/
			String captcha = request.getParameter("captcha");
			
			if(StringUtil.checkNull(person.getPersonCode()) || StringUtil.checkNull(person.getPersonPassword()) || 
					StringUtil.checkNull(person.getPhone()) || StringUtil.checkNull(captcha)){
				throw new BusinessException(getRB().getString("uppv"));
			}
			
			/**校验验证码*/
			this.checkCaptcha(person.getPhone(), captcha);
			person.setType(0);
			hiPersonService.savePerson(person,getRB());
			CookiesUtils.addCookie(response, "captcha","", 60 * 60); // 默认可用一个小时
			
			return createResponse(1 , getRB().getString("rs") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 登陆
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Create Date: 2015-2-8下午2:27:17
	  * @Parameters:@return
	 */
	@RequestMapping(value="/login")
	public @ResponseBody Map<String , Object> login(@ModelAttribute("person") Person person){
		
		String ip = StringUtil.objectToString(request.getParameter("ip"));
		if(StringUtil.checkNull(ip))
			throw new BusinessException(getRB().getString("ipcnbb"));
		
		try{
			
			PersonVo dbPersonVo = hiPersonService.findByPersonCodePassword(person.getPersonCode(),person.getPersonPassword());
			if(dbPersonVo == null){
				return createResponse(0 , super.getMessage("view.label.accountpassworderror")  , null);
			}
			if(dbPersonVo.getStatus()==2){
				return createResponse(0 , super.getMessage("账号被冻结！")  , null);
			}
			if(dbPersonVo.getType()==1&&dbPersonVo.getSuperpersonid()!=null&&dbPersonVo.getSuperpersonid()!=0){
				Person superperson = hiPersonService.findById(dbPersonVo.getSuperpersonid().longValue());
				if(superperson.getStatus()!=1){
					return createResponse(0 , super.getMessage("一级管理员账号不可用！")  , null);
				}
			}
			
			dbPersonVo.setIp(ip);
			PersonVo sessionPersonVo = this.initLoginSession(dbPersonVo);
			long personid = 0;
			int operatepersonid = sessionPersonVo.getId().intValue();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				personid = sessionPersonVo.getSuperpersonid().longValue();
			}else{
				personid = sessionPersonVo.getId();
			}
			hiOperateLogService.saveOperateLog(0, "登录系统", dbPersonVo.getRealName(), personid,operatepersonid,sessionPersonVo.getIp() , sessionPersonVo.getId(),"login",null,null);
			return createResponse(1 , getRB().getString("dlcg") , sessionPersonVo);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	
	}
	public static boolean checkIfSecondAdmin(PersonVo sessionPersonVo){
		if(sessionPersonVo.getType()==1&&sessionPersonVo.getSuperpersonid()!=null&&sessionPersonVo.getSuperpersonid()!=0){
			return true;
		}
		return false;
	}
	public static List<Long> findOwnDistrictIdList(PersonVo sessionPersonVo){
		List<Long> lst = new ArrayList<Long>();
		if(sessionPersonVo.getType()==1&&sessionPersonVo.getSuperpersonid()!=null&&sessionPersonVo.getSuperpersonid()!=0){
			//二级管理
			List<DataPrivilegeGrant> dplist = privilegeService.findByPersonId(sessionPersonVo.getId().intValue());
			for(DataPrivilegeGrant d : dplist){
				lst.add(d.getDistrictid().longValue());
			}
		}else{
			//一级管理
			List<District> dlist = hiDistrictService.findByPersonId(sessionPersonVo.getId());
			for(District d: dlist){
				lst.add(d.getId());
			}
		}
		return lst;
	}
	
	private PersonVo initLoginSession(PersonVo dbPersonVo)
	{
		PersonVo newPersonVo = new PersonVo();
		newPersonVo.setPersonCode(dbPersonVo.getPersonCode());
		newPersonVo.setId(dbPersonVo.getId());
		newPersonVo.setPersonImgUrl(dbPersonVo.getPersonImgUrl());
		newPersonVo.setRealName(dbPersonVo.getRealName());
		newPersonVo.setIp(dbPersonVo.getIp());
		newPersonVo.setType(dbPersonVo.getType());
		newPersonVo.setSuperpersonid(dbPersonVo.getSuperpersonid());
		newPersonVo.setStatus(dbPersonVo.getStatus());
		this.updatePersonSession(newPersonVo);
		return newPersonVo;
	}
	
	private void updatePersonSession(PersonVo sessionPersonVo)
	{
		request.getSession().removeAttribute("user");
		request.getSession().setAttribute("user", JSON.toJSONString(sessionPersonVo));
	}
	
	/**
	 * 校验用户名是否已存在
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/checkPersonCode")
	public @ResponseBody Map<String , Object> checkPersonCode(@ModelAttribute("person") Person person){
		try
		{
			return createResponse(1 , getRB().getString("jsuccess") , hiPersonService
					.checkPersonCode(person.getPersonCode(), null));
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 退出登陆
	 * @author aj
	 * @param  
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/logout")
	public @ResponseBody Map<String , Object> logout(@ModelAttribute("personVo") PersonVo personVo)
	{
		try
		{
			request.getSession().removeAttribute("user");
			return createResponse(1 , getRB().getString("tccg") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 修改密码
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/updatePersonPassword")
	public @ResponseBody Map<String , Object> updatePersonPassword(@ModelAttribute("personVo") PersonVo personVo){
		try{
			PersonVo sessionPersonVo = (PersonVo)this.getCurrentUser();
			PersonVo dbPersonVo = hiPersonService.findByPersonCodePassword
				(sessionPersonVo.getPersonCode(), personVo.getPersonPassword());
			String newPassword = request.getParameter("newPassword").toString();
			if(dbPersonVo == null){
				throw new BusinessException(getRB().getString("jmmcw"));
			}
			
			if(newPassword == null || "".equals(newPassword)){
				throw new BusinessException(getRB().getString("mmwk"));
			}
			hiPersonService.resetPersonPassword(sessionPersonVo.getPersonCode(),newPassword);
			return createResponse(1 , getRB().getString("modifysuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 重置密码
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/resetPersonPassword")
	public @ResponseBody Map<String , Object> resetPersonPassword(@ModelAttribute("personVo") PersonVo personVo){
		try{
			/**校验验证码*/
			String captcha = request.getParameter("captcha");
			
			if(StringUtil.checkNull(personVo.getPersonCode()) ||
					StringUtil.checkNull(captcha) || StringUtil.checkNull(personVo.getPersonPassword())){
				throw new BusinessException(getRB().getString("upv"));
			}
			
			/**校验验证码*/
			String sendCaptcha = ""; //发送的验证码
			String sendPersonCode = ""; //发送验证码时用的邮箱
			sendPersonCode = CookiesUtils.getCookieValueByName(request,
				"personCode");
			sendCaptcha = CookiesUtils.getCookieValueByName(request,
				"captcha");
			if(StringUtil.checkNull(sendCaptcha) || StringUtil.checkNull(sendPersonCode)){
				throw new BusinessException(getRB().getString("reoc"));
			}
			if(!sendPersonCode.equals(StringUtil.md5(personVo.getPersonCode()))){
				throw new BusinessException(getRB().getString("reoc"));
			}
			if(!sendCaptcha.equals(StringUtil.md5(captcha))){
				throw new BusinessException(getRB().getString("vcw"));
			}
			
			
			Person dbPerson = hiPersonService.findByPersonCode(personVo.getPersonCode());
			if(dbPerson == null){
				throw new BusinessException(getRB().getString("udne"));
			}
			dbPerson.setPersonPassword(StringUtil.md5(personVo.getPersonPassword()));
			hiPersonService.update(dbPerson);
			
			CookiesUtils.addCookie(response, "captcha","", 60 * 60); // 默认可用一个小时
			
			return createResponse(1 , getRB().getString("resetsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 修改用户信息
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/updatePerson")
	public @ResponseBody Map<String , Object> updatePerson(@ModelAttribute("personVo") PersonVo personVo){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			
			hiPersonService.updatePerson(sessionPersonVo.getId(),personVo);
			sessionPersonVo.setRealName(personVo.getRealName());
			this.updatePersonSession(sessionPersonVo);
			
			return createResponse(1 , getRB().getString("jsave") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 查询用户信息
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/findPerson")
	public @ResponseBody Map<String , Object> findPerson(@ModelAttribute("personVo") PersonVo personVo){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			
			Person dbPerson = hiPersonService.findById(sessionPersonVo.getId());
			dbPerson.setPersonPassword("");
			
			return createResponse(1 , getRB().getString("jsave") , dbPerson);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 修改用户头像
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	@RequestMapping(value="/updatePersonImgUrl")
	public @ResponseBody Map<String , Object> updatePersonImgUrl(@ModelAttribute("personVo") PersonVo personVo,@RequestParam("file") MultipartFile file){
		try{
			Map fileMap = this.upload(file);
			PersonVo sessionPersonVo = super.getCurrentUser();
			
			hiPersonService.updatePersonImgUrl(sessionPersonVo.getId(), fileMap.get("filePath").toString());
			sessionPersonVo.setPersonImgUrl(fileMap.get("filePath").toString());
			this.updatePersonSession(sessionPersonVo);
			
			return createResponse(1 , getRB().getString("uploadsuccess"), null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}

	public Map upload(MultipartFile file) throws Exception
	{
		String fileFix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//文件扩展名
        String filename = StringUtil.getUUID() + fileFix;
        String folder = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        String dir =  MjConfig.get("filePath") + folder;
        File dirFile = new File(dir);
        if(!dirFile.exists())
        	dirFile.mkdir();
        
		FileUtils.writeByteArrayToFile(new File(dir,filename), file.getBytes());
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("filePath", folder + filename);

        return map;
	}
	
	

	/**
	 * 发送验证码
	 */
	@RequestMapping(value = "sendRegistCheckNumber")
	public @ResponseBody Map<String , Object> sendRegistCheckNumber(@ModelAttribute("person") Person person) {
		try {
			if(StringUtil.checkNull(person.getPersonCode()) || StringUtil.checkNull(person.getPhone())){
				throw new BusinessException(getRB().getString("upcnbb"));
			}
			String captcha = StringUtil.generateNumberCaptcha(6);
			boolean temp = hiPersonService.checkPersonCode(person.getPersonCode(),null);
			if (temp)
				throw new BusinessException(getRB().getString("tuir"));

			// /**发送短信验证码*/
			this.sendCaptcha(null,MjConfig.get("registPhont"), captcha,getRB());
			
			CookiesUtils.addCookie(response, "phone", StringUtil.md5(person
					.getPhone()), 60 * 60); // 默认可用一个小时
			CookiesUtils.addCookie(response, "captcha",
					StringUtil.md5(captcha), 60 * 60); // 默认可用一个小时
			return createResponse(1 , getRB().getString("aspca"), null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	 * 校验手机号码、验证码是否正确
	 * @author aj
	 * @param  
	 * @return 
	 * @version V1.0
	 */
	public void checkCaptcha(String phone,String captcha){
		String sendCaptcha = ""; //发送的验证码
		String sendPhone = ""; //发送验证码时用的手机号码
		sendPhone = CookiesUtils.getCookieValueByName(request,
			"phone");
		sendCaptcha = CookiesUtils.getCookieValueByName(request,
			"captcha");
		if(StringUtil.checkNull(sendCaptcha) || StringUtil.checkNull(sendPhone)){
			throw new BusinessException(getRB().getString("reoc"));
		}
		if(!sendPhone.equals(StringUtil.md5(phone))){
			throw new BusinessException(getRB().getString("reoc"));
		}
		if(!sendCaptcha.equals(StringUtil.md5(captcha))){
			throw new BusinessException(getRB().getString("vcw"));
		}
	}
	
	/**
	 * 发送验证码
	 */
	@RequestMapping(value = "sendResetPassCheckNumber")
	public @ResponseBody Map<String , Object> sendResetPassCheckNumber(@ModelAttribute("person") Person person) {
		try {
			if(StringUtil.checkNull(person.getPersonCode()) ){
				throw new BusinessException(getRB().getString("upcnbb"));
			}
			String captcha = StringUtil.generateNumberCaptcha(6);
			Person dbPerson = hiPersonService.findByPersonCode(person.getPersonCode());
			if (dbPerson == null)
				throw new BusinessException(getRB().getString("tuinr"));

			// /**发送短信验证码*/
			this.sendCaptcha(dbPerson.getCountrycode(),dbPerson.getPhone(), captcha,getRB());
			
			CookiesUtils.addCookie(response, "personCode", StringUtil.md5(person.getPersonCode()), 60 * 60); // 默认可用一个小时
			CookiesUtils.addCookie(response, "captcha",StringUtil.md5(captcha), 60 * 60); // 默认可用一个小时
			return createResponse(1 , getRB().getString("sendsuccess"), null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	public @ResponseBody Map<String , Object> sendCaptcha(String countrycode,String phone,String captcha, ResourceBundle rb){
		/**发送短信*/
		Map<String , Object> pmap = new HashMap<String , Object>();
		if(countrycode==null||countrycode.length()==0){
			pmap.put("countrycode", "86");
		}else{
			pmap.put("countrycode", countrycode);
		}
		pmap.put("phonenumber",phone);
		pmap.put("message", getRB().getString("usingsmartcloud") + captcha + "");
		String str = RestfulUtil.postToServerHttps(MjConfig.get("restUrl") + "thirdpart/zufang/sendsms", pmap,rb);
		Map resultMap = JSON.parseObject(str,HashMap.class);
		if(StringUtil.checkNull(resultMap.get("resultCode"))){
			throw new BusinessException(getRB().getString("seecib"));
		}
		int resultCode = Integer.parseInt(resultMap.get("resultCode").toString());
		if(resultCode != 0){
			throw new BusinessException(getRB().getString("seeci") + resultCode);
		}else{
			return createResponse(1 , getRB().getString("suijima"), null);
		}
	}
	@Autowired
	public void setHiDistrictService(HiDistrictService hiDistrictService) {
		PersonController.hiDistrictService = hiDistrictService;
	}
	@Autowired
	public void setPrivilegeService(DataPrivilegeGrantService privilegeService) {
		PersonController.privilegeService = privilegeService;
	}
	
}
