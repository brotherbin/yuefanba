package com.yuefanba.service;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.model.Top;

/**
 * 主页业务处理类
 * @author 宋达彬
 *
 */
public class MainService {

	/*
	 * 分页查询餐厅信息
	 */
	public Page<ShopInfo> listShop(int pageNumber, int pageSize, String shopType, int sortTag) {
		Page<ShopInfo> page = null;
		if (StrKit.notBlank(shopType)) {
			page = ShopInfo.dao.listByTypeAndPage(pageNumber, pageSize, shopType, sortTag);
		} else {
			page = ShopInfo.dao.listByPage(pageNumber, pageSize, sortTag);
		}
		return page;
	}

	/*
	 * 分页搜索餐厅信息
	 */
	public Page<ShopInfo> search(int pageNumber, int pageSize, int sortTag, String searchKey ) {
		Page<ShopInfo> page = null;
		if (StrKit.isBlank(searchKey)) {
			page = new Page<ShopInfo>(null, 1, 0, 0, 0);
		} else {
			page = ShopInfo.dao.searchByShopName(pageNumber, pageSize, sortTag, searchKey);
		}
		return page;
	}

	/*
	 * 查询热销餐厅
	 */
	public Page<Top> listHotShop(int pageNumber, int pageSize) {
		// 从Top表里查询
		return Top.dao.listHotShop(pageNumber, pageSize);
	}
	
	/*
	 * 从餐厅信息表里根据销量查询热销餐厅
	 */
	public Page<ShopInfo> listHotShopFromShopInfo(int pageNumber, int pageSize) {
		return ShopInfo.dao.listHotShop(pageNumber, pageSize);
	}
}
