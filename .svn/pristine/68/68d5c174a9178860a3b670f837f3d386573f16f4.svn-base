package com.ant.business;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ant.controller.PersonController;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;
/**
 * 小区
 * @author 艾建
 * @date  2016-1-30上午10:10:38
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name="district" )
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Long id;
	
	/**用户id*/
	private Long personId;

	/**省份*/
	private String provinceCode;
	
	/**城市*/
	private String cityCode;
	
	/**区县*/
	private String areasCode;
	
	/**省份名称*/
	private String provinceName;
	
	/**城市名称*/
	private String cityName;
	
	/**区县名称*/
	private String areasName;
	
	/**图片*/
	private String districtImg;
	
	/**小区名称*/
	private String districtName;
	
	/**地址*/
	private String address;
	
	/**房间数量*/
	private Integer roomCount;
	
	/**管理员数量*/
	private Integer managerCount;
	
	/**电价 以分为单位*/
	private Integer price;
	
	private Integer waterprice;
	private Integer hotwaterprice;
	
	/**均摊费用 以分为单位*/
	private Integer shareAmount;
	
	/**备注*/
	private String remark;
	
	/**插入时间*/
	private Date inputDate;
	
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(Integer shareAmount) {
		this.shareAmount = shareAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreasCode() {
		return areasCode;
	}

	public void setAreasCode(String areasCode) {
		this.areasCode = areasCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreasName() {
		return areasName;
	}

	public void setAreasName(String areasName) {
		this.areasName = areasName;
	}

	public String getDistrictImg() {
		return districtImg;
	}

	public void setDistrictImg(String districtImg) {
		this.districtImg = districtImg;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public Integer getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(Integer managerCount) {
		this.managerCount = managerCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	/**
	 * 校验权限
	 * @param inspirationCollect
	 * @param sessionPersonVo
	 */
	public static void checkRole(District district,PersonVo sessionPersonVo, ResourceBundle rb){
		if(district.getPersonId() == null || 
				district.getPersonId().longValue() != sessionPersonVo.getId().longValue()){
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
	public static void checkSecondAdminRole(District district,PersonVo sessionPersonVo, ResourceBundle rb){
		List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
		if ((district.getPersonId() == null)
				|| (district.getPersonId().longValue() != sessionPersonVo.getId().longValue()
				&& !idList.contains(district.getId()))) {
			throw new BusinessException(rb.getString("noprivilegetoaccess"));
		}
	}
	public Integer getWaterprice() {
		return waterprice;
	}

	public void setWaterprice(Integer waterprice) {
		this.waterprice = waterprice;
	}

	public Integer getHotwaterprice() {
		return hotwaterprice;
	}

	public void setHotwaterprice(Integer hotwaterprice) {
		this.hotwaterprice = hotwaterprice;
	}
}
