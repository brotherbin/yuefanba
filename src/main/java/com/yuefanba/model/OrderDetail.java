package com.yuefanba.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 订单明细表模型
 * @author 833901
 *
 */
public class OrderDetail extends Model<OrderDetail> {

	private static final long serialVersionUID = 1250868847995314757L;

	public static final OrderDetail dao = new OrderDetail();

	public OrderDetail getOrderDetail(String orderId, String orderUserId) {
		// TODO Auto-generated method stub
		String sql ="select * from orderdetail where orderId=? and userId=? ";
		OrderDetail orderDetail=findFirst(sql, orderId,orderUserId);
		return orderDetail;
	}

	/**
	 * 查询当前用户的订单列表的订单细节信息
	 */
	public List<Record> selectOrderDetailByOrderId(String orderId){
		List<Record> orderDeTailList=new ArrayList<Record>();
		String sql = "select f.foodName,f.foodPrice,od.foodNum from orderdetail od,foodinfo f "
				+ "where od.foodId=f.foodId and orderId=?";
		orderDeTailList=Db.find(sql,orderId);
		return orderDeTailList;
	}	
	
	/**
	 * 删除已取消的订单细节信息
	 */
	public void deleteOrderDetail(String orderId){
		String sql="delete from orderdetail where orderId=?";
		Db.update(sql, orderId);		
	}
	
	public List<Record> getFoodBuyNum(){
		String sqlGetBuy = "select foodId , count(id) as saleNum from orderDetail group by foodId";
		List<Record> FoodBuyNum = Db.find(sqlGetBuy);
		return FoodBuyNum;
	}
	/**
	 * 订单详情查询
	 * @param orderId
	 * @return
	 */
	public List<Record> selectShopOrderDetailByOrderId(String orderId) {
		List<Record> orderDeTailList=new ArrayList<Record>();
		String sql = "select f.foodName,f.foodPrice,od.foodNum from orderdetail od,foodinfo f "
				+ "where od.foodId=f.foodId and od.orderId=?";
		orderDeTailList=Db.find(sql,orderId);
		return orderDeTailList;
	}
	
	/**
	 * 最受欢迎菜品统计
	 */
		public List<Record> top10() {
			String sqltop = "select f.foodName,f.foodId,f.foodPrice,sum(o.foodNum) as default2,f.foodImg from foodinfo f left join orderdetail o on f.foodId=o.foodId GROUP BY foodId ORDER BY default2 desc limit 10";
			List<Record> top10food = Db.find(sqltop);
		    return top10food;
		}
	/*
	 * 保存订单明细
	 */
	public boolean saveOrderDetail(OrderDetail model) {
		return new OrderDetail().setAttrs(model).save();
	}
}
