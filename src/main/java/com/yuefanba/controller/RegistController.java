package com.yuefanba.controller;


import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.yuefanba.model.User;
import com.yuefanba.service.LoginAndRegistService;
import com.yuefanba.util.IDGenerator;
import com.yuefanba.util.ImageRender;

/**
 * 注册页面控制器
 * @author 刘鹏宇
 */
public class RegistController extends Controller {




	/**

	 * 登录注册业务操作类对象
	 */
	LoginAndRegistService service = new LoginAndRegistService();

	public void index(){
		render("regist.jsp");
	}

	/**
	 * 用户注册
	 */

	public void save(){
		String validateCodePara = getPara("validate");
		String validateCode = getSessionAttr("validateCode");
		String userId=IDGenerator.genUserId();
		String userName=getPara("userName");
		String password=service.md5(getPara("password"));
		int userType=getParaToInt("userType");
		Map<String, String> tipMsg = new HashMap<String, String>();
		if (validateCodePara.equals(validateCode)) {
			User finduser=service.registVaildate(userName);
			if(finduser!=null){
				//用户已经存在时处理
				tipMsg.put("msg3", "failed");
				renderJson(tipMsg);
			}else{
				service.saveUserInfo(userId, "", "", "", 0);
				boolean rs=service.saveUser(userId, userName, password, userType);
				if(rs){
					tipMsg.put("state", "ok");
					renderJson(tipMsg);
				}
				else{
					//注册失败处理
					tipMsg.put("msg1", "failed");
					renderJson(tipMsg);
				}	
			}
		}
		else{
			//验证码错误处理
			tipMsg.put("msg2", "failed");
			renderJson(tipMsg);
		}	
	}

	/**
	 * 获取验证码
	 */

	public void validateImg(){

		render(new ImageRender());

	}




}
