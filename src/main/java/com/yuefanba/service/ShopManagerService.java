package com.yuefanba.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.domain.OrderDetailWrap;
import com.yuefanba.model.FoodInfo;
import com.yuefanba.model.OrderDetail;
import com.yuefanba.model.OrderInfo;
import com.yuefanba.model.ShopInfo;

/**
 * 餐厅信息维护业务类
 * @author 池家文
 *
 */
public class ShopManagerService {

	/**
	 * 餐厅信息修改
	 * @param shopInfo
	 * @return
	 */
	public boolean shopInfoModify(ShopInfo shopInfo) {
		// TODO Auto-generated method stub

		return ShopInfo.dao.shopInfoModify(shopInfo);
	}
	/**
	 * 餐厅信息查询
	 * @param userId    登录ID
	 * @return
	 */
	public ShopInfo shopInfoSelect(String userId) {

		// TODO Auto-generated method stub
		return ShopInfo.dao.shopInfoSelect(userId);
	}
	/**
	 * 查询出所有订单
	 * @param userId
	 * @param status 
	 * @param sortTag 
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	public Page<OrderDetailWrap> getOrderList(String userId, Integer status, int pageNum, int pageSize, int sortTag) {
		// TODO Auto-generated method stub
		List<OrderDetailWrap> orderList = new ArrayList<OrderDetailWrap>();
		ShopInfo shopInfo=ShopInfo.dao.select(userId);
		String shopId=shopInfo.getStr("shopId");
		Page<Record> shopOrderList=OrderInfo.dao.getOrderList(shopId,status,pageNum,pageSize, sortTag);
		List<Record>   orderListIndex=shopOrderList.getList();
		for(Record order:orderListIndex){
			OrderDetailWrap orderDetailWrap = new OrderDetailWrap();
			String  orderId= order.getStr("orderId");
			
			List<Record> OrderDetailList=OrderDetail.dao.selectShopOrderDetailByOrderId(orderId);
			if(OrderDetailList==null||OrderDetailList.equals("")){
				orderList.add(orderDetailWrap);

			}else{
				orderDetailWrap.setOrderID(orderId);
				orderDetailWrap.setUserName(order.getStr("getName"));
				orderDetailWrap.setOrderAddr(order.getStr("orderAddr"));
				orderDetailWrap.setShopTel(shopInfo.getStr("shopTel"));
				orderDetailWrap.setOrderTel(order.getStr("orderTel"));
				orderDetailWrap.setOrderState(order.getInt("orderState"));
				orderDetailWrap.setTotalPrice(order.getFloat("totalPrice"));
				orderDetailWrap.setSendState(order.getInt("sendState"));
				orderDetailWrap.setSendAdd(shopInfo.getFloat("sendAdd"));


				Date d = order.getDate("orderTime"); 
				SimpleDateFormat  sdf=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				orderDetailWrap.setOrderTime(sdf.format(d));		    	 
				orderDetailWrap.setList(OrderDetailList);
				orderList.add(orderDetailWrap);
			}
		}
		 Page<OrderDetailWrap>  orderPage = new Page<OrderDetailWrap>(orderList, shopOrderList.getPageNumber(), shopOrderList.getPageSize(), shopOrderList.getTotalPage(), shopOrderList.getTotalRow());
		 return orderPage;
	}

	/**
	 * 菜品添加
	 * @param addFood
	 * @return  添加状态  （成功，失败）
	 */
	public boolean addFood(FoodInfo addFood) {
		// TODO Auto-generated method stub


		return FoodInfo.dao.addFood(addFood);
	}
	
	/**
	 * 餐厅菜品查询
	 * @param userId   餐厅登录者Id
	 * @param pageNum
	 * @param pageSize
	 * @param sortTag
	 * @return
	 */
	public Page<FoodInfo> foodListFind(String userId, int pageNum, int pageSize, int sortTag) {
		ShopInfo shopInfo=ShopInfo.dao.select(userId);
		String shopId=shopInfo.getStr("shopId");
		Page<FoodInfo>  foodList=FoodInfo.dao.foodListFind(shopId,pageNum,pageSize,sortTag);
		return foodList;
	}
	/**
	 * 食品信息修改
	 * @param foodModify
	 * @return
	 */
	public boolean foodInfoModify(FoodInfo foodModify) {
		// TODO Auto-generated method stub
		return FoodInfo.dao.foodInfoModify(foodModify);
	}
	/**
	 * 菜品删除
	 * @param foodId
	 * @return
	 */
	public boolean foodInfoDelet(String foodId) {

		return FoodInfo.dao.foodInfoDelet(foodId);
	}
	/**
	 * 指定菜品信息查询
	 * @param foodId
	 * @return
	 */
	public FoodInfo foodInfoFind(String foodId) {
		// TODO Auto-generated method stub
		return FoodInfo.dao.foodInfoFind(foodId);
	}
	/**
	 * 订单状态改变
	 * @param orderId
	 * @param status 0：买家未确认订单  1：未发货	2：送货中 3:送货结束
	 * @return
	 */
	public Boolean orderSure(String orderId, String status) {
		// TODO Auto-generated method stub
		return OrderInfo.dao.orderSure(orderId,status);
	}
	/**
	 * 商家信息查询
	 * @param shopId
	 * @return
	 */
	public ShopInfo shopInfoFind(String shopId) {
		// TODO Auto-generated method stub
		return ShopInfo.dao.shopInfoFind(shopId);
	}
	/**
	 *菜品停售
	 * @param foodId
	 * @return
	 */
	public Boolean foodStopSale(String foodId) {
		// TODO Auto-generated method stub
		 return FoodInfo.dao.foodStopSale(foodId);
	}

}
