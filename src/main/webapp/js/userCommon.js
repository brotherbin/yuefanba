/**
 * 
 */
$(document).ready(function(){
	// 初始化当前登录用户
	initLoginUser();
	
	// 初始化页面所有动画效果
	initAnimation();
    // 将搜索框绑定回车键点击事件
	$("#searchText").keydown(function(e) {
		if (e.keyCode == 13) {
			searchRestaurant();
		}
	});
});
function initAnimation(){
	$("#login").mouseover(function(){
		$("#userDrop").show();
	});
	$("#login").mouseout(function(){
		$("#userDrop").hide();
	});
	if ( $("#toTop").length > 0 && $(".container").length > 0 ) {
		$("#toTop").css("left", $(".container").offset().left + 980);
	}
	$(".container").css("min-height", $(window).height()-150);
}

/*
 * 加载当前登录用户
 */
function initLoginUser() {
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data: {},
		success: function(data){
			if(data.isLogin && data.loginUser.userType==0) {
				$("#notLogin").hide();
				$("#login").show();
				$("#userName").text("Hi, " + data.loginUser.userName + " !");
			}
		},
		error: function(){
			var a = 1;
		}
	});
}

/*
 * 点击搜索
 */
function searchRestaurant(){
	var searchKey = $("#searchText").val().trim();
	if (searchKey == null || searchKey == "") {
		new Toast("请输入搜索内容").show();
	} else {
		var tParam = encodeURIComponent("keyword=" + searchKey);
		window.location.href = basePath + "html/search.html?" + tParam;
	}
}