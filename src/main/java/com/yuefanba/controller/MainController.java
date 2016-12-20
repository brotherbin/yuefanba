package com.yuefanba.controller;

import java.io.UnsupportedEncodingException;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.service.MainService;

/**
 * 主页控制器
 * @author 宋达彬
 */
public class MainController extends Controller {

	/*
	 * 主页业务操作类对象
	 */
	MainService service = new MainService();

	public void index(){
		redirect("/html/index.html");
	}

	/**
	 * 查询餐厅信息
	 */
	public void listShop(){
		// 起始下标、查询数量、排序规则
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		int sortTag = getParaToInt("sortTag", 0);
		String shopType = getPara("shopType", "");
		Page<ShopInfo> pageShop = service.listShop(pageNum, pageSize, shopType, sortTag);
		renderJson(pageShop);

	}

	/**
	 * 查询热销餐厅信息
	 */
	public void listHotShop() {
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		// 从hot表里查询
		//Page<Top> pageTop = service.listHotShop(pageNumber, pageSize);
		Page<ShopInfo> pageTop = service.listHotShopFromShopInfo(pageNumber, pageSize);
		renderJson(pageTop);
	}

	/*
	 * 进入搜索页面
	 */
	public void search() {
		String searchKey = "";
		try {
			searchKey = new String(getPara("keyword").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setAttr("searchKey", searchKey);
		render("search.jsp");
	}

	/*
	 * 搜索操作
	 */
	public void doSearch(){
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 20);
		int sortTag = getParaToInt("sortTag", 0);
		String searchKey = getPara("searchKey", "");
		Page<ShopInfo> page = service.search(pageNum, pageSize, sortTag, searchKey);
		renderJson(page);
	}
}
