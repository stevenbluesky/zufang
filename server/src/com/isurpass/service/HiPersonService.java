package com.isurpass.service;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.ant.util.StringUtil;
import com.isurpass.common.exception.BusinessException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ant.business.DataPrivilegeGrant;
import com.ant.business.Person;
import com.ant.config.MjConfig;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

/**
 * 用户service
 * @author aijian
 *
 */
public class HiPersonService extends BaseService<Person> 
{
	private static Log log = LogFactory.getLog(HiPersonService.class);
	
	/**
	  * @param rb 
	 * @Description: 注册
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Create Date: 208-2-8下午2:28:20
	  * @Parameters:
	 */
	public void savePerson(Person person, ResourceBundle rb)
	{
		if(this.checkPersonCode(person.getPersonCode(), null))
			throw new BusinessException(rb.getString("hps1"));
		
		person.setPersonImgUrl(MjConfig.get("personDefaultImgUrl"));
		person.setPersonPassword(StringUtil.md5(person.getPersonPassword()));
		person.setInputDate(new Date());
		person.setStatus(1);
		super.save(person);

	}
	
	/**
	 * 校验用户名是否已存在
	 * @author aj
	 * @param  @param personVo
	 * @return 
	 * @Create Date: 208-2-8下午2:30:20
	 * @version V1.0
	 */
	public boolean checkPersonCode(String personCode,Long id)
	{
		Person dbPerson = this.findByPersonCode(personCode);
		if(dbPerson == null){
			return false;
		}
		if(id == null)
			return true;
		if(id.longValue() == dbPerson.getId().longValue()){	//传入的id与查询出的id一致，则不重复
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @author aj
	 * @param  personCode 用户名
	 * @return 返回用户信息
	 * @Create Date: 208-2-8下午2:30:12
	 * @version V1.0
	 */
	public Person findByPersonCode(String personCode)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("personCode", personCode));
		cw.add(ExpWrap.in("status", new int[]{1,2}));
		return cw.uniqueResult();
	}
	
	/**
	 * 根据手机号查询用户信息
	 * @author aj
	 * @param  phone 用户名
	 * @return 返回用户信息
	 * @Create Date: 208-2-8下午2:30:12
	 * @version V1.0
	 */
	public Person findByPhone(String phone)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("phone", phone));
		return cw.uniqueResult();
	}
	
	/**
	 * 校验手机号是否已存在
	 * @author aj
	 * @param  @param personVo
	 * @return 
	 * @Create Date: 208-2-8下午2:30:20
	 * @version V1.0
	 */
	public boolean checkPhone(String phone,Long id){
		Person dbPerson = this.findByPhone(phone);
		if(dbPerson == null){
			return false;
		}
		if(id == null)
			return true;
		if(id.longValue() == dbPerson.getId().longValue()){	//传入的id与查询出的id一致，则不重复
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据用户名密码查询用户
	 * @param userName 用户名
	 * @param userPassword 用户密码
	 * @Create Date: 208-2-8下午3:30:10
	 * @return 查询结果
	 */
	public PersonVo findByPersonCodePassword(String personCode,String personPassword)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("personCode", personCode));
		cw.add(ExpWrap.eq("personPassword", StringUtil.md5(personPassword)));
		cw.add(ExpWrap.in("status", new int[]{1,2}));
		List<Person> list = cw.list();
		if(list.size() == 0){
			return null;
		}
		Person p = list.get(0);
		PersonVo vo = new PersonVo();
		try
		{
			PropertyUtils.copyProperties(vo, p);
		} 
		catch (Throwable e)
		{
			log.error(e.getMessage() ,e);
		} 
		
		return vo ;
	}
	
	/**
	 * 重置密码
	 * @author aj
	 * @return 
	 * @date 2015-3-11 下午07:25:25
	 * @version V1.0
	 */
	public Person resetPersonPassword(String personCode,String userPassword)
	{
		Person person = (Person)this.findByPersonCode(personCode);
		person.setPersonPassword(StringUtil.md5(userPassword));
		super.update(person);
		return person;
	}
	
	/**
	 * 根据id查询
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	public Person findById(Long id){
		return super.query(id);
	}
	
	/**
	 * 更新用户头像
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	public void updatePersonImgUrl(Long id,String personImgUrl){
		Person dbPerson = this.findById(id);
		dbPerson.setPersonImgUrl(personImgUrl);
		super.update(dbPerson);
	}
	
	/**
	 * 更新用户信息
	 * @author aj
	 * @return 
	 * @version V1.0
	 */
	public void updatePerson(Long id,PersonVo personVo){
		Person dbPerson = this.findById(id);
		if(personVo.getPersonImgUrl() != null){
			dbPerson.setPersonImgUrl(personVo.getPersonImgUrl());
		}
		if(personVo.getRealName() != null){
			dbPerson.setRealName(personVo.getRealName());
		}
		if(personVo.getPhone() != null){
			dbPerson.setPhone(personVo.getPhone());
		}
		if(personVo.getIdNo() != null){
			dbPerson.setIdNo(personVo.getIdNo());
		}
		if(personVo.getEmail() != null){
			dbPerson.setEmail(personVo.getEmail());
		}
		super.update(dbPerson);
	}

	public List<HiPersonService> findPage(PersonVo sessionPersonVo, BasicScroll scroll) {
		CriteriaWrap cw = new CriteriaWrap(Person.class.getName() , sessionFactory);
		cw.add(ExpWrap.eq("superpersonid",  sessionPersonVo.getId().intValue()));
		cw.add(ExpWrap.in("status", new int[]{1,2}));
		cw.setScroll(scroll);
		
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}
	
}
