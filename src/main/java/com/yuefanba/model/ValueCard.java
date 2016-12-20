package com.yuefanba.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.config.Protocol;

/**
 * 储值卡信息表模型
 * @author 833901
 *
 */
public class ValueCard extends Model<ValueCard> {

	private static final long serialVersionUID = -6390736317172456805L;

	public static final ValueCard dao = new ValueCard();
	
	/**
	 * 查询用户余额
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Page<Record> ValueCardSelect(int pageNum, int pageSize, String userId){
		String sql="select userName,userMoney";
		String sqlExceptSelect="from user as u,userinfo as ui where u.userId=ui.userId and ui.userId=?";
		Page<Record> vs=Db.paginate(pageNum, pageSize, sql, sqlExceptSelect,userId);
		return vs;
	}
	
	
	public Page<Record> selectValueList(int pageNum, int pageSize, String userId){
		String sql="select userId,addMoney,addTime";
		String sqlExceptSelect="from valuecard where userId=? order by addTime desc";
		Page<Record> sv=Db.paginate(pageNum, pageSize, sql, sqlExceptSelect,userId);
		return sv;
	}
	
	
	/**
	 * 用户信息表充值
	 * @param userMoney
	 * @param updateUserId
	 * @return
	 */
	public boolean updateUserMoney(int userMoney,String updateUserId){
		
		String sql="update userinfo set userMoney=userMoney+? where userId=?";
		return (1== Db.update(sql, userMoney,updateUserId));
		
	}	
	/**
	 * 储值卡中添加记录
	 * @param userId
	 * @param addMoney
	 * @return
	 */
	public boolean addValue(String userId,int addMoney){
		ValueCard valuecard=new ValueCard();
		valuecard.set("userId", userId);
		valuecard.set("addMoney", addMoney);
		valuecard.set("addTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return valuecard.save();
	}
	/**
	 * 订单采用储值卡消费付款
	 * @param userId
	 * @param addMoney
	 * @return
	 */
	public boolean valueCardUpdate(String orderId,String userId){
		ValueCard valuecard=new ValueCard();
		OrderInfo  order= new  OrderInfo();
		String sql="select *  from  orderinfo  where   orderId=? and orderState=?";
		order= OrderInfo.dao.findFirst(sql, orderId,Protocol.ORDER_STATUS_PAID);
		float value = order.getFloat("totalPrice");		
		valuecard.set("userId", userId);
		valuecard.set("addMoney", -value);
		valuecard.set("addTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		UserInfo user = UserInfo.dao.getUserInfo(userId);
		if (user!=null) {
			float newMoney = user.getFloat("userMoney") - value;
			user.set("userMoney", newMoney).update();
		}
		return valuecard.save();
	}
}
	
	