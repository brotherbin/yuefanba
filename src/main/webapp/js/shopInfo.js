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
//var basePath="http://HQSFTC00828471M.sf.com:8080/yuefanba/";
//var basePath = "http://localhost:8080/yuefanba/";
var page = {
	pageNum: 1,
	pageSize: 0,
	totalPage: 0,
	totalRow: 0
};
function initOrderData(status){
	
	if(status==0){
		$("#active0").addClass("active0");
		$("#active2").removeClass("active0");
		$("#active3").removeClass("active0");
		$("#active1").removeClass("active0");
		$("#active4").removeClass("active0");
	}else if(status==1){
		$("#active1").addClass("active0");
		$("#active0").removeClass("active0");
		$("#active2").removeClass("active0");
		$("#active3").removeClass("active0");
	}else if(status==2){
		$("#active2").addClass("active0");
		$("#active0").removeClass("active0");
		$("#active3").removeClass("active0");
		$("#active1").removeClass("active0");
		$("#active4").removeClass("active0");
	}else if(status==3){
		$("#active3").addClass("active0");
		$("#active0").removeClass("active0");
		$("#active2").removeClass("active0");
		$("#active1").removeClass("active0");
		$("#active4").removeClass("active0");
	}else if(status==4){
		$("#active4").addClass("active0");
		$("#active0").removeClass("active0");
		$("#active2").removeClass("active0");
		$("#active1").removeClass("active0");
		$("#active3").removeClass("active0");
	}	
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/listOrder",
		dataType: "json",
		data: {
			status:status,
			pageNum: page.pageNum,
			pageSize: 5,
			sortTag: 0
		},
		success: function(data){
			$(".detail").empty();
			/*page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;*/
			if(data.list.length > 0){				
				$("#orderTmpl").tmpl(data.list).appendTo("#orderList");
				$("#orderList").show();
				$("#loadingOrderList").hide();
			}else{
				$("#loadingOrderList").show();
			}
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
				initOrderData(0);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/shopInfo.html";
			}
		}
	});
}
function ordersure(me, status){
	var id = $(me).attr("data-id");
	
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/orderSure",
		dataType: "json",
		data: {
			orderId: id,
			status:status
		},
		success: function(data){
			if(data){
				//location.reload();
				initOrderData(status-1)
			}
					
		}
	});	
}