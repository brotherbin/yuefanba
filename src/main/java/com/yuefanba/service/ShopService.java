package com.yuefanba.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.config.Protocol;
import com.yuefanba.model.AddressInfo;
import com.yuefanba.model.EstimateInfo;
import com.yuefanba.model.FoodInfo;
import com.yuefanba.model.OrderDetail;
import com.yuefanba.model.OrderInfo;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.model.ValueCard;

/**
 * 餐厅业务类
 * @author 宋达彬
 *
 */
public class ShopService {

	/**
	 * 根据餐厅ID获取餐厅信息
	 * @param shopId
	 * @return
	 */
	public ShopInfo getShopByShopId(String shopId) {
		return ShopInfo.dao.getShopByShopId(shopId);
	}
	
	/**
	 * 根据餐厅ID获取餐厅的所有菜品信息
	 * @param shopId
	 * @return
	 */
	public List<FoodInfo> listFoodByShopId(String shopId) {
		return FoodInfo.dao.listFoodByShopId(shopId);
	}
	
	/**
	 * 根据用户ID获取其送餐地址信息
	 * @param userId
	 * @return
	 */
	public List<AddressInfo> listAddrByUserId(String userId) {
		return AddressInfo.dao.listAddrByUserId(1, 4, userId).getList();
	}
	
	/*
	 * 保存订单
	 */
	public boolean saveOrder(String userId, String orderId, JSONObject orderJsonObj) {
		// 获取订单信息并保存
		Map<String, Object> attrs = new HashMap<String, Object>();
		attrs.put("orderId", orderId) ;
		attrs.put("userId", userId);
		attrs.put("shopId", orderJsonObj.getString("shopId")) ;
		attrs.put("getName", orderJsonObj.getString("getName"));
		attrs.put("orderAddr", orderJsonObj.getString("orderAddr"));
		attrs.put("orderTel", orderJsonObj.getString("orderTel")) ;
		attrs.put("totalPrice", Float.parseFloat(orderJsonObj.getDouble("amount")+""));
		attrs.put("orderState", orderJsonObj.getInt("payWay"));
		attrs.put("sendState", Protocol.ORDER_STATUS_NEWORDER);
		attrs.put("orderTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		if (OrderInfo.dao.saveOrder(attrs)){
			if (!ShopInfo.dao.addShopOrderNum(orderJsonObj.getString("shopId"))){
				return false;
			}
			if (orderJsonObj.getInt("payWay")==2) {
				ValueCard.dao.valueCardUpdate(orderId, userId);
			}
			// 获取订单详细信息并保存
			JSONArray foodList = orderJsonObj.getJSONArray("foodList");
			int detailLength = foodList.length();
			for (int i = 0; i < detailLength; i ++) {
				JSONObject foodJsonObj = (JSONObject) foodList.get(i);
				OrderDetail model = new OrderDetail();
				model.set("orderId", orderId);
				model.set("userId", userId);
				model.set("foodId", foodJsonObj.getString("foodId"));
				model.set("foodPrice", Float.parseFloat(foodJsonObj.getDouble("foodPrice")+""));
				model.set("foodNum", foodJsonObj.getInt("foodCount"));
				model.set("foodName", foodJsonObj.getString("foodName"));
				if (!OrderDetail.dao.saveOrderDetail(model)){
					return false;
				}
				if (!FoodInfo.dao.updateFoodBuyNum(foodJsonObj.getString("foodId"), foodJsonObj.getInt("foodCount"))){
					return false;
				}
			}
			
		}
		return true;
	}
	
	/**
	 * 分页查询餐厅的所有评价信息
	 * @param pageNumber
	 * @param pageSize
	 * @param shopId
	 * @return
	 */
	public Page<Record> pageEstimationByShopId(int pageNumber, int pageSize, String shopId) {
		return EstimateInfo.dao.pageEstimationByShopId(pageNumber, pageSize, shopId);
	}
}
