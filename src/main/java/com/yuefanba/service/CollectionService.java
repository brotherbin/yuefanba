package com.yuefanba.service;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.model.Collection;

/**
 * 收藏夹业务类
 * 
 * @author 兰星珂
 */
public class CollectionService {

	public Page<Record> listCollection(int pageNumber, int pageSize, String userId) {

		return Collection.dao.listByPage(pageNumber, pageSize, userId);
	}

	public boolean deleteCollectionByShopId(String userId, String shopId) {
		return Collection.dao.deleteCollectionByShopId(userId, shopId);
	}
	public boolean judgeCollection(String shopId, String userId){
		return Collection.dao.judgeCollection(shopId,userId);
	}

	public boolean addCollection(String shopId, String userId, String collectTime) {
		return Collection.dao.addCollection(shopId, userId, collectTime);
	}

}
