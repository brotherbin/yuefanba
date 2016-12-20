package com.yuefanba.service;

import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.model.ComplaintInfo;

/**
 * 投诉中心业务类
 * @author 郑艳霞
 *
 */
public class ComplaintService {

	public void save() {
		// TODO Auto-generated method stub
		
	}
	public Record getShopId(String orderId){
		
		return ComplaintInfo.dao.getShopId(orderId);
		
	}
	public  void saveComplaint(String orderId, String userId, String content,String shopId) {
		ComplaintInfo.dao.saveComplaint(orderId, userId, content,shopId);
	}
}
