package com.yuefanba.config;

public class Protocol {
	/*
	 * 用户类型---游客
	 */
	public static final int USER_TYPE_OF_VISITOR = 4;
	/*
	 * 用户类型---注册用户
	 */
	public static final int USER_TYPE_OF_USER = 0;
	/*
	 * 用户类型---注册商家
	 */
	public static final int USER_TYPE_OF_SHOP = 1;
	/*
	 * 用户类型---系统管理员
	 */
	public static final int USER_TYPE_OF_ADMIN = 2;

	/*
	 * 餐厅营业状态---营业中
	 */
	public static final int SHOP_STATUS_OPEN = 1;
	/*
	 * 餐厅营业状态---关闭
	 */
	public static final int SHOP_STATUS_CLOSED = 0;

	/*
	 * 菜品状态---在售
	 */
	public static final int FOOD_STATUS_OPEN = 1;
	/*
	 * 菜品状态---售空
	 */
	public static final int FOOD_STATUS_CLOSED = 0;
	/*
	 * 订单状态---最新订单（等待商家确认）
	 */
	public static final int ORDER_STATUS_NEWORDER = 0;

	/*
	 * 配送状态---未送餐
	 */
	public static final int ORDER_STATUS_UNSENT = 1;
	/*
	 * 配送状态---送餐中
	 */
	public static final int ORDER_STATUS_SENTING = 2;
	/*
	 * 配送状态---已完成
	 */
	public static final int ORDER_STATUS_FINISH = 3;
	/*
	 * 订单状态---取消
	 */
	public static final int ORDER_STATUS_CANCEL = 4;

	/*
	 * 下单状态---未付款（货到付款）
	 */
	public static final int ORDER_STATUS_UNPAID = 1;
	/*
	 * 下单状态---付款（储值卡付款）
	 */
	public static final int ORDER_STATUS_PAID = 2;
	/*
	 * 餐厅状态---允许营业
	 */
	public static final int SHOP_STATUS_APPROVE = 1;
	/*
	 * 餐厅状态---未通过审核或强制关闭
	 */
	public static final int SHOP_STATUS_REJECT = 0;
	/*
	 * 餐厅状态---审核中..
	 */
	public static final int SHOP_STATUS_VERIFY = 2;
	/*
	 * 餐厅状态---审核不通过，驳回..
	 */
	public static final int SHOP_STATUS_NOTAPPROVE = 3;

}
