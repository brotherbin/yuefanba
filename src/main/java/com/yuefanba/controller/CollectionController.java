package com.yuefanba.controller;

import java.util.HashMap;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.interceptor.UserAuthInterceptor;
import com.yuefanba.model.User;
import com.yuefanba.service.CollectionService;

/**
 * 收藏夹页面控制器 负责收藏夹的显示和收藏
 * 
 * @author 兰星珂
 *
 */
@Before(UserAuthInterceptor.class)
public class CollectionController extends Controller {
	/*
	 * 收藏夹业务操作类对象
	 */
	CollectionService service = new CollectionService();

	public void index() {
		renderJsp("collection.jsp");
	}

	/**
	 * 显示收藏餐厅信息
	 */
	public void listCollection() {
//		 起始下标、查询数量、排序规则
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		//	String userId = "sfer001";
		Page<Record> pageShop = service.listCollection(pageNum, pageSize, userId);
		renderJson(pageShop);
	}

//	 删除收藏餐厅
	public void deleteCollection() {

		String shopId = getPara("shopId");
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		HashMap<String, String> msg = new HashMap<String, String>();
		if (service.deleteCollectionByShopId(userId, shopId)){
			msg.put("status", "ok");
		} else {
			msg.put("staus", "failure");
		}
		renderJson(msg);
	}
	//判断餐厅是否已经收藏
	public void judgeCollection(){
		String shopId = getPara("shopId");
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		HashMap<String, String> judgemsg = new HashMap<String, String>();
		if (service.judgeCollection(userId,shopId)){
			judgemsg.put("status", "ok");
		} else {
			judgemsg.put("staus", "failure");
		}
		renderJson(judgemsg);

	}

	// 增加收藏
	public void addCollection() {
		String shopId = getPara("shopId");
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");

//		设定userId和userId检测功能
//		String shopId = "1";
//		String userId = "sfer001";           

		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = 
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String collectTime = sdf.format(dt);
		HashMap<String, String> addmsg = new HashMap<String, String>();

		if (service.addCollection(shopId, userId, collectTime)){
			addmsg.put("status", "ok");
		} else {
			addmsg.put("staus", "failure");
		}
		renderJson(addmsg);
	}
}

