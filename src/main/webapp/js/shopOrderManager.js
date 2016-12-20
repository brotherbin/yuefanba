/**
 * 
 */
$(document).ready(function(){	
	checkLogin();

});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			++ page.pageNum;
			initOrderData(mystatus);
		}
	}
});
var page = {
	pageNum: 1,
	pageSize: 0,
	totalPage: 0,
	totalRow: 0
};
var  mystatus=10;
var  changState=false;
function initOrderData(status){
	var isNeedEmpty = true;
	if (mystatus==status) {
		isNeedEmpty = false;
	}else{
		mystatus=status;
		page.pageNum=1;
	}	
	if(status==0){
		$("#active0").addClass("active");
		$("#active2").removeClass();
		$("#active3").removeClass();
		$("#active1").removeClass();
		$("#active4").removeClass();
	}else if(status==1){

		$("#active1").addClass("active");
		$("#active0").removeClass();
		$("#active2").removeClass();
		$("#active3").removeClass();
		$("#active4").removeClass();

	}else if(status==2){
		$("#active2").addClass("active");
		$("#active0").removeClass();
		$("#active3").removeClass();
		$("#active1").removeClass();
		$("#active4").removeClass();
	}else if(status==3){
		$("#active3").addClass("active");
		$("#active0").removeClass();
		$("#active2").removeClass();
		$("#active1").removeClass();
		$("#active4").removeClass();
	}else if(status==4){
		$("#active4").addClass("active");
		$("#active0").removeClass();
		$("#active2").removeClass();
		$("#active1").removeClass();
		$("#active3").removeClass();
	}	
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/listOrder",
		dataType: "json",
		data: {
			status:status,
			pageNum: page.pageNum,
			pageSize: 10,
			sortTag: 0
		},
		success: function(data){
			if(isNeedEmpty|changState){
				changState=false;
				$("#orderList").empty();
				
			}
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
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
			if (data.isLogin&& data.loginUser.userType==1) {
				// 写你们要执行的js函数，如我的是 initShopData(1, currShopType);
				initOrderData(0);
				window.setInterval('isNewOrder()', 5000); 
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/shopOrderManage.html";
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
				if(status==4){
					changState = true;
					initOrderData(0);
					
				}
				else{
					changState = true;
					initOrderData(status-1);
					
				}
				
			}
					
		}
	});	
}