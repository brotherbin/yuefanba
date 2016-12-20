package com.yuefanba.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 投诉信息表模型
 * 
 * @author 833901
 *
 */
public class ComplaintInfo extends Model<ComplaintInfo> {

	private static final long serialVersionUID = -8450538393360576421L;

	public static final ComplaintInfo dao = new ComplaintInfo();
/**
 * 保存投诉信息
 * @param orderId
 * @param userId
 * @param content
 * @param shopId
 */
	public void saveComplaint(String orderId, String userId, String content,String shopId) {
		// TODO Auto-generated method stub
		ComplaintInfo complaint = new ComplaintInfo();
		complaint.set("orderId", orderId);
		complaint.set("userId", userId);
		complaint.set("complaintQue", content);
		System.out.println(333);
		complaint.set("shopId", shopId);
		complaint.save();
	}
/**
 * 查询用户投诉的订单是否存在
 * @param orderId
 * @return
 */
	public Record getShopId(String orderId) {
		String sql = "select shopId from orderinfo where orderId=?";
		System.out.println("begin");
		Record shopId = Db.findFirst(sql,orderId);
		System.out.println("getShop");
		return shopId;
	}
}
