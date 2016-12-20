package com.yuefanba.controller;

import java.util.HashMap;
import java.util.List;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.interceptor.UserAuthInterceptor;
import com.yuefanba.model.Province;
import com.yuefanba.model.User;
import com.yuefanba.model.Area;
import com.yuefanba.model.City;
import com.yuefanba.service.UserInfoService;

/**
 * 用户信息管理及地址管理页面控制器
 * @author 田粉粉
 */
@Before(UserAuthInterceptor.class)
public class UserInfoController extends Controller {

	/*
	 * 用户信息管理业务操作类对象 
	 */
	UserInfoService service = new UserInfoService();

	public void index(){
		renderJsp("address.jsp");
	}

	/**
	 * 前端获取表单提交过来的数据
	 */
	public void save(){		
		User user = getSessionAttr("loginUser");
		String userId=user.getStr("userId");
		// String userId="tian";	 
		String getName = getPara("getName");
		String userPhone = getPara("userPhone");
		String userAddress = getPara("userAddress");

	    boolean rs = service.saveAddress(userId,getName, userPhone,userAddress);
	    //返回前端数据
	    HashMap<String, String> msg = new HashMap<String , String>();

		if (rs) {			
			msg.put("status", "ok");

		} else {
			msg.put("status", "failure");
		}
		renderJson(msg);		

	}
	/**
	 * 编辑地址
	 */
	public void updateAddress() {
		String updateId = getPara("updateId");		
		String getName = getPara("getName");
		String userPhone = getPara("userPhone");
		String userAddress = getPara("userAddress");
		boolean rs = service.updateAddress(getName, userPhone,userAddress,updateId);	
		HashMap<String, String> upmsg = new HashMap<String, String>();
		if (rs) {			
			upmsg.put("status", "yes");

		} else {
			upmsg.put("status", "no");
		}
		renderJson(upmsg);
	}
	/**
	 * 显示地址列表
	 */
	public void listAddress(){
		// 起始下标、查询数量、排序规则

		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);	
		User user = getSessionAttr("loginUser");
		String userId=user.getStr("userId");				
		Page<Record> addresslist = service.listAddress(pageNum, pageSize, userId);
		renderJson(addresslist);
	}
	/**
	 * 删除地址
	 */
	public void deleteAddress() {
		String id = getPara("id");		  
		HashMap<String, String> delmsg = new HashMap<String, String>();
		if (service.deleteAddressById(id)){
			delmsg.put("status", "success");
		} else {
			delmsg.put("staus", "failed");
		}
		renderJson(delmsg);
	}

	/**
	 * 获取省份
	 */
	public void province() {		
		//		User loginUser = (User)getSessionAttr("loginUser");
		//		String userId = loginUser.getStr("userId");

		List<Province> provinceList = service.provinceSelect();
        renderJson(provinceList);				
	}	
	/**
	 * 获取城市
	 */
	public void city() {	
		String provinceCode = getPara("code");
		//		User loginUser = (User)getSessionAttr("loginUser");
		//		String userId = loginUser.getStr("userId");
		List<City> cityList = service.citySelect(provinceCode);
        renderJson(cityList);				

	}	
	/**
	 * 获取地区
	 */
	public void area(){	
		String cityCode = getPara("cityCode");		
		//		User loginUser = (User)getSessionAttr("loginUser");
		//		String userId = loginUser.getStr("userId");			
		List<Area> areaList = service.areaSelect(cityCode);
		renderJson(areaList);				

	}
}

