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
	pageSize: 0,
	totalPage: 0,
	totalRow: 0
};
//var basePath="http://HQSFTC00828471M.sf.com:8080/yuefanba/";
//var basePath = "http://localhost:8080/yuefanba/";
function initOrderData(pageNum){
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/foodListFind",
		dataType: "json",
		data: {
			pageNum: page.pageNum,
			pageSize: 20,
			sortTag: 0
		},
		
		success: function(data){
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			$("#foodTmpl").tmpl(data.list).appendTo("#foodList");
			$("#foodList").show();
			$("#loadingFoodList").hide();
		}
	});
}
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin) {
				// 写你们要执行的js函数，如我的是 initShopData(1, currShopType);
				initOrderData(1);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/foodManager.html";
			}
		}
	});
}
function foodDelet(me){
	var foodId=$(me).attr("food-id");
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/foodInfoDelet",
		dataType: "json",
		data: {
			foodId:foodId
		},
		
		success: function(data){		
			if(data){
				
				window.location.href = basePath + "html/foodManager.html";
				//location.reload();
				//initOrderData(1);
			}
		}
	});
}
