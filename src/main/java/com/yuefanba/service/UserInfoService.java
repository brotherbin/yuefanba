package com.yuefanba.service;


import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yuefanba.model.AddressInfo;
import com.yuefanba.model.Area;
import com.yuefanba.model.City;
import com.yuefanba.model.Province;


/**
 * 个人信息管理业务类
 * @author 田粉粉
 *
 */

public class UserInfoService {
	
	
	public boolean saveAddress(String userId,String getName, String userPhone,String userAddress) {
		
		return AddressInfo.dao.saveAddress(userId,getName, userPhone,userAddress);
	}	
    public boolean updateAddress(String getName, String userPhone,String userAddress,String updateId) {
		
		return AddressInfo.dao.updateAddress(getName, userPhone,userAddress,updateId);
	}

	public Page<Record> listAddress(int pageNum, int pageSize, String userId) {
		// TODO Auto-generated method stub
		return AddressInfo.dao.addressListByPage(pageNum, pageSize, userId);		
	}

	public boolean deleteAddressById(String id) {
		return AddressInfo.dao.deleteAddressById(id);
	}

	public List<Province> provinceSelect() {
		// TODO Auto-generated method stub
		return Province.dao.provinceList();		
	}
	public List<City> citySelect(String provinceCode) {
		// TODO Auto-generated method stub
		return City.dao.cityList(provinceCode);	
	}
	public List<Area> areaSelect(String cityCode) {
		// TODO Auto-generated method stub
		return Area.dao.areaList(cityCode);	
	}
	
	/*public List<AddressInfo> updateAddressById(String updateId){
		return AddressInfo.dao.updateAddressById(updateId);
	}*/

	

	
}
