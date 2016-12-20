$(document).ready(function(){	
	checkLogin();
	
});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			page.pageNum ++ ;
			listOrderByStatus(currStatus);
			$("#loadingOrdertList").show();
		}
	}
});
var page = {
		pageNum: 1,
		pageSize: 0,
		totalPage: 0,
		totalRow: 0
	};

var currStatus = 0;
/*
 * 显示订单信息
 * */
function listOrderByStatus(status){
	var isNeedEmpty = true;
	if (status == currStatus) {
		isNeedEmpty = false;
	} else {
		currStatus = status;
		page.pageNum=1;
	}
	
		$.ajax({
			type: "POST",
			url: basePath + "order/listOrderInfo",
			dataType: "json",
			data: {
				status:status,
				pageNum: page.pageNum,
				pageSize: 10,
				sortTag: 0
			},
			success: function(data){
				if (isNeedEmpty){
					$(".detail").empty();
				}
				page.pageNum = data.pageNumber;
				page.pageSize = data.pageSize;
				page.totalPage = data.totalPage;
				page.totalRow = data.totalRow;				
				if (data.list.length > 0) {
				  $("#orderByStatusTmpl").tmpl(data.list).appendTo("#orderByStatusList");
				  $("#orderByStatusList").show();
				  //$("#cancelBox").hide();
				  $("#empty").hide();
				  
				}else{
					$("#empty").show();
					//$("#cancelBox").hide();
				}
				$("#loadingOrdertList").hide();
			}
		});
}
/*
 * 取消未派送与等待商家确认的订单信息
 * */
function isCancel(orderInfo){
	var id = $(orderInfo).attr("data-id");
	$("#cancel").val(id);
	$("#cancelBox").show();
	}

function assert(){
	cancelOrder($("#cancel").val());	
	$("#cancelBox").hide()
}

function cancalButton(){
	$("#cancelBox").hide();	
}

function cancelOrder(id){	
//	var id = $(orderInfo).attr("data-id");
	$.ajax({
		type: "POST",
		url: basePath+ "order/cancelOrder",
		dataType: "json",
		data: {
			id: id
		},
		success: function(data){
			if(data.status){	
				new Toast("取消成功！").show();
				$(".detail").empty();
				listOrderByStatus(5);
			}else{
				new Toast("取消失败！").show();	
				$(".detail").empty();
				listOrderByStatus(5);
			}			
		}
	});	
}
/*
 * 删除已取消订单
 * */
function isDetele(orderInfo){
	var id = $(orderInfo).attr("delete-id");
	$("#delete").val(id);
	$("#deletelBox").show();
	}

function assertDelete(){
	deleteOrder($("#delete").val());	
	$("#deletelBox").hide()
}

function cancalDeleteButton(){
	$("#deletelBox").hide();	
}
function deleteOrder(id){	
	$.ajax({
		type: "POST",
		url: basePath+ "order/deleteOrder",
		dataType: "json",
		data: {
			id: id
		},
		success: function(data){
			if(data.status){	
				new Toast("删除成功！").show();
				$(".detail").empty();
				listOrderByStatus(4);
			}else{
				new Toast("删除失败！").show();
				$(".detail").empty();
				listOrderByStatus(4);
			}			
		}
	});	
}
/*
 * 评价订单信息
 * */
function estimate(orderInfo){
	var orderId = $(orderInfo).attr("data-id");
	var shopId = $(orderInfo).attr("shop-id");
	$("#orderId").val(orderId);
	$("#shopId").val(shopId);
	$("#orderName").val(orderId);
	$("#estimatePop").show();
	document.getElementById('estimateContent').focus();
}

function saveEstimate(){
	var estimateContent = $("#estimateContent").val();
	if (document.getElementById('estimateContent').value.trim().length == 0) {
		new Toast("评价内容不能为空，请输入评价内容！").show();
		document.getElementById('estimateContent').focus();
		return false;
	}
	if (document.getElementById('estimateContent').value.length > 200) {
		new Toast("评价内容超过了200字！").show();
		document.getElementById('estimateContent').focus();
		return false;
	}
	saveEatimateContent($("#orderId").val(),$("#shopId").val(),estimateContent);
	$("#estimatePop").hide();
	$("#estimateContent").val("");
}

function closeEstimateInput(){
	$("#estimatePop").hide();
}

function saveEatimateContent(orderId,shopId,estimateContent){	
	$.ajax({
		type: "POST",
		url: basePath+ "order/saveEatimateContent",
		dataType: "json",
		data: {
			orderId: orderId,
			shopId:shopId,
			estimateContent:estimateContent
		},
		success: function(data){
			if(data.status){	
				new Toast("评价成功！").show();
				$(".detail").empty();
				listOrderByStatus(3);
			}else{
				new Toast("评价失败！").show();	
				listOrderByStatus(3);
			}			
		}
	});	
}

/*
 * 点击状态链接样式变化
 * */
function switchStatus(me){
	$(me).parent().siblings(".statusChange").removeClass("statusChange");
	$(me).parent().addClass("statusChange");
}
/*
 * 检验是否登录
 * */
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin && data.loginUser.userType==0) {
				listOrderByStatus(0);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/myOrder.html";
			}
		}
	});
}
