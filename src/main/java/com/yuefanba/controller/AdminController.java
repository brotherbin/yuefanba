package com.yuefanba.controller;


import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.interceptor.AdminAuthInterceptor;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.service.AdminService;

/**
 * 后台管理页面控制器
 * 负责餐厅管理、用户统计、订单统计、处理投诉等。。。
 * @author 曹诗维
 */
@Before(AdminAuthInterceptor.class)
public class AdminController extends Controller {

	AdminService admin = new AdminService();

	public void index(){
		renderJsp("Admin.jsp");
	}
	public void top10Shop(){
		renderJsp("TongJi.jsp");
	}
	/**
	 * 餐厅审核
	 */
	public void verifyShop(){
		renderJsp("VerifyShop.jsp");
	}
	/**
	 * 审核不通过，更新餐厅状态
	 */
	public void notApprove(){
		String shopId = getPara("shopId");
		admin.notApprove(shopId);
		renderJson();
	}
	/**
	 * 餐厅管理
	 */
	public void manage(){
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		int sortTag = getParaToInt("sortTag", 0);
		Page<Record> pageShop = admin.menageService(pageNum, pageSize, sortTag);
		renderJson(pageShop);
	}
	/**
	 * 停止营业
	 */
	public void cancelShop(){
		String shopId = getPara("shopId");
		admin.cancelService(shopId);
		renderJson();
	}
	/**
	 * 允许营业
	 */
	public void openShop(){
		String shopId = getPara("shopId");
		admin.openService(shopId);
		renderJson();
	}
	/**
	 * 餐厅审核通过
	 */
	public void approveShop(){
		String shopId = getPara("shopId");
		admin.approveService(shopId);
		renderJson();
	}
	/**
	 * 最受欢迎餐厅及菜品统计
	 */
	public void tongJi(){
		renderJson(admin.top10Service());
	}
	/**
	 * 更新最受欢迎餐厅及菜品列表
	 */
	public void setTableTop(){
		Boolean bl=admin.setTableTopService();
		renderJson(bl);
	}
	/**
	 * 后台餐厅搜索
	 */
	public void adminSearchByName(){
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		int sortTag = getParaToInt("sortTag", 0);
		String shopId=getPara("shopId");
		renderJson(admin.adminSearchByName(pageNum, pageSize, sortTag,shopId));
	}
	/**
	 * 待审核餐厅列表查询
	 */
	public void verifyShopData(){

		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		int sortTag = getParaToInt("sortTag", 0);
		Page<ShopInfo> shopinfo = admin.verifyShopService(pageNumber, pageSize, sortTag);
		renderJson(shopinfo);
	}

}
