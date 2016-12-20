package com.yuefanba.controller;

import java.util.HashMap;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.model.User;
import com.yuefanba.service.ValueCardService;

public class ValueCardController extends Controller {
	ValueCardService service=new ValueCardService();
	
	public void index(){
		renderJsp("valuecard.html");
	}
	
	public void addValue(){
		renderJsp("addValue.html");
	}
	
	public void valueList(){
		renderJsp("valueList.html");
	}
	
	/**
	 * 储值卡余额查询
	 */
	
	
	public void ValueCardSelect(){
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		Page<Record> ValSel=service.ValueCardSel(pageNum,pageSize,userId);
		renderJson(ValSel);
		
	}
	
	
	
	/**
	 * 充值记录分页列表
	 *author: 833902
	 */
	
	public void selectValueList(){
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		User user = getSessionAttr("loginUser");
		String userId = user.getStr("userId");
		Page<Record> valueList=service.selectValueList(pageNum, pageSize, userId);
		renderJson(valueList);
	}
	
	/**
	 * 用户充值
	 */
	public void addMoney(){
		User user = getSessionAttr("loginUser");
		String updateId = user.getStr("userId");
		int userMoney=getParaToInt("addMoney");
		boolean rs=service.updateMoney(userMoney, updateId);
		boolean rv=service.addValue(updateId, userMoney);
		HashMap<String, String> upmsg = new HashMap<String, String>();
		if (rs==true&&rv==true) {			
			upmsg.put("status","yes");
			renderJson(upmsg);
		} else {
			upmsg.put("status","no");
			renderJson(upmsg);
		}	
	}	
}
