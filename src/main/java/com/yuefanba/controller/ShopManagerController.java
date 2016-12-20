package com.yuefanba.controller;

import java.io.File;
import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.yuefanba.config.Protocol;
import com.yuefanba.domain.OrderDetailWrap;
import com.yuefanba.interceptor.ShopAuthInterceptor;
import com.yuefanba.model.FoodInfo;
import com.yuefanba.model.ShopInfo;
import com.yuefanba.model.User;
import com.yuefanba.service.ShopManagerService;
import com.yuefanba.util.IDGenerator;


/**
 * 餐厅信息维护和餐厅订单管理控制器
 * @author ChiJiaWen
 */
@Before(ShopAuthInterceptor.class)
public class ShopManagerController extends Controller {

	/*
	 * 餐厅信息维护业务操作类对象
	 */
	ShopManagerService service = new ShopManagerService();

	public void index(){
		renderJsp("shopInfo.jsp");
	}
	public void shopMessage(){
		renderJsp("shopMessage.jsp");
	}
	public void foodManager(){
		renderJsp("foodManager.jsp");
	}
	public void incomeQuery(){
		renderJsp("incomeQuery.jsp");
	}
	public void newFoodAdd(){
		setAttr("foodId", getPara());
		renderJsp("newFoodAdd.jsp");
	}

	/**
	 * 订单信息查询(全部)
	 */
	public void listOrder(){
		User user=getSessionAttr("loginUser");
		String  userId=user.getStr("userId");
		Integer status=getParaToInt("status");
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);		
		int sortTag = getParaToInt("sortTag", 0);
		Page<OrderDetailWrap> orderList=service.getOrderList(userId,status,pageNum,pageSize,sortTag);
		renderJson(orderList);
	}
	/**
	 * 商家訂單  确认
	 */
	public void orderSure(){
		String orderId=getPara("orderId");
		String status=getPara("status");
		Boolean orderSure=service.orderSure(orderId,status);
		renderJson(orderSure);
	}
	/**
	 * 菜品信息查询
	 */
	public void foodListFind(){

		User user = getSessionAttr("loginUser");
		String   userId=user.getStr("userId");
		int pageNum = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);		
		int sortTag = getParaToInt("sortTag", 0);
		Page<FoodInfo> foodList=service.foodListFind(userId,pageNum,pageSize,sortTag);
		renderJson(foodList);
	}
	/**
	 * 菜品信息添加，返回空
	 */
	public void foodAddModify(){
		FoodInfo  info=new FoodInfo();
		String foodId=getPara("foodId");
		if(foodId == null || foodId.equals("")){
			renderJson(info);
		}else{
			info=service.foodInfoFind(foodId);
			renderJson(info);	
		}
	}
	/**
	 * 菜品添加     ，修改
	 */
	public void modifyAndAdd(){
		FoodInfo addFood=new FoodInfo();
		UploadFile  file = null;
		file = getFile();
		User user = getSessionAttr("loginUser");
		String   userId=user.getStr("userId");
		ShopInfo shopInfo=service.shopInfoSelect(userId);
		String newFoodId=shopInfo.getStr("shopId");
		String   foodName= getPara("foodName");
		String   foodPrice= getPara("foodPrice");
		Integer   buyNum= getParaToInt("buyNum");
		String   foodInfo= getPara("foodInfor");
		Integer  foodState= getParaToInt("foodState");
		Integer foodNum= getParaToInt("foodNum");
		String 	foodId=getPara("foodId");
		addFood.set("foodName", foodName);
		addFood.set("foodPrice", foodPrice);
		addFood.set("buyNum", buyNum);
		addFood.set("foodInfor", foodInfo);
		addFood.set("foodState", foodState);
		addFood.set("foodNum", foodNum);
		addFood.set("shopId", newFoodId);
		if(buyNum==null||buyNum.equals("")){
			addFood.set("buyNum", 0);
		}else{
			addFood.set("buyNum", buyNum);
		}
		//判断是否上传图片
		if(file==null||file.equals("")){
			if(foodId== null || foodId.equals("")){
				addFood.set("foodImg", "");
				addFood.set("foodState", Protocol.FOOD_STATUS_OPEN);
				addFood.set("foodId", IDGenerator.genFoodId());//如果为食品增加则赋予其ID
				service.addFood(addFood);
			}else{
				FoodInfo info=service.foodInfoFind(foodId);
				addFood.set("foodImg", info.getStr("foodImg"));
				addFood.set("foodId", foodId);
				service.foodInfoModify(addFood);
			}

		}else{
			String  path=file.getSaveDirectory();
			long  newFileName=System.currentTimeMillis();
			Boolean   imgName=file.getFile().renameTo( new File(path+newFileName+"."+"jpg"));
			if(foodId== null || foodId.equals("")){
				if(imgName){
					addFood.set("foodState", Protocol.FOOD_STATUS_OPEN);
					addFood.set("foodImg", "../resource/images/"+newFileName+"."+"jpg");
					addFood.set("foodId", IDGenerator.genFoodId());//如果为食品增加则赋予其ID
					service.addFood(addFood);
				}
			}else{
				FoodInfo info=service.foodInfoFind(foodId);
				String addr=info.getStr("foodImg");
				File   addrFile= new  File(addr);
				try {
					addrFile.delete();
				} catch (Exception e) {
					System.out.println(e);
				}
				addFood.set("foodImg", "../resource/images/"+newFileName+"."+"jpg");
				addFood.set("foodId", foodId);
				service.foodInfoModify(addFood);

			}


		}
		redirect("/html/shopFoodManage.html");
	}
	/**
	 * 菜品信息删除
	 */
	public void foodStopSale(){
		String foodId=getPara("foodId");
		Boolean stopState=service.foodStopSale(foodId);

		renderJson(stopState);
		//redirect("/foodManager.html");
	}
	/**
	 * 菜品信息删除
	 */
	public void foodInfoDelet(){
		String foodId=getPara("foodId");
		Boolean deletState=service.foodInfoDelet(foodId);

		renderJson(deletState);
		//redirect("/foodManager.html");
	}
	/**
	 * 餐厅基本信息查询
	 */
	public void shopInfoSelect(){
		User user = getSessionAttr("loginUser");
		String   userId=user.getStr("userId");
		ShopInfo  shopInfo =service.shopInfoSelect(userId);
		renderJson(shopInfo);
	}
	/**
	 * 餐厅信息修改
	 */
	public void shopInfoModify(){
		User user = getSessionAttr("loginUser");
		String   userId=user.getStr("userId");
		UploadFile   shopImg= getFile("shopImgFile");
		String  shopId  = getPara("shopId");
		String   hostCard= getPara("shoperIDNumber");
		String   hostName= getPara("shoperName");
		String   shopName= getPara("shopName");
		String   shopAddr= getPara("shopAddr");
		String   shopTel= getPara("shopTel");
		Integer   sendTime= getParaToInt("sendTime");
		String   startPrice= getPara("startPrice");
		Long   sendAdd= getParaToLong("sendAdd");
		String   shopType= getPara("shopType");
		String   startTime= getPara("startTime");
		String    endTime= getPara("endTime");
		Long   incomeMoney= getParaToLong("incomeMoney");
		Integer   busState= getParaToInt("busState");
		Date date=new Date();
		Date   shopApplyTime= date;
		//Date   shopReplyTime= getParaToDate("shopReplyTime");
		String   shopNotice= getPara("shopNotice");
		ShopInfo shopInfo =new ShopInfo();		
		shopInfo.set("userId", userId);
		shopInfo.set("hostCard", hostCard);
		shopInfo.set("hostName", hostName);
		shopInfo.set("shopName", shopName);
		shopInfo.set("shopAddr", shopAddr);
		shopInfo.set("shopTel", shopTel);
		shopInfo.set("startPrice", startPrice);
		shopInfo.set("sendAdd", sendAdd);
		shopInfo.set("shopType", shopType);
		shopInfo.set("startTime", startTime);
		shopInfo.set("endTime", endTime);
		shopInfo.set("incomeMoney", incomeMoney);
		shopInfo.set("busState", busState);
		shopInfo.set("shopState", Protocol.SHOP_STATUS_VERIFY);
		shopInfo.set("shopApplyTime", shopApplyTime);
		//shopInfo.set("shopReplyTime", shopReplyTime);
		shopInfo.set("shopNotice", shopNotice);
		shopInfo.set("sendTime", sendTime);
		if(shopImg==null||shopImg.equals("")){
			if(shopId== null || shopId.equals("")){
				shopInfo.set("orderNum", 0);
				shopInfo.set("shopId",IDGenerator.genShopId());
				shopInfo.set("shopImg","");
			}else{
				ShopInfo   theShopInfo=service.shopInfoFind(shopId);
				shopInfo.set("shopImg",theShopInfo.getStr("shopImg"));
				shopInfo.set("shopId", shopId);
			}

		}else{
			String  path=shopImg.getSaveDirectory();
			long  fileName=System.currentTimeMillis();
			Boolean   imgName=shopImg.getFile().renameTo( new File(path+fileName+"."+"jpg"));
			if(shopId== null || shopId.equals("")){
				shopInfo.set("orderNum", 0);
				if(imgName){
					shopInfo.set("shopImg", "../resource/images/"+fileName+"."+"jpg");
				}else{
					shopInfo.set("shopImg", "../resource/images/"+shopImg.getFileName());
				}
				shopInfo.set("shopId",IDGenerator.genShopId());
			}else{
				if(imgName){
					ShopInfo   theShopInfo=service.shopInfoFind(shopId);
					String addr=theShopInfo.getStr("shopImg");
					File   addrFile= new  File(addr);
					try {
						addrFile.delete();
					} catch (Exception e) {
						System.out.println(e);
					}
					shopInfo.set("shopImg", "../resource/images/"+fileName+"."+"jpg");
				}else{
					shopInfo.set("shopImg", "../resource/images/"+shopImg.getFileName());
				}
				shopInfo.set("shopId", shopId);
			}			
		}
		
		service.shopInfoModify(shopInfo);	
		redirect("/html/shopInfoManage.html");
	}
}
