package com.isurpass.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import com.alibaba.fastjson.JSONObject;
import com.ant.business.Room;
import com.ant.business.Transaction;
import com.ant.constant.TransactionStatus;
import com.ant.constant.TransactionType;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class TransactionService extends BaseService<Transaction> 
{
	public Transaction createTransaction(int roomid , int amount , TransactionType type , TransactionStatus status)
	{
		Transaction t = new Transaction();
		t.setRoomid(roomid);
		t.setAmount(amount);
		t.setStatus(status.ordinal());
		t.setTransactiontype(type.ordinal());
		
		t.setDescription(type.getDescription());
		
		if ( TransactionStatus.finished.equals(status) )
			t.setFinishtime(new Date());
		
		super.save(t);
		
		return t ; 
	}
	
	public void transactionFinished(int transactionid , JSONObject data,Map<String , String> pm)
	{
		CriteriaWrap cw = new CriteriaWrap(Transaction.class.getName() , sessionFactory);
		cw.add(ExpWrap.eq("transactionid", transactionid));
		Transaction t = cw.uniqueResult();
		
		if ( t == null || t.getStatus() == TransactionStatus.finished.ordinal() )
			return ;
		
		t.setStatus(TransactionStatus.finished.ordinal());
		t.setFinishtime(new Date());
		t.setTptransactionid(pm.get("transaction_id"));
		
		HiRoomService roomService = (HiRoomService)this.applicationContext.getBean("HiRoomService");

		Room r = roomService.findById(new Long(t.getRoomid()));
		if ( r == null )
			return ;
		
		t.setPrebalance(r.getBalance());
		r.setBalance(r.getBalance() + t.getAmount());
		t.setPostbalance(r.getBalance());
	}
	
	public List<Transaction> queryTransaction(int roomid , BasicScroll scroll)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("roomid", roomid));
		cw.addOrder(Order.desc("transactionid"));
		cw.setScroll(scroll);
		
		return cw.list();
	}
}
