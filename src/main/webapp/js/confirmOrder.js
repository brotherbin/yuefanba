// 定义当前餐厅信息对象
var shop = {};
// 定义购物车对象
var cart;
$(document).ready(function(){
	
	shop.shopId = getUrlParam("shopId");
	
	if (shop.shopId == undefined) {
		window.location.href = basePath + "html/index.html";
	}
	
	checkLogin();
});



/**
 * 判断是否登录
 */
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin) {
				// 加载订单所在餐厅的信息
				initShopInfo();
				// 加载订单信息
				initOrderInfo();
				// 加载地址信息
				initAddrInfo();
			} else {
				var param = encodeURIComponent("initTip=loginFirst&redirectUrl=" + basePath + "html/confirmOrder.html?shopId=" + shop.shopId);
				window.location.href = basePath + "html/login.html?" + param;
			}
		}
	});
}

function initShopInfo(){
	$.ajax({
		type: "POST",
		url: basePath + "shop/getShopInfo",
		dataType: "json",
		data: {
			shopId: shop.shopId,
		},
		success: function(data) {
			shop = data;
			$("#shopHref").attr("href", "shop.html?shopId=" + shop.shopId );
			$("#shopName").text(shop.shopName);
			$("#cartBackShop").attr("href", "shop.html?shopId=" + shop.shopId)
		}
	});
}

/*
 * 加载订单信息
 */
function initOrderInfo() {
	cart = new Cart(shop.shopId);
	$.ajax({
		type: "POST",
		url: basePath + "shop/getCart",
		data: {
			shopId: shop.shopId
		},
		success: function(data) {
			if (data.shopId != undefined) {
				cart.shopId = data.shopId;  // 商家id
				cart.foodList = data.foodList;	// 菜品信息列表
				cart.startPrice = data.startPrice;  // 起送价
				cart.incomePrice = data.incomePrice;  // 配送价
				cart.amount = data.amount;  // 购物车总价
				$("#orderItemTmpl").tmpl(data.foodList).appendTo("#orderItemList");
				$("#orderItemList").append($("#orderSendAddTmpl").html());
				$("#sendAddPrice").text(cart.incomePrice);
				$("#amountValue").text(data.amount);
			} else {
				window.location.href = basePath + "html/shop.html?shopId=" + shop.shopId;
			}
		}
	});
}

/*
 * 加载用户常用地址信息
 */
function initAddrInfo(){
	$.ajax({
		type: "POST",
		url: basePath + "shop/getAddress",
		dataType: "json",
		data: {},
		success: function(data){
			if (data.length > 0) {
				$("#addrList").empty();
				$("#addrTmpl").tmpl(data).appendTo("#addrList");
				$("#addrPageInput").hide();
				$("#addrList").show();
			}
		}
	});
}

/*
 * 打开新增地址弹出框
 */
function popAddrInput(){
	$("#addrName2").val("");
	$("#addrTel2").val("");
	$("#addrInfo2").val("");
	$("#addrInputPop").show();
}

/*
 * 关闭新增地址弹出框
 */
function closeAddrInput(){
	$("#addrName2").val("");
	$("#addrName2").siblings("p.tip-msg").text("");
	$("#addrTel2").val("");
	$("#addrTel2").siblings("p.tip-msg").text("");
	$("#addrInfo2").val("");
	$("#addrInfo2").siblings("p.tip-msg").text("");
	$("#addrInputPop").hide();
}

/*
 * 选择地址
 */
function selectAddr(me){
	var addrId = $(me).attr("data-addrId");
	// 选择地址后更改样式
	$(me).siblings(".addr-selected").children(".addr-selected-icon").hide();
	$(me).siblings(".addr-selected").removeClass("addr-selected");
	$(me).addClass("addr-selected");
	$(me).children(".addr-selected-icon").show();
	
	// 修改订单数据
	cart.getName = $(me).children(".addr-name").text();
	cart.orderTel = $(me).children(".addr-tel").text();
	cart.orderAddr = $(me).children(".addr-info").text();
	
	setConfirmBtnStatus();
}

/*
 * 保存地址（在页面中保存）
 */
function saveAddr1(){
	var isOk1 = isOk2 = isOk3 = false;
	var addrName1 = $.trim($("#addrName1").val());
	var addrTel1 = $.trim($("#addrTel1").val());
	var addrInfo1 = $.trim($("#addrInfo1").val());
	if (addrName1==null || addrName1=="") {
		$("#addrName1").siblings("p.tip-msg").text("*姓名不能为空");
	} else {
		$("#addrName1").siblings("p.tip-msg").text("");
		isOk1 = true;
	}
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
	if (!re.test(addrTel1)) {
		$("#addrTel1").siblings("p.tip-msg").text("*请填写正确电话号码");
	} else {
		$("#addrTel1").siblings("p.tip-msg").text("");
		isOk2 = true;
	}
	if (addrInfo1==null || addrInfo1=="") {
		$("#addrInfo1").siblings("p.tip-msg").text("*地址不能为空");
	} else {
		$("#addrInfo1").siblings("p.tip-msg").text("");
		isOk3 = true;
	}
	if (isOk1 && isOk2 && isOk3) {
		saveAddr(addrName1, addrTel1, addrInfo1);
	}
}

/*
 * 保存地址（在弹出框中保存）
 */
function saveAddr2(){
	var isOk1 = isOk2 = isOk3 = false;
	var addrName2 = $("#addrName2").val().trim();
	var addrTel2 = $("#addrTel2").val().trim();
	var addrInfo2 = $("#addrInfo2").val().trim();
	if (addrName2==null || addrName2=="") {
		$("#addrName2").siblings("p.tip-msg").text("*姓名不能为空");
	} else {
		$("#addrName2").siblings("p.tip-msg").text("");
		isOk1 = true;
	}
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
	if (!re.test(addrTel2)) {
		$("#addrTel2").siblings("p.tip-msg").text("*请填写正确电话号码");
	} else {
		$("#addrTel2").siblings("p.tip-msg").text("");
		isOk2 = true;
	}
	if (addrInfo2==null || addrInfo2=="") {
		$("#addrInfo2").siblings("p.tip-msg").text("*地址不能为空");
	} else {
		$("#addrInfo2").siblings("p.tip-msg").text("");
		isOk3 = true;
	}
	if (isOk1 && isOk2 && isOk3) {
		saveAddr(addrName2, addrTel2, addrInfo2);
		closeAddrInput();
	}
}

/*
 * 将地址保存至服务端
 */
function saveAddr(name, tel, addr) {
	$.ajax({
		type: "POST",
		url: basePath + "userInfo/save",
		dataType: "json",
		data: {
			getName: name,
			userPhone: tel,
			userAddress: addr
		},
		success: function(data) {
			if (data.status == "ok") {
				initAddrInfo();
			}
		}
	});
}

/*
 * 选择支付方式
 */
function selectPayWay(me){
	$(me).siblings().removeClass("selected-item");
	$(me).addClass("selected-item");
	cart.payWay = parseInt($(me).attr("data-payway"));
}

/*
 * 设置“确认下单按钮状态”
 */
function setConfirmBtnStatus() {
	if (cart.validate()) {
		$("#confirmOrderBtn").removeClass("disabled-btn");
	} else {
		$("#confirmOrderBtn").addClass("disabled-btn");
	}
}

/*
 * 提交订单
 */
function confirmOrder() {
	if (cart.validate()== false) {
		return;
	}
	$("#confirmOrderBtn").addClass("disabled-btn").text("正在提交");
	$.ajax({
		type: "POST",
		url: basePath + "shop/saveOrder",
		dataType: "json",
		data: {
			orderJsonStr: JSON.stringify(cart)
		},
		success: function(data) {
			if (data.orderId != null && data.orderTel != null) {
				var tParam = encodeURIComponent("orderId=" + data.orderId + "&orderTel=" + data.orderTel)
				window.location.href= basePath + "html/orderSuccess.html?" + tParam;
			} else {
				window.location.href= basePath;
			}
		}
	});
}