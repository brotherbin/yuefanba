/**
 * 
 */
$(document).ready(function(){
	
	checkLogin();
	

	
	$("#searchName").keydown(function(e) {
		if (e.keyCode == 13) {
			searchByName();
		}
	});
});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			initShopData(++ page.pageNum);
		}
	}
});
var page = {
	pageNum: 1,
	pageSize: 0,
	totalPage: 0,
	totalRow: 0
};

function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin && data.loginUser.userType==2) {
				initShopData(1);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/admin.html";
			}
		}
	});
}

function initShopData(pageNum){
	$.ajax({
		type: "POST",
		url: basePath+ "admin/manage",
		dataType: "json",
		data: {
			pageNum: pageNum,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			console.log(data);
			if(data.list.length == 0){
	    		new Toast("未找到符合搜索条件的餐厅").show();
	    		$("#loadingUpdate").hide();
	    	}else{
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			console.log(data.list);
			$("#restaurantTmpl").tmpl(data.list).appendTo("#restaurantList");
		//	$("#restaurantList").show();
		//	$("#searchList").hide();
		//	$("#loadingRestList").hide();
	    	}
		}
	});
}
function updateSaleData(){
	$("#update").attr("disabled", true).text("正在更新");
	$.ajax({
		type: "POST",
		url:basePath + "admin/setTableTop",
		dataType: "json",
		success: function(data){
			console.log(data);
			$("#update").attr("disabled", false).text("更新排名");
		}
	});
}

function searchByName(pageNum){
	
	
	var searchKey = $("#searchName").val().trim();
	if (searchKey == null || searchKey == "") {
		new Toast("请输入搜索内容").show();
	}
	else{
		$("#loadingUpdate").show();
	$.ajax({
		type:"POST",
		
	    url:basePath+"admin/adminSearchByName",
	    dataType:"json",
	    data:{
	    	shopId:searchKey
	    },
	    success:function(data){
	    	console.log(data);
	    	if(data.list.length == 0){
	    		new Toast("未找到符合搜索条件的餐厅").show();
	    		//$("#loadingUpdate").hide();
	    	}else{
	    	page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			$("#restaurantList").empty();
			$("#restaurantTmpl").tmpl(data.list).appendTo("#restaurantList");
		//	$("#restaurantList").hide();
		//	$("#searchList").show();
		//	$("#restaurantTmpl").tmpl(data.list).appendTo("#restaurantList");
			//$("#loadingUpdate").hide();
		//	$("#loadingRestList").hide();
	    	}
	    }
	});
	}
	
}

function cancelShop(shopId){
	$.ajax({
		type: "POST",
		url: basePath + "admin/cancelShop",
		dataType: "json",
		data:{shopId:shopId},
		success: function(data){
			console.log(data);
			window.location.href = basePath + "html/admin.html";
		}
	});
}

function openShop(shopId){
	$.ajax({
		type: "POST",
		url: basePath + "admin/openShop",
		dataType: "json",
		data:{shopId:shopId},
		success: function(data){
			console.log(data);
			window.location.href = basePath + "html/admin.html";
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