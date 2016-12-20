package com.yuefanba.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.config.Protocol;


/**
 * 餐厅信息表模型
 * @author 833901
 *
 */
public class ShopInfo extends Model<ShopInfo> {

	private static final long serialVersionUID = 8902163236244740836L;

	public static final ShopInfo dao = new ShopInfo();

	/*
	 * 分页查询餐厅信息
	 */
	public Page<ShopInfo> listByPage(int pageNumber, int pageSize, int sortTag){
		String select = "select shopId,shopName,shopImg,startPrice,sendAdd,sendTime,orderNum";
		String sqlExceptSelect = "from shopinfo where shopState=?";
		Page<ShopInfo> p = paginate(pageNumber, pageSize, select, sqlExceptSelect,Protocol.SHOP_STATUS_OPEN);
		return p;
	}

	/*
	 * 根据餐厅类型分页查询
	 */
	public Page<ShopInfo> listByTypeAndPage(int pageNumber, int pageSize, String shopType, int sortTag){
		String select = "select shopId,shopName,shopImg,startPrice,sendAdd,sendTime,orderNum";
		String sqlExceptSelect = "from shopinfo where shopState=? and shopType like '%" + shopType + "%'";
		Page<ShopInfo> p = paginate(pageNumber, pageSize, select, sqlExceptSelect, Protocol.SHOP_STATUS_OPEN);
		return p;
	}

	/*
	 * 根据关键字搜索餐厅或菜品
	 */
	public Page<ShopInfo> searchByShopName(int pageNumber, int pageSize, int sortTag, String searchKey) {
		String select = "select shopId,shopName,shopImg,startPrice,sendAdd,sendTime,orderNum";
		String sqlExceptSelect = "from shopinfo where shopState=? and (shopName like '%" + searchKey + "%' or shopId IN (select distinct shopId from foodinfo where foodName like '%" + searchKey + "%'))";
		Page<ShopInfo> p = paginate(pageNumber, pageSize, select, sqlExceptSelect, Protocol.SHOP_STATUS_OPEN);
		return p;
	}
	
	/*
	 * 查询热销餐厅
	 */
	public Page<ShopInfo> listHotShop(int pageNumber, int pageSize) {
		String select = "select shopId,shopName,shopImg,startPrice,sendAdd,sendTime,orderNum";
		String sqlExceptSelect = "from shopinfo where shopState = ? order by orderNum desc";
		Page<ShopInfo> p = paginate(pageNumber, pageSize, select, sqlExceptSelect, Protocol.SHOP_STATUS_OPEN);
		return p;
	}
	
	/**
	 * 餐厅管理
	 */
	public Page<Record> verifyByPage(int pageNumber, int pageSize, int sortTag){
	//	String select1 = "SELECT s.shopName, s.shopId,s.shopState, COUNT(o.shopId) as ordCount, COUNT(e.shopId) as estCount, COUNT(c.shopId) as comCount";
	//	String sqlExceptSelect1="FROM shopinfo s LEFT JOIN orderinfo o ON s.shopId = o.shopId LEFT JOIN estimateinfo e ON s.shopId = e.shopId LEFT JOIN complaintinfo c ON s.shopId = c.shopId where s.shopState=? or s.shopState=? GROUP BY s.shopId";
		String select1 ="SELECT a.shopName,a.shopId,a.shopState, sum(a.ordCount) as ordCount,sum(a.pjCount) as estCount,sum(a.tsCount) as comCount";
		String sqlExceptSelect1="from ( SELECT s.shopName, s.shopId,s.shopState, COUNT(o.shopId) ordCount,0 pjCount,0 tsCount FROM shopinfo s  left  JOIN orderinfo o ON s.shopId = o.shopId GROUP BY s.shopId UNION all SELECT s.shopName, s.shopId,s.shopState,0 ordCount, COUNT(e.shopId) pjCount,0 tsCount FROM shopinfo s  left JOIN estimateinfo e ON s.shopId = e.shopId GROUP BY s.shopId UNION all SELECT s.shopName, s.shopId,s.shopState,0 ordCount,0 pjCount, COUNT(c.shopId) tsCount FROM shopinfo s  left JOIN complaintinfo as c on s.shopId = c.shopId GROUP BY s.shopId ) a WHERE a.shopState IN(?,?) group by a.shopId";
		Page<Record> p1 = Db.paginate(pageNumber, pageSize, select1, sqlExceptSelect1,0,1);
        return p1;
	}

	/**
	 * 餐厅信息修改
	 * @param shopInfo
	 * @return
	 */
	public boolean shopInfoModify(ShopInfo shopInfo) {
		// TODO Auto-generated method stub
		String shopId=shopInfo.getStr("shopId");
		ShopInfo info = new ShopInfo();
		info =dao.findFirst("select * from shopinfo where shopId=?",shopId);
		if(info== null || info.equals("")){
			try {
				info=shopInfo;
				shopInfo.save();   //抛错是因为存在字段非空，
			} catch (Exception e) {
				System.out.println(e);	
			}
		}else{
			try {
				shopInfo.set("id", info.get("id"));
				info=shopInfo;
				info.update();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return true;
	}

	/**
	 * 停止营业
	 */
	public boolean setShopState(String shopId){
		String sql = "update shopinfo set shopState=? where shopId=?";
		Db.update(sql,Protocol.SHOP_STATUS_REJECT,shopId);
		return true;
	}
	/**
	 * 允许营业
	 */
	public boolean setShopStateOpen(String shopId){
		String sql = "update shopinfo set shopState=? where shopId=?";
		Db.update(sql,Protocol.SHOP_STATUS_APPROVE,shopId);
		return true;
	}
	/**
	 * 餐厅信息查询
	 * @param userId
	 * @return
	 */
	public ShopInfo shopInfoSelect(String userId) {
		// TODO Auto-generated method stub
		String sql="select * from shopinfo where userId=?";
		ShopInfo info=new ShopInfo();
		info= dao.findFirst(sql,userId);
		return info;
	}
	/**
	 * 餐厅信息指定ID查询
	 * @param userId
	 * @return
	 */
	public ShopInfo select(String userId) {
		// TODO Auto-generated method stub
		ShopInfo info= dao.findFirst("select * from shopinfo where userId=?",userId);

		return info;
	}


	public ShopInfo getShopByShopId(String shopId){
		String sql = "select * from shopinfo where shopId = ? ";
		return dao.findFirst(sql, shopId);
	}

	/**
	 * 待审核餐厅列表查询
	 */
	public Page<ShopInfo> verifyShop(int pageNumber, int pageSize, int sortTag) {
		String select = "select *";
		String sqlExceptSelect = "from shopinfo where shopState="+Protocol.SHOP_STATUS_VERIFY+"";
		Page<ShopInfo> p = paginate(pageNumber, pageSize, select, sqlExceptSelect);
		return p;
	}

	/**
	 * 审核不通过，更新餐厅状态
	 */
	public boolean setShopStateNotAppro(String shopId) {
		String sql = "update shopinfo set shopState=? where shopId=?";
		Db.update(sql,Protocol.SHOP_STATUS_NOTAPPROVE,shopId);
		return true;
        }

	/**
	 * 餐厅审核通过
	 */
	public boolean setApproveShopOpen(String shopId) {
		String sql = "update shopinfo set shopState=?,shopReplyTime=sysdate() where shopId=?";
		Db.update(sql,Protocol.SHOP_STATUS_APPROVE,shopId);
		return true;
	}
	/**
	 * 后台餐厅搜索
	 */
	public Page<Record> searchByPage(int pageNumber, int pageSize, int sortTag,String shopId) {
		String select2 = "SELECT a.shopName,a.shopId,a.shopState, sum(a.ordCount) as ordCount,sum(a.pjCount) as estCount,sum(a.tsCount) as comCount";
		String sqlExceptSelect2="from ( SELECT s.shopName, s.shopId,s.shopState, COUNT(o.shopId) ordCount,0 pjCount,0 tsCount FROM shopinfo s  left  JOIN orderinfo o ON s.shopId = o.shopId GROUP BY s.shopId UNION all SELECT s.shopName, s.shopId,s.shopState,0 ordCount, COUNT(e.shopId) pjCount,0 tsCount FROM shopinfo s  left JOIN estimateinfo e ON s.shopId = e.shopId GROUP BY s.shopId UNION all SELECT s.shopName, s.shopId,s.shopState,0 ordCount,0 pjCount, COUNT(c.shopId) tsCount FROM shopinfo s  left JOIN complaintinfo as c on s.shopId = c.shopId GROUP BY s.shopId ) a WHERE a.shopState IN(?,?)  and (a.shopId = ? or a.shopName like '%"+shopId+"%') group by a.shopId";
		//String sqlExceptSelect2="FROM shopinfo s LEFT JOIN orderinfo o ON s.shopId = o.shopId LEFT JOIN estimateinfo e ON s.shopId = e.shopId LEFT JOIN complaintinfo c ON s.shopId = c.shopId where s.shopState in(?,?) and (s.shopId = ? or s.shopName=?) GROUP BY s.shopId";
		Page<Record> p2 = Db.paginate(pageNumber, pageSize, select2, sqlExceptSelect2,0,1,shopId);
		return p2;
	}
	/**
	 * 商家信息查询
	 * @param shopId
	 * @return
	 */
	public ShopInfo shopInfoFind(String shopId) {
		String  sql  ="select * from shopinfo where shopId=?";
		ShopInfo  shopInfo=dao.findFirst(sql, shopId);
		return shopInfo;
	}

	/**
	 * 有用户下单时给餐厅订单量加1；
	 * @param shopId
	 * @return
	 */
	public boolean addShopOrderNum(String shopId){
		String sql = "update shopinfo set orderNum = orderNum + 1 where shopId = ?";
		return Db.update(sql, shopId) == 1;
	}

}
