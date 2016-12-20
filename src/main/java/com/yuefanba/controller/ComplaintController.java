package com.yuefanba.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.model.User;
import com.yuefanba.service.ComplaintService;

/**
 * 投诉中心页面控制器
 * @author 郑艳霞
 */
public class ComplaintController extends Controller {

	/*
	 * 投诉中心业务操作类对象
	 */
	ComplaintService service = new ComplaintService();

	public void index(){
		renderJsp("complaint.jsp");
	}
	/**
	 * 保存投诉内容信息
	 */
	public void doComplaint(){
		String orderId=getPara("orderId");
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		String content = getPara("content");
		Map<String, String> msg = new HashMap<String, String>();
		Record rc = service.getShopId(orderId);
		String shopId = null;
		if(rc != null){
			shopId = rc.getStr("shopId");
			service.saveComplaint(orderId, userId,content,shopId);
			msg.put("status", "ok");
		}
		else{
			msg.put("status", "no");
		}
		renderJson(msg);
	}
}
