package com.yuefanba.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 收藏信息表模型
 * 
 * @author 兰星珂
 */
public class Collection extends Model<Collection> {

	private static final long serialVersionUID = 5089711771739720451L;

	public static final Collection dao = new Collection();

	//分页显示已收藏餐厅
	public Page<Record> listByPage(int pageNum, int pageSize, String userId) {
		String sql = "select s.shopId, shopName, shopImg, startPrice, sendAdd ";
		String sqlExceptSelect = "from shopinfo s, collection c where c.shopId=s.shopId and c.userId=?";
		Page<Record> r = Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, userId);
		return r;
	}
	//判断餐厅是否收藏
	public boolean judgeCollection(String userId, String shopId){
		String sql = "select shopId,userId from collection where userId=? and shopId=?";
		int s = dao.find(sql, userId, shopId).size();
		return s > 0;
	}


	// 添加新的收藏餐厅
	public boolean addCollection(String shopId, String userId,String collectTime){
		Collection  collection=new Collection();
		boolean state = collection.set("shopId",shopId )
				.set("userId", userId)
				.set("foodId","foodId")
				.set("collectTime", collectTime).save();
		return state;
	}

	// 删除已经收藏餐厅
	public boolean deleteCollectionByShopId(String userId, String shopId) {
		String sql = "delete from collection where userId=? and shopId=?";
		return (1 == Db.update(sql, userId, shopId));

	}
}