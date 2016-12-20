package com.yuefanba.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 评价信息表模型
 * @author 833901
 */
public class EstimateInfo extends Model<EstimateInfo> {

	private static final long serialVersionUID = 7330621974927179886L;

	public static final EstimateInfo dao = new EstimateInfo();
	/**
	 * 插入评价内容
	 */
	public boolean saveEatimateContent(String userId,String orderId,String shopId,String estimateContent){
		EstimateInfo estimateInfo=new EstimateInfo();
		estimateInfo.set("userId", userId);
		estimateInfo.set("orderId", orderId);
		estimateInfo.set("shopId", shopId);
		estimateInfo.set("estimateInfo", estimateContent);	
		estimateInfo.set("estimateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return estimateInfo.save();	
	}
	
	/**
	 * 分页查询餐厅的所有评价信息
	 * @param pageNumber
	 * @param pageSize
	 * @param shopId
	 * @param userId
	 * @return
	 */
	public Page<Record> pageEstimationByShopId(int pageNumber, int pageSize, String shopId) {
		String select = "select u.username, e.estimateInfo, e.estimateTime";
		String sqlExceptSelect = "from estimateinfo e, user u where e.userId = u.userId and shopId = ? order by e.estimateTime desc";
		Page<Record> estimationPage = Db.paginate(pageNumber, pageSize, select, sqlExceptSelect, shopId);
		return estimationPage;
	}
}
