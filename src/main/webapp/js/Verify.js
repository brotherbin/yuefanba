/**
 * 
 */
$(document).ready(function(){
	checkLogin();
	
});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			initOrderData(++ page.pageNum);
		}
	}
});
var page = {
	pageNum: 1,
	pageSize: 20,
	totalPage: 0,
	totalRow: 0
};
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
				initOrderData(1);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/verifyShop.html";
			}
		}
	});
}

function initOrderData(pageNum){
	$.ajax({
		
		type: "POST",
		url: basePath+"admin/verifyShopData",
		dataType: "json",
		data: {
			pageNum: 1,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			console.log(data);
			if(data.list.length == 0){
	    		new Toast("没有待审核的餐厅").show();
	    		$("#loadingRestList").hide();
	    		
	    	}else{
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			$("#verifyTmpl").tmpl(data.list).appendTo("#verifyList");
			//$("#verifyList").show();
		//	$("#loadingRestList").hide();
	    	}
		}
	});
}

function notApprove(shopId){
	var shopId1 = shopId;
	console.log(shopId1);
	$.ajax({
		type: "POST",
		url: basePath + "admin/notApprove",
		dataType: "json",
		data:{shopId:shopId},
		success: function(data){
			window.location.href = basePath + "html/verifyShop.html";
		}
	});
}

function approveShop(shopId){
	$.ajax({
		type: "POST",
		url: basePath + "admin/approveShop",
		dataType: "json",
		data:{shopId:shopId},
		success: function(data){
			window.location.href = basePath + "html/verifyShop.html";
		}
	});
}

/*
 * 登出
 */
function logout() {
	$.ajax({
		type: "POST",
		url: basePath + "login/logout",
		dataType: "json",
		data: {},
		success: function(data) {
			if (data.logout) {
				window.location.href = basePath + "html/index.html";
			} else {
				new Toast("系统繁忙").show();
			}
		}
	});
}