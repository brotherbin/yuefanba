package com.yuefanba.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.config.Protocol;

/**
 * 菜品信息表模型
 * @author 833901
 *
 */
public class FoodInfo extends Model<FoodInfo> {

	private static final long serialVersionUID = -8952089739705513272L;

	public static final FoodInfo dao = new FoodInfo();

	/**
	 * 食品添加
	 * @param addFood
	 * @return
	 */
	public boolean addFood(FoodInfo addFood) {
		Boolean mySaveState = false;
		FoodInfo mySave = new FoodInfo();
		mySave=addFood;
		try {
			mySaveState=mySave.save();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return mySaveState;              
	}

	
	public List<FoodInfo> listFoodByShopId(String shopId) {
		String sql = "select * from foodinfo where shopId = ? and foodState = ?";
		return dao.find(sql, shopId, Protocol.FOOD_STATUS_OPEN);
	}

	/**
	 * 餐厅菜品信息分页查询
	 * @param shopId
	 * @param pageNum
	 * @param pageSize
	 * @param sortTag
	 * @return
	 */
	public Page<FoodInfo> foodListFind(String shopId, int pageNum, int pageSize, int sortTag) {
		Page<FoodInfo> foodList = null;
		String sql="select foodId,foodName,foodPrice,shopId,buyNum,foodImg,foodInfor,foodState  ";
		String sqlExceptSelect="from foodinfo where shopId=?";
		//String  mysql="select  *   from foodinfo where shopId=?";
		//List<FoodInfo>  myList= dao.find(mysql, shopId);
		foodList=dao.paginate(pageNum, pageSize, sql, sqlExceptSelect, shopId);
		return foodList;
	}

	/**
	 * 餐厅信息修改
	 * @param foodModify
	 * @return
	 */
	public boolean foodInfoModify(FoodInfo foodModify) {
		Boolean myModifyState = false;
		String foodId=foodModify.getStr("foodId");
		String sql="select * from foodinfo where foodId=?";
		FoodInfo myfood=findFirst(sql,foodId);
		foodModify.set("id", myfood.get("id"));
		myfood=foodModify;
		try {
			myModifyState=myfood.update();
		} catch (Exception e) {
			System.out.println(e);
		}
		return myModifyState;
	}
	/**
	 * 菜品停售
	 * @param foodId
	 * @return
	 */
	public Boolean foodStopSale(String foodId) {
		String  sql="select * from foodinfo where foodId=?";
		FoodInfo  info=findFirst(sql,foodId);
		
		if(info.get("foodState")==""||info.get("foodState")==null||
				info.get("foodState")==Integer.valueOf(Protocol.FOOD_STATUS_OPEN)){
			info.set("foodState", Protocol.FOOD_STATUS_CLOSED);
		}else{
			info.set("foodState", Protocol.FOOD_STATUS_OPEN);
		}
		
		Boolean  stopState= info.update();
		return stopState;
	}
	/**
	 * 菜品删除
	 * @param foodId
	 * @return
	 */
	public boolean foodInfoDelet(String foodId) {
		String  sql="select * from foodinfo where foodId=?";
		FoodInfo  info=findFirst(sql,foodId);
		Integer keyId=info.getInt("id");
		Boolean deletState= Db.deleteById("foodinfo", keyId);
		return  deletState;

	}
	/**
	 * 更新最受欢迎餐厅及菜品列表
	 */
	public boolean setBuyFoodNum(){
		List<Record> FoodBuyNum = OrderDetail.dao.getFoodBuyNum();
		int i = FoodBuyNum.size();
		String sqlUpdate="update foodinfo set buyNum = ? where foodId = ?";
		for(int j =0;j<i;j++){
			Record r = FoodBuyNum.get(j);
			String foodId = FoodBuyNum.get(j).get("foodId");
			Db.update(sqlUpdate, r.get("saleNum"), foodId);
		}
		return true;
	}
	
	/**
	 * 用户下单后更新食品被购买数量
	 * @param foodId
	 * @param addCount 增量
	 * @return
	 */
	public boolean updateFoodBuyNum(String foodId, int addCount) {
		String sql = "update foodinfo set buyNum = buyNum + ? where foodId = ? ";
		return Db.update(sql, addCount, foodId)==1;
	}


	/**
	 * 食品信息查询
	 * @param foodId
	 * @return
	 */
	public FoodInfo foodInfoFind(String foodId) {
		String sql="select * from foodinfo where foodId=?";
		FoodInfo info=dao.findFirst(sql,foodId);
		return info;
	}


    
	
	/**
	 * 系统用户总数
	 */
	public List<Record> sum_user(){
		String sum_usersql = "select count(id) as default2 from user";
		return Db.find(sum_usersql);
	}
	/**
	 * 系统餐厅总数
	 */
	public List<Record> sum_shoper() {
		String sum_shopsql = "select count(*) as default1 from user where userType="+Protocol.USER_TYPE_OF_SHOP+"";
		return Db.find(sum_shopsql);
	}


	


}
