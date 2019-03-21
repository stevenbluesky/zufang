package com.isurpass.service;

import java.util.List;
import com.ant.business.Areas;
import com.ant.business.City;
import com.ant.business.Province;

import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class HiProvinceService extends BaseService<Province>
{
	
	/**
	 * 查询省份数据，加redis处理
	 * @author aj
	 * @param  @return
	 * @return 
	 * @date 2015-3-9 下午05:31:50
	 * @version V1.0
	 */
	public List<Province> findProvince()
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		return cw.list();
	}
	
	/**
	 * 根据省份查询城市数据，加redis处理
	 * @author aj
	 * @param  provinceCode 省代码
	 * @return 
	 * @date 2015-3-9 下午05:31:50
	 * @version V1.0
	 */
	public List<City> findCity(City city)
	{
		CriteriaWrap cw = new CriteriaWrap(City.class.getName() , super.sessionFactory);
		cw.addifNotNull(ExpWrap.eq("provinceCode", city.getProvinceCode()));
		cw.addifNotNull(ExpWrap.like("cityName", city.getCityName()));
		return cw.list();
	}

	/**
	 * 根据省份、城市代码获取市名称
	 * @author aj
	 * @param  @param provinceCode
	 * @param  @param cityCode
	 * @param  @return
	 * @return 
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	public String getCityNameByCode(String cityCode)
	{
		CriteriaWrap cw = new CriteriaWrap(City.class.getName() , super.sessionFactory);
		cw.addifNotNull(ExpWrap.eq("cityCode", cityCode));

		City city = cw.uniqueResult();
		if ( city == null )
			return "" ;
		return city.getCityName();
	}
	
	/**
	 * 根据省份代码获取省名称
	 * @author aj
	 * @param  @param provinceCode
	 * @param  @param cityCode
	 * @param  @return
	 * @return 
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	public String getProvinceNameByCode(String provinceCode)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("provinceCode",provinceCode));
		Province p = cw.uniqueResult();
		if ( p == null )
			return "" ;
		return p.getProvinceName();
	}
	
	/**
	 * 根据市、区县代码获取区县名称
	 * @author aj
	 * @param  @param provinceCode
	 * @param  @param cityCode
	 * @param  @return
	 * @return 
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	public String getAreasNameByCode(String areasCode)
	{
		CriteriaWrap cw = new CriteriaWrap(Areas.class.getName() , super.sessionFactory);
		cw.add(ExpWrap.eq("areasCode",areasCode));
		Areas ar = cw.uniqueResult();
		if ( ar == null )
			return "" ;
		return ar.getAreasName();
	}
	
	/**
	 * 根据市查询区县数据，加redis处理
	 * @author aj
	 * @param  areas 区域对象
	 * @return 
	 * @date 2015-3-9 下午05:31:50
	 * @version V1.0
	 */
	public List<Areas> findAreas(Areas areas)
	{
		CriteriaWrap cw = new CriteriaWrap(Areas.class.getName() , super.sessionFactory);
		cw.addifNotNull(ExpWrap.like("cityCode",areas.getCityCode()));
		cw.addifNotNull(ExpWrap.like("areasName",areas.getAreasName()));
		return cw.list();
	}
	
	

}
