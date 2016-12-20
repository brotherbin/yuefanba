package com.yuefanba.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.yuefanba.config.Protocol;
import com.yuefanba.model.User;
import com.yuefanba.service.LoginAndRegistService;

/**
 * 登录页面控制器
 * @author 刘鹏宇
 */
public class LoginController extends Controller {

	/*
	 * 登录注册业务操作类对象
	 */
	LoginAndRegistService service = new LoginAndRegistService();

	public void index(){
		keepPara("initTip", "url");
		renderJsp("login.jsp");
	}

	/**
	 * 用户登录
	 */

	public void validate(){

		String userName=getPara("userName");
		String password=service.md5(getPara("password"));   
		User loginUser=service.loginValidate(userName,password);
		Map<String, String> tipMsg = new HashMap<String, String>();
		if(loginUser!=null ){
			setSessionAttr("loginUser", loginUser);
			int userType = loginUser.getInt("userType");
			if(userType==Protocol.USER_TYPE_OF_USER){
				tipMsg.put("state", "user");
				renderJson(tipMsg);
			}
			else if(userType==Protocol.USER_TYPE_OF_SHOP){
				tipMsg.put("state", "shoper");
				renderJson(tipMsg);
			}
			else if(userType==Protocol.USER_TYPE_OF_ADMIN){
				tipMsg.put("state", "admin");
				renderJson(tipMsg);
			}
		}
		else{
			//登录失败时处理
			tipMsg.put("msg","用户名或密码不正确！");
			renderJson(tipMsg);
		}
	}

	/**
	 * 判断当前是否有登录用户
	 */
	public void checkLogin() {
		User loginUser = getSessionAttr("loginUser");
		Map<String, Object> msg = new HashMap<String, Object>();
		if (loginUser==null) {
			msg.put("isLogin", false);
			renderJson(msg);
		} else {
			msg.put("isLogin", true);
			msg.put("loginUser", loginUser);
			renderJson(msg);
		}
	}



	/**

	 * 用户退出登录
	 */
	public void logout() {
		setSessionAttr("loginUser", null);
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("logout", true);
		renderJson(msg);
	}
}
