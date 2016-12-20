/*
 * 定义存储购物车内容的全局变量
 */
var cart;

/*
 * 定义当前餐厅信息的全局变量
 */
var shop = {};

/*
 * 当前是否显示评价信息
 */
var isShowEstimation = false;

/*
 * 定义评价列表的分页信息
 */
var estimatePage = {
	pageNumber: 1,
	pageSize: 20,
	totalPage: 0,
	totalRow: 0
};

$(document).ready(function(){
	// 获取页面传递过来的shopId
	shop.shopId = getUrlParam("shopId");
	
	if (shop.shopId == undefined) {
		window.location.href = basePath + "html/index.html";
	}
	
	// 设置购物车位置
	setShopCarLoc();
	
	// 初始化餐厅信息
	initShopInfo();
	
});

$(window).resize(function(){
	setShopCarLoc();
});

/*
 * 监听页面滚动事件，并滚动加载餐厅信息
 */
$(window).scroll(function() {
	if ($(document).scrollTop() + $(window).height() == $(document).height()) {
		if (estimatePage.pageNumber < estimatePage.totalPage) {
			estimatePage.pageNumber ++;
			getEstimation();
		}
	}
});

/*
 * 设置购物车位置
 */
function setShopCarLoc(){
	$("#cart").css("left", $(".container").offset().left + 980 - 280);
}

function initShopInfo(){
	$.ajax({
		type: "POST",
		url: basePath + "shop/getShopInfo",
		dataType: "json",
		data: {
			shopId: shop.shopId
		},
		success: function(data) {
			shop = data;
			$("#locShopName").text(shop.shopName);
			$("#shopImg").attr("src", basePath + shop.shopImg);
			$("#shopName").text(shop.shopName);
			$("#startTime").text(shop.startTime);
			$("#endTime").text(shop.endTime);
			$("#shopAddr").text(shop.shopAddr);
			$("#sendTime").text(shop.sendTime);
			$("#shopStartPrice").text(shop.startPrice);
			$("#shopSendAdd").text(shop.sendAdd);
			$("#cartOrderBtn").attr("href", "confirmOrder.html?shopId=" + shop.shopId);
			
			// 初始化菜单数据
			initRestMenuData();
			
			// 初始化购物车
			initCart();
			
			// 初始化收藏信息
			initCollectionInfo();
		}
	});
}

/*
 * 初始化餐厅菜单数据
 */
function initRestMenuData(){
	$.ajax({
		type: "POST",
		url: basePath + "shop/listAllFood",
		dataType: "json",
		data: {
			shopId: shop.shopId
		},
		success: function(data) {
			$("#restMenu").empty();
			for (i = 0; i < data.length; i ++) {
				data[i].foodImg = basePath + data[i].foodImg;
			}
			$("#restMenuTmpl").tmpl(data).appendTo("#restMenu");
		}
	});
}

/*
 * 初始化购物车信息
 */
function initCart() {
	cart = new Cart(shop.shopId, shop.startPrice, shop.sendAdd);
	$.ajax({
		type: "POST",
		url: basePath + "shop/getCart",
		data: {
			shopId: shop.shopId
		},
		success: function(data) {
			if (data.foodList!=null && data.foodList.length != 0) {
				cart.foodList = data.foodList;
				cart.refresh();
			} 
		}
	});
}

/*
 * 点击添加到购物车按钮的点击事件
 */
function addToCart(me, foodPrice){
	// 数据操作
	var foodId = $(me).attr("data-foodId");
	var foodName = $(me).attr("data-foodName");
	var foodPrice = $(me).attr("data-foodPrice");
	var rs = cart.addFood(foodId, foodName, foodPrice);
	if (rs == -1) {
		new Toast("抱歉，一次最多只能添加20个菜").show();
	} else if (rs == -2) {
		new Toast("抱歉，一个菜品最多只能点20份").show();
	}
}
/*
 * 鼠标移动到购物车信息项上的响应事件
 */
function cartItemFocus(me){
	$(me).find("a.delete-icon").show();
}
/*
 * 鼠标移开购物车信息项上的响应事件
 */
function cartItemOut(me){
	$(me).find("a.delete-icon").hide();
}
/*
 * 点击减少菜品数量按钮
 */
function cutFoodCount(me){
	var foodId = $(me).attr("data-foodId");
	cart.reduceFoodCount(foodId);
}
/*
 * 点击增加菜品数量按钮
 */
function addFoodCount(me){
	var foodId = $(me).attr("data-foodId");
	var rs = cart.addFoodCount(foodId);
	if (!rs){
		new Toast("抱歉，一个菜品最多只能点10份").show();
	}
}
/*
 * 点击删除菜品按钮
 */
function removeCartItem(me){
	var foodId = $(me).attr("data-foodId");
	cart.removeFood(foodId);
}
/*
 * 清空购物车
 */
function clearCart(){
	cart.clear();
}

/*
 * 判断商铺是否被收藏
 */
function initCollectionInfo(){
	$.ajax({
		type: "POST",
		url: basePath + "collection/judgeCollection",
		dataType: "json",
		data: {
			shopId: shop.shopId
		},
		success: function(data) {
			if (data.status == "ok") {
				changeCollectionUI(1);
			} else {
				changeCollectionUI(0);
			}
		}
	});
}

/*
 * 收藏餐厅
 */
function collect(me) {
	var isCollect = $(me).attr("data-isCollected");
	var url;
	if (isCollect == "0") {
		url = basePath + "collection/addCollection";
	} else if (isCollect == "1"){
		url = basePath + "collection/deleteCollection";
	} else {
		new Toast("请先登录").show();
		return;
	}
	$.ajax({
		type: "POST",
		url: url,
		dataType: "json",
		data: {
			shopId: shop.shopId,
		},
		success: function(data){
			if (data.status == "ok") {
				if (isCollect == "0") {
					changeCollectionUI(1);
					new Toast("添加收藏成功").show();
				} else {
					changeCollectionUI(0);
					new Toast("取消收藏成功").show();
				}
			} else {
				new Toast("系统繁忙").show();
			}
		},
		error: function(){
			new Toast("请先登录").show();
		}
	});
}

function changeCollectionUI(tag){
	if (tag == 1) {
		$("#favoriteIcon").children("a").addClass("discollect").removeClass("collect").attr("data-isCollected", 1);
		$("#favoriteIcon").children("p").text("已收藏");
	} else {
		$("#favoriteIcon").children("a").removeClass("discollect").addClass("collect").attr("data-isCollected", 0);
		$("#favoriteIcon").children("p").text("未收藏");
	}
}

/*
 * 显示菜单
 */
function showFoodMenu(){
	$("#tabMenu").addClass("active");
	$("#tabEstimate").removeClass("active");
	$("#restMenu").show();
	$("#cart").show();
	$("#estimateList").hide();
	$("#emptyEstimationTip").hide();
}
/*
 * 显示评价列表
 */
function showEstimation(){
	$("#tabMenu").removeClass("active");
	$("#tabEstimate").addClass("active");
	$("#restMenu").hide();
	$("#estimateList").show();
	$("#cart").hide();
	if (estimatePage.totalRow < 1) {
		getEstimation();
	}
}

/*
 * 获取餐厅评价信息
 */
function getEstimation() {
	// 显示正在加载样式
	$("#loadingRestList").show();
	$.ajax({
		type: "POST",
		url: basePath + "shop/getEstimation",
		dataType: "json",
		data: {
			pageNumber: estimatePage.pageNumber,
			pageSize: estimatePage.pageSize,
			shopId: shop.shopId
		},
		success: function(data){
			estimatePage.pageNumber = data.pageNumber;
			estimatePage.pageSize = data.pageSize;
			estimatePage.totalPage = data.totalPage;
			estimatePage.totalRow = data.totalRow;
			if (data.list.length > 0) {
				// 渲染页面
				$("#estimateTmpl").tmpl(data.list).appendTo("#estimateList");
				// 隐藏正在加载样式
				$("#loadingRestList").hide();
				$("#estimateList").show();
			} else {
				$("#loadingRestList").hide();
				// 空内容提示
				$("#emptyEstimationTip").show();
			}
		},
		error: function(){
			
		}
	});
}

