package com.yuefanba.util;

/**
 * 生成数据库表的唯一ID
 * @author songdabin
 *
 */
public class IDGenerator {
	
	/**
	 * 生成用户ID
	 */
	public synchronized static String genUserId(){
		return "u" + System.currentTimeMillis();
	}
	
	/**
	 * 生成餐厅ID
	 */
	public synchronized static String genShopId(){
		return "s" + System.currentTimeMillis();
	}
	
	/**
	 * 生成菜品ID
	 */
	public synchronized static String genFoodId(){
		return "f" + System.currentTimeMillis();
	}
	
	/**
	 * 生成订单ID
	 * @return
	 */
	public synchronized static String genOrderId(){
		return "o" + System.currentTimeMillis();
	}
}
