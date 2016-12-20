package com.yuefanba.model;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.config.Protocol;

/**
 * 订单信息表模型
 * 
 * @author 833901
 *
 */
public class OrderInfo extends Model<OrderInfo> {

	private static final long serialVersionUID = -5025875513042167658L;

	public static final OrderInfo dao = new OrderInfo();
	
	/**
	 * 订单信息分页查询
	 * @param shopId
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sortTag
	 * @return
	 */
	public Page<Record> getOrderList(String shopId, Integer status, int pageNum, int pageSize, int sortTag) {
		
		Page<Record> orderList = null;
		if(status!=4){
			String sql="select *  ";
			String sqlExceptSelect="from orderinfo where shopId=? and sendState=? order by orderTime desc";
			orderList=Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, shopId,status);
		}else{
			String sql="select * ";
			String sqlExceptSelect="from orderinfo where shopId=? order by orderTime desc";
			orderList=Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, shopId);
		}
		return orderList;
	}



	/**
	 * 分页查询当前用户的不同状态的订单信息
	 */
	public Page<Record> selectOrderInfoByUsername(String userName, int status, int pageNum, int pageSize, int sortTag) {
		Page<Record> orderInfoList = null;
		// 显示全部的订单
		if (status == 0) { 
			String sql = "select orderId,o.shopId,s.shopName,o.getName,orderAddr,orderTel,totalPrice,orderState,sendState,orderTime,shopTel,sendAdd ";
			String sqlExceptSelect = "from orderinfo o,user u,shopinfo s where o.userId=u.userId and o.shopId=s.shopId and u.userName=? order by orderTime desc";
			orderInfoList = Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, userName);
		//显示待确认的订单(status=0)
		}else if(status==5){   
			String sql = "select orderId,o.shopId,s.shopName,o.getName,orderAddr,orderTel,totalPrice,orderState,sendState,orderTime,shopTel,sendAdd ";
			String sqlExceptSelect = "from orderinfo o,user u,shopinfo s where o.userId=u.userId and o.shopId=s.shopId and u.userName=? and sendState=? order by orderTime desc";
			orderInfoList = Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, userName, 0);
		// 显示未派送(status=1)，正在派送(status=2)，已完成(status=3)，取消的订单(status=4)
		}else { 
			String sql = "select orderId,o.shopId,s.shopName,o.getName,orderAddr,orderTel,totalPrice,orderState,sendState,orderTime,shopTel,sendAdd ";
			String sqlExceptSelect = "from orderinfo o,user u,shopinfo s where o.userId=u.userId and o.shopId=s.shopId and u.userName=? and sendState=? order by orderTime desc";
			orderInfoList = Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, userName, status);
		}
		return orderInfoList;
	}

	/**
	 * 取消未派送的订单信息
	 */
	public boolean cancelOrder(String orderId) {
		String sql = "update orderinfo set sendState=? where orderId=?";
		return (1 == Db.update(sql, Protocol.ORDER_STATUS_CANCEL, orderId));
	}
	
	/**
	 * 删除已取消的订单信息
	 */
	public boolean deleteOrder(String orderId) {		
		String sql="delete from orderinfo where orderId=?";		
		return (1 == Db.update(sql, orderId));
	}

	/**
	 * 订单状态改变
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Boolean orderSure(String orderId, String status) {
		Integer sendStatus=Integer.parseInt(status);
		Boolean mystatus = false;
		OrderInfo orderInfo= new OrderInfo();
		String sql="select * from orderinfo where orderId=?";
		orderInfo= dao.findFirst(sql,orderId);
		try {
			mystatus=orderInfo.set("sendState", sendStatus).update();
		} catch (Exception e) {
			System.out.println(e);
		}
		return mystatus;
	}

	/*
	 * 保存订单信息
	 */
	public boolean saveOrder(Map<String, Object> attrs) {
		return new OrderInfo().setAttrs(attrs).save();
	}
	/**
	 * 最受欢迎餐厅
	 */
	public List<Record> top10Shop() {
		String sqlshop = "select o.shopId,s.shopName,s.shopImg,count(o.orderId) as default2 from orderinfo o LEFT JOIN shopinfo s on s.shopId=o.shopId  where s.shopState=? GROUP BY o.shopId ORDER BY default2 desc limit 10";
		List<Record> top10shop = Db.find(sqlshop,Protocol.SHOP_STATUS_APPROVE);
	    return top10shop;
	}
	public List<Record> TopShop() {
		String sqlshop = "select o.shopId,s.shopName,s.shopImg,s.shopAddr,s.shopTel,s.startPrice,s.sendAdd,s.sendTime,s.shopNotice,count(o.orderId) as orderNum from orderinfo o LEFT JOIN shopinfo s on s.shopId=o.shopId  where s.shopState=? GROUP BY o.shopId ORDER BY orderNum desc limit 10";
		//System.out.println("night");
		List<Record> topshopA = Db.find(sqlshop,Protocol.SHOP_STATUS_APPROVE);
	  //  System.out.println(topshopA);
        return topshopA;
	}
}
