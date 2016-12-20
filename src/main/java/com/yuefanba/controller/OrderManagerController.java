package com.yuefanba.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.yuefanba.interceptor.ShopAuthInterceptor;
import com.yuefanba.service.OrderManagerService;

/**
 * 餐厅订单管理页面控制器
 * @author 池家文
 */
@Before(ShopAuthInterceptor.class)
public class OrderManagerController extends Controller {

	/*
	 * 餐厅订单管理业务操作类对象
	 */
	OrderManagerService service = new OrderManagerService();

	public void index(){
		renderJsp("template.jsp");
	}
}
