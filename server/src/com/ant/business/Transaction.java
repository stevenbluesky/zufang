package com.ant.business;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity 
@Table(name="transaction" )
public class Transaction 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment") 
	private int transactionid;
	private int roomid;
	private int transactiontype ;
	private String description ;
	private int amount;
	private int prebalance;
	private int postbalance;
	private int status ;
	private Date createtime = new Date();
	private Date finishtime;
	private String tptransactionid;
	
	public int getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public int getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(int transactiontype) {
		this.transactiontype = transactiontype;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrebalance() {
		return prebalance;
	}
	public void setPrebalance(int prebalance) {
		this.prebalance = prebalance;
	}
	public int getPostbalance() {
		return postbalance;
	}
	public void setPostbalance(int postbalance) {
		this.postbalance = postbalance;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTptransactionid() {
		return tptransactionid;
	}
	public void setTptransactionid(String tptransactionid) {
		this.tptransactionid = tptransactionid;
	}

	
}
