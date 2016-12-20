package com.yuefanba.service;

import java.security.MessageDigest;

import com.yuefanba.model.User;
import com.yuefanba.model.UserInfo;

/**
 * 登录注册业务类
 * @author 刘鹏宇
 *
 */
public class LoginAndRegistService {

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public User loginValidate(String userName,String password){
		return User.dao.loginValidate(userName, password);
	}

	/**
	 * 判断用户是否已经存在
	 * @param userName
	 * @return
	 */
	public User registVaildate(String userName){
		return User.dao.userSearch(userName);
	}

	/**
	 * 注册
	 */
	public boolean saveUser(String userId,String userName,String password,int userType){
		return User.dao.saveUser(userId, userName, password, userType);
	}
	
	public boolean saveUserInfo(String userId,String name,String userAddr,String userTel,int userMoney){
		return UserInfo.dao.saveUserInfo(userId, name, userAddr, userTel, userMoney);
	}

	/**
	 * 对原始密码进行加密
	 * @param s
	 * @return
	 */
	public String md5(String s) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
