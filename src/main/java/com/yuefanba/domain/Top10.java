package com.yuefanba.domain;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class Top10 {
	List<Record> sum_user;
	List<Record> sum_shoper;
	List<Record> listFood;
	List<Record> listShop;

	public List<Record> getListShop() {
		return listShop;
	}
	public void setListShop(List<Record> listShop) {
		this.listShop = listShop;
	}
	public List<Record> getListFood() {
		return listFood;
	}
	public List<Record> getSum_user() {
		return sum_user;
	}
	public void setSum_user(List<Record> sum_user) {
		this.sum_user = sum_user;
	}
	public List<Record> getSum_shoper() {
		return sum_shoper;
	}
	public void setSum_shoper(List<Record> sum_shoper) {
		this.sum_shoper = sum_shoper;
	}
	public void setListFood(List<Record> listFood) {
		this.listFood = listFood;
	}
	

}
