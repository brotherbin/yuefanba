package com.yuefanba.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
/**
 * 区域信息类模型
 * @author 833901
 *
 */
public class Area extends Model<Area> {
	private static final long serialVersionUID = 8342318234049012229L;

	public static final Area dao = new Area();

	public List<Area> areaList(String cityCode) {
		// TODO Auto-generated method stub
		String sql = "select id,code,name,citycode from area where citycode=?";		
		List<Area> areaaddr=dao.find(sql,cityCode);		
		return areaaddr;
	}

}
