package com.ant.business;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity
@Table(name="roomfee" )
public class RoomFee 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private long roomfeeid;
	private long roomid;
	/**201608*/
	private int month;
	private Float electricitypremonthdegree;
	private Float electricitycurrentdegrees;
	private int electricityprice;
	private int electricityfee;
	private int waterfee;
	private Float waterpremonthdegree;
	private Float watercurrentdegrees;
	private int waterprice;
	private int hotwaterfee;
	private Float hotwaterpremonthdegree;
	private Float hotwatercurrentdegrees;
	private int hotwaterprice;
	private int pooledfee;
	private int totalamount ;
	private int rentfee ;
	private int status ;
	
	public long getRoomfeeid() {
		return roomfeeid;
	}
	public void setRoomfeeid(long roomfeeid) {
		this.roomfeeid = roomfeeid;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getElectricityfee() {
		return electricityfee;
	}
	public void setElectricityfee(int electricityfee) {
		this.electricityfee = electricityfee;
	}
	public int getWaterfee() {
		return waterfee;
	}
	public void setWaterfee(int waterfee) {
		this.waterfee = waterfee;
	}
	public int getHotwaterfee() {
		return hotwaterfee;
	}
	public void setHotwaterfee(int hotwaterfee) {
		this.hotwaterfee = hotwaterfee;
	}
	public int getPooledfee() {
		return pooledfee;
	}
	public void setPooledfee(int pooledfee) {
		this.pooledfee = pooledfee;
	}
	public int getTotalamount() {
		return getWaterfee() + getHotwaterfee() + getElectricityfee() + getPooledfee() + getRentfee();
	}

	public long getRoomid() {
		return roomid;
	}
	public void setRoomid(long roomid) {
		this.roomid = roomid;
	}

	public Float getElectricitycurrentdegrees()
	{
		return electricitycurrentdegrees;
	}
	public void setElectricitycurrentdegrees(Float electricitycurrentdegrees)
	{
		this.electricitycurrentdegrees = electricitycurrentdegrees;
	}
	public int getElectricityprice()
	{
		return electricityprice;
	}
	public void setElectricityprice(int electricityprice)
	{
		this.electricityprice = electricityprice;
	}
	public Float getWatercurrentdegrees()
	{
		return watercurrentdegrees;
	}
	public void setWatercurrentdegrees(Float watercurrentdegrees)
	{
		this.watercurrentdegrees = watercurrentdegrees;
	}
	public int getWaterprice()
	{
		return waterprice;
	}
	public void setWaterprice(int waterprice)
	{
		this.waterprice = waterprice;
	}
	public Float getHotwatercurrentdegrees()
	{
		return hotwatercurrentdegrees;
	}
	public void setHotwatercurrentdegrees(Float hotwatercurrentdegrees)
	{
		this.hotwatercurrentdegrees = hotwatercurrentdegrees;
	}
	public int getHotwaterprice()
	{
		return hotwaterprice;
	}
	public void setHotwaterprice(int hotwaterprice)
	{
		this.hotwaterprice = hotwaterprice;
	}
	public Float getElectricitypremonthdegree()
	{
		return electricitypremonthdegree;
	}
	public void setElectricitypremonthdegree(Float electricitypremonthdegree)
	{
		this.electricitypremonthdegree = electricitypremonthdegree;
	}
	public Float getWaterpremonthdegree()
	{
		return waterpremonthdegree;
	}
	public void setWaterpremonthdegree(Float waterpremonthdegree)
	{
		this.waterpremonthdegree = waterpremonthdegree;
	}
	public Float getHotwaterpremonthdegree()
	{
		return hotwaterpremonthdegree;
	}
	public void setHotwaterpremonthdegree(Float hotwaterpremonthdegree)
	{
		this.hotwaterpremonthdegree = hotwaterpremonthdegree;
	}
	public int getRentfee() {
		return rentfee;
	}
	public void setRentfee(int rentfee) {
		this.rentfee = rentfee;
	}
	public void setTotalamount(int totalamount) {
		this.totalamount = totalamount;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
}
