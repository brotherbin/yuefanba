package com.yuefanba.service;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.domain.Top10;
import com.yuefanba.model.FoodInfo;
import com.yuefanba.model.OrderDetail;
import com.yuefanba.model.OrderInfo;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.model.Top;

/**
 * 后台业务类
 * @author 曹诗维
 */
public class AdminService {


	/**
	 * 餐厅管理
	 */
	public Page<Record> menageService(int pageNumber, int pageSize, int sortTag){
		return ShopInfo.dao.verifyByPage(pageNumber, pageSize, sortTag);
	}
	/**
	 * 停止营业
	 */
	public void cancelService(String shopId){
		ShopInfo.dao.setShopState(shopId);
	}
	/**
	 * 允许营业
	 */
	public void openService(String shopId){
		ShopInfo.dao.setShopStateOpen(shopId);
	}
	/**
	 * 最受欢迎餐厅及菜品统计
	 */
	public Top10 top10Service() {
		Top10 top10 = new Top10();
		top10.setSum_user(FoodInfo.dao.sum_user());
		top10.setListShop(OrderInfo.dao.top10Shop());
		top10.setSum_shoper(FoodInfo.dao.sum_shoper());
		top10.setListFood(OrderDetail.dao.top10());
		return	top10;

	}
	/**
	 * 待审核餐厅列表查询
	 */
	public Page<ShopInfo> verifyShopService(int pageNumber, int pageSize, int sortTag){
		return ShopInfo.dao.verifyShop(pageNumber, pageSize, sortTag);
	}
	/**
	 * 审核不通过，更新餐厅状态
	 */
	public void notApprove(String shopId) {
		ShopInfo.dao.setShopStateNotAppro(shopId);
	}
	/**
	 * 餐厅审核通过
	 */
	public void approveService(String shopId) {
		ShopInfo.dao.setApproveShopOpen(shopId);
	}
	/**
	 * 更新最受欢迎餐厅及菜品列表
	 */
	public Boolean setTableTopService() {
		return Top.dao.setTop10()&&FoodInfo.dao.setBuyFoodNum();
	}
	/**
	 * 后台餐厅搜索
	 */
	public Page<Record> adminSearchByName(int pageNumber, int pageSize, int sortTag,String shopId){
		return ShopInfo.dao.searchByPage(pageNumber, pageSize, sortTag,shopId);
	}
}
