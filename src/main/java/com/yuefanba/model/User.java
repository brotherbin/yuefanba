package com.yuefanba.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 用户表模型
 * @author 833902
 *
 */
public class User extends Model<User> {

	private static final long serialVersionUID = -7382505037575156643L;

	public static final User dao = new User();
	
	
	public User selVal(String userId){
		String sql="select userName,userMoney from user as u,userinfo as ui where u.userId=ui.userId and ui.userId=?";
		User user=dao.findFirst(sql,userId);
		return user;
	}

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public User loginValidate(String userName,String password){
		String sql="select userId, userName, userType from user where userName=? and password=?";
		User user=dao.findFirst(sql, userName,password);
		return user;
	}

	/**
	 * 判断用户是否已经存在
	 * @param userName
	 * @return
	 */
	public User userSearch(String userName){
		String sql="select userId, userName, userType from user where userName=?";
		User user=dao.findFirst(sql, userName);
		return user;
	}
	
	/**
	 * 注册
	 */

	public boolean saveUser(String userId,String userName,String password,int userType){
		User users=new User();
		users.set("userId", userId);
		users.set("userName", userName);
		users.set("password", password);
		users.set("userType", userType);
		return users.save();
	}
}
