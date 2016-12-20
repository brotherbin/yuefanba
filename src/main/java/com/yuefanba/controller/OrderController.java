package com.yuefanba.controller;

import java.util.HashMap;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.yuefanba.domain.OrderDetailWrap;
import com.yuefanba.interceptor.UserAuthInterceptor;
import com.yuefanba.service.OrderService;
import com.yuefanba.model.User;

/**
 * 用户订单信息页面控制器
 * @author 黄小训
 */
@Before(UserAuthInterceptor.class)
public class OrderController extends Controller {

	/*
	 * 用户订单业务操作类对象
	 */
	OrderService service = new OrderService();

	public void index(){
		renderJsp("myOrder.jsp");
	}

	/**
	 * 查询当前用户不同状态的订单列表
	 */

	public void listOrderInfo(){
		User user = getSessionAttr("loginUser");
		String userName=user.getStr("userName");
		int status=getParaToInt("status");
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);		
		int sortTag = getParaToInt("sortTag", 0);
		Page<OrderDetailWrap> list=service.pageOrderByUsername(userName,status,pageNum, pageSize, sortTag);
		renderJson(list);
	}

	/**
	 * 取消未派送的订单信息
	 */
	public void cancelOrder(){
		HashMap<String, Boolean> canlmsg = new HashMap<String, Boolean>();
		String orderId=getPara("id");
		boolean result =service.cancelOrder(orderId) ;
		if(result){
			canlmsg.put("status",result);
		}else {
			canlmsg.put("status", result);
		}
		renderJson(canlmsg);				
	}
	
	/**
	 * 删除已取消的订单信息
	 */
	public void deleteOrder(){
		HashMap<String, Boolean> deletemsg = new HashMap<String, Boolean>();
		String orderId=getPara("id");
		boolean result =service.deleteOrder(orderId) ;
		if(result){
			deletemsg.put("status",result);
		}else {
			deletemsg.put("status", result);
		}
		renderJson(deletemsg);				
	}
	
	/**
	 * 评价已完成的订单信息
	 */
	public void saveEatimateContent(){
		HashMap<String, Boolean> estimatemsg = new HashMap<String, Boolean>();
		User user = getSessionAttr("loginUser");
		String userId=user.getStr("userId");
		String orderId=getPara("orderId");
		String shopId=getPara("shopId");
		String estimateContent=getPara("estimateContent");
		boolean result=service.saveEatimateContent(userId,orderId,shopId,estimateContent);
		if(result){
			estimatemsg.put("status",result);
		}else {
			estimatemsg.put("status", result);
		}
		renderJson(estimatemsg);
	}
}
