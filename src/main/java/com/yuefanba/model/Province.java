package com.yuefanba.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 省份信息表模型
 * @author 833901
 *
 */
public class Province extends Model<Province> {

	private static final long serialVersionUID = -4789564264737877073L;
	
	public static final Province dao = new Province();
	
	public List<Province> provinceList() {
		String sql = "select id,code,name from province";		
		List<Province> addr=dao.find(sql);		
		return addr;
	}
}
