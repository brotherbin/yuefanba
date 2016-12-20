package com.yuefanba.domain;

import java.util.List;
import com.jfinal.plugin.activerecord.Record;

/*
 * 保存页面显示的订单数据字段
 */
public class OrderDetailWrap{
	String orderID;
	String shopId;	
	String userName;
	String orderAddr;
	String orderTel;
	String  orderTime;
	String shopTel;
	String shopName;	
	float totalPrice;
	float sendAdd;
	int orderState;
	int sendState;
	List<Record> list;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopTel() {
		return shopTel;
	}
	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}
	public float getSendAdd() {
		return sendAdd;
	}
	public void setSendAdd(float sendAdd) {
		this.sendAdd = sendAdd;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderAddr() {
		return orderAddr;
	}
	public void setOrderAddr(String orderAddr) {
		this.orderAddr = orderAddr;
	}
	public String getOrderTel() {
		return orderTel;
	}
	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public int getSendState() {
		return sendState;
	}
	public void setSendState(int sendState) {
		this.sendState = sendState;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<Record> getList() {
		return list;
	}
	public void setList(List<Record> list) {
		this.list = list;
	}	
}