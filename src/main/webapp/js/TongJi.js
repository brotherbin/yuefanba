/**
 * 
 */
$(document).ready(function(){
	checkLogin();
	
});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			initShopData(++ page.pageNum);
		}
	}
});

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
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/tongJi.html";
			}
		}
	});
}

function initShopData(pageNum){
	console.log("begin:tongji");
	$.ajax({
		type: "POST",
		url: basePath+"admin/tongJi",
		dataType: "json",
		data: {
		},
		success: function(data){
			
			console.log(data);
			console.log(data.sum_shoper[0]);
			console.log(data.sum_user[0]);
			
			var shopLen = data.listShop.length;
			for (i = 0; i < shopLen; i ++) {
				data.listShop[i].shopIndex = i + 1;
			}
			var shopLen = data.listShop.length;
			for (i = 0; i < shopLen; i ++) {
				data.listShop[i].shopIndex = i + 1;
			}
			var foodLen = data.listFood.length;
			for (i = 0; i < foodLen; i ++) {
				data.listFood[i].foodIndex = i + 1;
			}
			
			$("#TongJiTmpl").tmpl(data.listShop).appendTo("#TongJiList");
			$("#TongJiFood").tmpl(data.listFood).appendTo("#TongJiListShop");
			$("#AllTmpl").tmpl(data.sum_shoper).appendTo("#TongJiAll");
			$("#UserTmpl").tmpl(data.sum_user).appendTo("#AllUser");
		//	$("#TongJiList").show();
		
		//	$("#TongJiAll").show();
		//	$("#loadingRestList").hide();
		}
	});
}

function changeShop(){
	$("#TongJiList").show();
	$("#TongJiListShop").hide();
}

function changeFood(){
	$("#TongJiList").hide();
	$("#TongJiListShop").show();
	
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