package com.yuefanba.service;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.model.ValueCard;

public class ValueCardService{
	
	
	/**
	 * 查询储值卡信息
	 */
	public Page<Record> ValueCardSel(int pageNumber, int pageSize, String userId){
		return ValueCard.dao.ValueCardSelect(pageNumber,pageSize,userId);
	}
	
	public Page<Record> selectValueList(int pageNumber, int pageSize, String userId){
		return ValueCard.dao.selectValueList(pageNumber, pageSize, userId);
	}
	
	public boolean updateMoney(int userMoney,String updateUid){
		return ValueCard.dao.updateUserMoney(userMoney, updateUid);
	}
	

	/**
	 * 储值卡中添加记录
	 */
	
	public boolean addValue(String userId,int addMoney){
		return ValueCard.dao.addValue(userId, addMoney);
	}
	
}