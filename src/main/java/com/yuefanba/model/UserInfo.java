package com.yuefanba.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 用户扩展信息表模型
 * @author 833901
 */
public class UserInfo extends Model<UserInfo> {

	private static final long serialVersionUID = 4127040317148035829L;
	
	public static final UserInfo dao = new UserInfo();

	public UserInfo getUserInfo(String userId) {
		// TODO Auto-generated method stub
		String  sql="select * from userinfo where userId=?";
		UserInfo userInfo=findFirst(sql,userId);
		return userInfo;
	}
	public boolean saveUser(String userId,String userName,String password,int userType){
		User users=new User();
		users.set("userId", userId);
		users.set("userName", userName);
		users.set("password", password);
		users.set("userType", userType);
		return users.save();
	}
	
	public boolean saveUserInfo(String userId,String name,String userAddr,String userTel,int userMoney){
		UserInfo userInfo=new UserInfo();
		userInfo.set("userId", userId);
		userInfo.set("uname", name);
		userInfo.set("userAddr", userAddr);
		userInfo.set("userTel", userTel);
		userInfo.set("userMoney", userMoney);
		return userInfo.save();
		
	}
	

}
