package com.yuefanba.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import com.jfinal.plugin.activerecord.Record;

import com.jfinal.plugin.activerecord.Page;

/**
 * 热销餐厅信息表模型
 * 
 * @author 833901
 *
 */
public class Top extends Model<Top> {

	private static final long serialVersionUID = 7338280412206368599L;

	public static final Top dao = new Top();

	/**
	 * 更新最受欢迎餐厅及菜品列表
	 */
	public boolean setTop10() {
		List<Record> topshopA = OrderInfo.dao.TopShop();
		int i = topshopA.size();
		Top.dao.deleteAll();
		for (int j = 1; j <= i; j++) {
			new Top().set("shopId", topshopA.get(j - 1).get("shopId")).set("shopId", topshopA.get(j - 1).get("shopId"))
					.set("shopName", topshopA.get(j - 1).get("shopName"))
					.set("shopImg", topshopA.get(j - 1).get("shopImg"))
					.set("orderNum", topshopA.get(j - 1).get("orderNum"))
					.set("shopAddr", topshopA.get(j - 1).get("shopAddr"))
					.set("shopTel", topshopA.get(j - 1).get("shopTel"))
					.set("startPrice", topshopA.get(j - 1).get("startPrice"))
					.set("sendAdd", topshopA.get(j - 1).get("sendAdd"))
					.set("sendTime", topshopA.get(j - 1).get("sendTime"))
					.set("shopNotice", topshopA.get(j - 1).get("shopNotice")).save();
		}
		return true;
	}

	private void deleteAll() {
		String deleteAll = "delete from top";
		Db.update(deleteAll);
	}

	public Page<Top> listHotShop(int pageNumber, int pageSize) {
		String select = "select shopId, shopName, shopImg, orderNum, startPrice, sendAdd, sendTime";
		String sqlExceptSelect = "from top order by orderNum desc";
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}

}
