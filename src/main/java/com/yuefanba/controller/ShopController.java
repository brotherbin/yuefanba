package com.yuefanba.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yuefanba.interceptor.UserAuthInterceptor;
import com.yuefanba.model.AddressInfo;
import com.yuefanba.model.FoodInfo;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.model.User;
import com.yuefanba.service.ShopService;
import com.yuefanba.util.IDGenerator;

/**
 * 餐厅信息页面控制器
 * 
 * @author 宋达彬
 */
public class ShopController extends Controller {

	/*
	 * 餐厅信息及下单业务操作类对象
	 */
	ShopService service = new ShopService();

	public void index() {
		String shopId = getPara(0);
		ShopInfo shopInfo = service.getShopByShopId(shopId);
		setAttr("shopInfo", shopInfo);
		render("shop.jsp");
	}

	/*
	 * 根据餐厅ID获取餐厅信息
	 */
	public void getShopInfo(){
		String shopId = getPara("shopId");
		ShopInfo shopInfo = service.getShopByShopId(shopId);
		renderJson(shopInfo);
	}

	/*
	 * 获取餐厅菜品列表
	 */
	public void listAllFood() {
		String shopId = getPara("shopId");
		List<FoodInfo> foodList = service.listFoodByShopId(shopId);
		renderJson(foodList);
	}

	/*
	 * 进入确认订单页面
	 */
	@Before(UserAuthInterceptor.class)
	public void confirmOrder() {
		String shopId = getPara(0);
		if (shopId == null) {
			redirect("/");
			return;
		}
		ShopInfo shopInfo = service.getShopByShopId(shopId);
		setAttr("shopInfo", shopInfo);
		render("confirmOrder.jsp");
	}

	/*
	 * 保存购物车信息
	 */
	public void saveCart() {
		String cartJsonStr = getPara("cartJsonStr");
		// 使用Session对象保存购物车内容
		setSessionAttr("cartJsonStr", cartJsonStr);

		// 使用Cookie对象保存购物车内容
		//setCookie("cartJsonStr", cartJsonStr, 60 * 60 * 24 * 7);
		renderJson();
	}

	/*
	 * 获取购物车信息
	 */
	public void getCart() {
		String shopId = getPara("shopId");
		// 从session对象中获取购物车信息
		String cartJsonStr = getSessionAttr("cartJsonStr");

		// 从Cookie对象中获取购物车信息
		//String cartJsonStr = getCookie("cartJsonStr");
		if (StrKit.notBlank(cartJsonStr) && StrKit.notBlank(shopId)) {
			JSONObject cartJsonObj = new JSONObject(cartJsonStr);
			if ( shopId.equals(cartJsonObj.getString("shopId")) ){
				renderJson(cartJsonStr);
			} else {
				renderJson();
			}
		} else {
			renderJson();
		}
	}

	/*
	 * 获取用户地址信息
	 */
	@Before(UserAuthInterceptor.class)
	public void getAddress(){
		User u = getSessionAttr("loginUser");
		String userId = u.getStr("userId");
		List<AddressInfo> addrList = service.listAddrByUserId(userId);
		renderJson(addrList);
	}

	/*
	 * 提交订单
	 */
	@Before({Tx.class, UserAuthInterceptor.class})
	public void saveOrder(){
		// 获取订单信息JSON字符串
		String orderJsonStr = getPara("orderJsonStr");
		// 将订单信息字符串转换为JSON对象
		JSONObject orderJsonObj = new JSONObject(orderJsonStr);
		// 获取登录用户ID
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		// 生成订单编号
		String orderId = IDGenerator.genOrderId();
		// 保存订单信息
		service.saveOrder(userId, orderId, orderJsonObj);
		// 清空购物车
		setSessionAttr("cartJsonStr", null);
		String orderTel = orderJsonObj.getString("orderTel");
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("orderId", orderId);
		msg.put("orderTel", orderTel);
		renderJson(msg);
	}
	
	/*
	 * 获取评价信息
	 */
	public void getEstimation() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 20);
		String shopId = getPara("shopId");
		Page<Record> page = service.pageEstimationByShopId(pageNumber, pageSize, shopId);
		renderJson(page);
	}

	/*
	 * 下单成功页面
	 */
	public void success(){
		setAttr("orderId", getPara("orderId"));
		setAttr("orderTel", getPara("orderTel"));
		render("orderSuccess.jsp");
	}
}
