package com.yuefanba.config;

import java.io.File;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.yuefanba.controller.AdminController;
import com.yuefanba.controller.CollectionController;
import com.yuefanba.controller.ComplaintController;
import com.yuefanba.controller.LoginController;
import com.yuefanba.controller.MainController;
import com.yuefanba.controller.OrderController;
import com.yuefanba.controller.OrderManagerController;
import com.yuefanba.controller.RegistController;
import com.yuefanba.controller.ShopController;
import com.yuefanba.controller.ShopManagerController;
import com.yuefanba.controller.UserInfoController;
import com.yuefanba.controller.ValueCardController;
import com.yuefanba.handler.MainHandler;
import com.yuefanba.model.AddressInfo;
import com.yuefanba.model.Area;
import com.yuefanba.model.City;
import com.yuefanba.model.Collection;
import com.yuefanba.model.ComplaintInfo;
import com.yuefanba.model.EstimateInfo;
import com.yuefanba.model.FoodInfo;
import com.yuefanba.model.OrderDetail;
import com.yuefanba.model.OrderInfo;
import com.yuefanba.model.Province;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.model.Top;
import com.yuefanba.model.User;
import com.yuefanba.model.UserInfo;
import com.yuefanba.model.ValueCard;
/**
 * JFinal基础配置类
 * @author songdabin
 *
 */
public class MainConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants constants) {
		loadPropertyFile("config.properties", "UTF-8");
		constants.setDevMode(getPropertyToBoolean("devMode", false));
		String webappPath = new File(PathKit.getWebRootPath()).getParent();
		String uploadFileSaveDir = webappPath + getProperty("uploadImgDir", "\\resource\\images");
		constants.setUploadedFileSaveDirectory(uploadFileSaveDir);
		constants.setMaxPostSize(getPropertyToInt("maxPostSize", 51200));
		constants.setBaseViewPath("view");
		constants.setViewType(ViewType.JSP);
		constants.setEncoding("UTF-8");
		constants.setError401View("/view/common/error401.jsp");
		constants.setError403View("/view/common/error403.jsp");
		constants.setError404View("/view/common/error404.jsp");
		constants.setError500View("/view/common/error500.jsp");
	}

	@Override
	public void configHandler(Handlers handlers) {
		handlers.add(new MainHandler());
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {

	}

	@Override
	public void configPlugin(Plugins plugins) {
		// 添加c3p0数据源插件
		C3p0Plugin dsPlugin = new C3p0Plugin(getProperty("mysqlUrl"), getProperty("mysqlUsername"), getProperty("mysqlPassword"));
		plugins.add(dsPlugin);
		// 添加jfinal的ActiveRecord插件
		ActiveRecordPlugin arPlugin = new ActiveRecordPlugin(dsPlugin);
		plugins.add(arPlugin);
		// 建立数据库表名到Model的映射关系
		arPlugin.addMapping("addressinfo", AddressInfo.class);
		arPlugin.addMapping("collection", Collection.class);
		arPlugin.addMapping("complaintinfo", ComplaintInfo.class);
		arPlugin.addMapping("estimateinfo", EstimateInfo.class);
		arPlugin.addMapping("foodinfo", FoodInfo.class);
		arPlugin.addMapping("orderdetail", OrderDetail.class);
		arPlugin.addMapping("orderinfo", OrderInfo.class);
		arPlugin.addMapping("shopinfo", ShopInfo.class);
		arPlugin.addMapping("user", User.class);
		arPlugin.addMapping("userinfo", UserInfo.class);
		arPlugin.addMapping("valuecard", ValueCard.class);
		arPlugin.addMapping("province", Province.class);
		arPlugin.addMapping("city", City.class);
		arPlugin.addMapping("area", Area.class);
		arPlugin.addMapping("top", Top.class);
	}

	@Override
	public void configRoute(Routes routes) {
		// 配置访问路由
		routes.add("/", MainController.class, "/");
		routes.add("/index", MainController.class, "/");


		routes.add("/login", LoginController.class, "/");
		routes.add("/regist", RegistController.class, "/");

		routes.add("/shop", ShopController.class, "/");
		routes.add("/order", OrderController.class, "/");
		routes.add("/collection", CollectionController.class, "/");
		routes.add("/userInfo", UserInfoController.class, "/");
		routes.add("/valuecard", ValueCardController.class, "/");

		routes.add("/admin", AdminController.class, "/");
		routes.add("/complaint", ComplaintController.class, "/");

		routes.add("/orderManager", OrderManagerController.class, "/");
		routes.add("/shopManager", ShopManagerController.class, "/");
	}

}
