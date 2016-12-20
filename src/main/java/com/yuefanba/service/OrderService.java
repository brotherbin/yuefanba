package com.yuefanba.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.domain.OrderDetailWrap;
import com.yuefanba.model.EstimateInfo;
import com.yuefanba.model.OrderDetail;
import com.yuefanba.model.OrderInfo;


/**
 * 用户订单业务类
 * @author 黄小训
 *
 */
public class OrderService {
	
	/**
	 * 分页查询当前用户不同状态的订单列表
	 */
	public Page<OrderDetailWrap> pageOrderByUsername(String userName,int status,int pageNum,int pageSize,int sortTag){	     
		    List<OrderDetailWrap> orderList = new ArrayList<OrderDetailWrap>();
		    Page<Record> orderInfoPage= OrderInfo.dao.selectOrderInfoByUsername(userName,status,pageNum,pageSize, sortTag); 	
		    List<Record> orderInfoList = orderInfoPage.getList();
		    for (Record orderinfo : orderInfoList) {
		    	 OrderDetailWrap orderDetailWrap = new OrderDetailWrap();
		    	 String orderId=orderinfo.getStr("orderId");
		    	 List<Record> OrderDetailList=OrderDetail.dao.selectOrderDetailByOrderId(orderId);
		    	 orderDetailWrap.setOrderID(orderId);
		    	 orderDetailWrap.setShopId(orderinfo.getStr("shopId"));
		    	 orderDetailWrap.setShopName(orderinfo.getStr("shopName"));
		    	 orderDetailWrap.setUserName(orderinfo.getStr("getName"));
		    	 orderDetailWrap.setOrderAddr(orderinfo.getStr("orderAddr"));
		    	 orderDetailWrap.setOrderTel(orderinfo.getStr("orderTel"));
		    	 orderDetailWrap.setOrderState(orderinfo.getInt("orderState"));
		    	 orderDetailWrap.setSendState(orderinfo.getInt("sendState")); 
		    	 orderDetailWrap.setTotalPrice(orderinfo.getFloat("totalPrice"));
		    	 Date date =  orderinfo.getDate("orderTime");
		    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	 orderDetailWrap.setOrderTime(sdf.format(date));
		    	 orderDetailWrap.setShopTel(orderinfo.getStr("shopTel"));
		    	 orderDetailWrap.setSendAdd(orderinfo.getFloat("sendAdd"));		    
		    	 orderDetailWrap.setList(OrderDetailList);
		    	 orderList.add(orderDetailWrap);
		    }
		    Page<OrderDetailWrap>  orderPage = new Page<OrderDetailWrap>(orderList, orderInfoPage.getPageNumber(), orderInfoPage.getPageSize(), orderInfoPage.getTotalPage(), orderInfoPage.getTotalRow());
		    return orderPage;		    			    	 		    	       
 	}
	
	/**
	 * 取消未派送的订单信息
	 */
	public boolean cancelOrder(String orderId){
		return OrderInfo.dao.cancelOrder(orderId);
	}
	
	/**
	 * 删除已取消的订单信息
	 */
	public boolean deleteOrder(String orderId){
		OrderDetail.dao.deleteOrderDetail(orderId);
		return OrderInfo.dao.deleteOrder(orderId);
	}
	
	/**
	 * 评价已完成的订单信息
	 */
	public boolean saveEatimateContent(String userId,String orderId,String shopId,String estimateContent){
		return EstimateInfo.dao.saveEatimateContent(userId,orderId,shopId,estimateContent);
	}
 }
