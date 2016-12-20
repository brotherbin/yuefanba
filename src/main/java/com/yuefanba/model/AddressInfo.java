package com.yuefanba.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/** 
 * 地址信息表的模型
 * @author 833901
 */
public class AddressInfo extends Model<AddressInfo> {

	private static final long serialVersionUID = -3482002679716151291L;	
	public static final AddressInfo dao = new AddressInfo();	
	/**
     *保存地址
	 */
	public  boolean saveAddress(String userId, String getName, String userPhone,String userAddress) {
		// TODO Auto-generated method stub
		//保存数据
		AddressInfo addrinfo=new AddressInfo();
		addrinfo.set("userId", userId);
		addrinfo.set("getName", getName);
		addrinfo.set("userPhone", userPhone);
		addrinfo.set("userAddress", userAddress);	
		addrinfo.set("addTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return addrinfo.save();	
	}
	
	/**
	 * 编辑地址
	 */
	public boolean updateAddress(String getName, String userPhone,String userAddress,String updateId){	
		 AddressInfo addrinfo=new AddressInfo();
		 return addrinfo.findById(updateId)
				.set("getName", getName)
				.set("userPhone", userPhone)
				.set("userAddress", userAddress)
				.set("addTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
				 .update();
			
		
	}
	/**
	 * 地址显示
	 */
	
	public Page<Record> addressListByPage(int pageNum, int pageSize, String userId) {
		String sql = "select id, userid, getname, userPhone, userAddress";
		String sqlExceptSelect = "from addressinfo where userId=?";		
		Page<Record> addre = Db.paginate(pageNum, pageSize, sql, sqlExceptSelect, userId);
		return addre;
	}
	/**
	 * 删除地址
	 */
	
	public boolean deleteAddressById(String id) {	
		String sql = "delete from addressinfo where id=?";
		return (1 == Db.update(sql, id));	
	}

	
	/**
	 * 根据用户ID分页查询地址信息
	 */
	public Page<AddressInfo> listAddrByUserId(int pageNumber, int pageSize, String userId) {
		String sql = "select id, userId, getName, userPhone, userAddress";
		String sqlExceptSelect = "from addressinfo where userId = ? order by addTime desc";
		return dao.paginate(pageNumber, pageSize, sql, sqlExceptSelect, userId);
	}
}
