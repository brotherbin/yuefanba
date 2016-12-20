package com.yuefanba.model;

import java.util.List;
import com.jfinal.plugin.activerecord.Model;
/**
 * 城市信息模型
 * @author 833901
 *
 */
public class City extends Model<City> {
	private static final long serialVersionUID = 1103591859003464681L;

	public static final City dao = new City();
	public List<City> cityList(String provinceCode) {
		String sql = "select id,code,name,provincecode from city where provincecode=?";		
		List<City> cityaddr=dao.find(sql,provinceCode);		
		return cityaddr;
	}

}
