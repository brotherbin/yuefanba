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
			//$("#foodList").empty();
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
			if (data.isLogin&& data.loginUser.userType==1) {
				// 写你们要执行的js函数，如我的是 initShopData(1, currShopType);
				initOrderData(1);
				window.setInterval('isNewOrder()', 5000); 
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/shopFoodManager.html";
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
/**
 * 显示视图
 * @param me
 */
var  food_id=null;
function   newFoodAddView(me){
	$('#foodEditOrAddModal').modal();
	 food_id =$(me).attr("food-id");
	 $.ajax({
			type: "POST",
			url: basePath+"shopManager/foodAddModify",
			dataType: "json",
			data: {

				foodId:food_id,
			},
			success:function(data){
				$("#foodId").val(data.foodId);
				$("#foodName").val(data.foodName);
				$("#foodPrice").val(data.foodPrice);
				$("#foodInfor").val(data.foodInfor);
				$("#foodImg").attr("src", data.foodImg);
				$("#buyNum").val(data.buyNum);
				$("#foodNum").val(data.foodNum);
			}
		});
}
function  stopSaleView(me){
	$('#StopTipModal').modal();
	 food_id =$(me).attr("food-id");
}
function  openSaleView(me){
	$('#OpenTipModal').modal();
	 food_id =$(me).attr("food-id");
}
function  foodDeletView(me){
	$('#delOrStopTipModal').modal();
	 food_id =$(me).attr("food-id");
}
/**
 * 调用方法
 */
function   modifyAndAdd(){
	if(!checkfoodPrice()|!checkFoodInfor()|!checkFoodName_()){
		
	}else{
		$("#foodInfo").attr("action", basePath + "shopManager/modifyAndAdd");
		$("#foodInfo").submit();
	}
	
}
function  stopSale(me){
	$('#StopTipModal').modal();
	var  foodId =food_id
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/foodStopSale",
		dataType: "json",
		data: {
			foodId:foodId
		},
		
		success: function(data){		
			if(data){
				
				window.location.href = basePath + "html/shopFoodManage.html";
				
			}
		}
	});
}
function  foodDelet(me){
	$('#delOrStopTipModal').modal();
	var  foodId =food_id
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/foodInfoDelet",
		dataType: "json",
		data: {
			foodId:foodId
		},
		
		success: function(data){		
			if(data){
				
				window.location.href = basePath + "html/shopFoodManage.html";
				
			}
		}
	});
}
function checkFoodName_(){
	var  foodName=$("#foodName").val().trim();
	if (foodName.length<2||foodName.length>10){
		new Toast("请输入2-10位之间的食物名称(提示:1个汉字=3个字符)").show();
		inputErrorTip("#foodName");
		return  false;
	}else{
		 inputSuccessTip("#foodName");
		return  true;
	}
	
}
function  checkfoodPrice(){
	var   foodPrice=$("#foodPrice").val();
	if($.trim(foodPrice).length == 0){
		new Toast("请填写食品价格").show();
		inputErrorTip("#foodPrice");
		return  false;
	}else if(!/^[0-9]*$/.test(foodPrice)){
		new Toast("请输入数字(提示:1个汉字=3个字符)").show();
		inputErrorTip("#foodPrice");
		return  false;
	}else if( $.trim(foodPrice).length >5){
		new Toast("请输入1-4位之间的数字").show();
		inputErrorTip("#foodPrice");
		return false;
	}else{
		 inputSuccessTip("#foodPrice");
		return true;
	}
	
}
function checkFoodInfor(){
	var foodInfor = $("#foodInfor").val();
	if($.trim(foodInfor).length == 0){
		new Toast("请填写食品简介").show();
		inputErrorTip("#foodInfor");
		return  false;
	}
	if( $.trim(foodInfor).length >150 ){
		new Toast("请控制输入食物简介字数(提示:1个汉字=3个字符)").show();
		inputErrorTip("#foodInfor");
		return  false;
	}else{
		 inputSuccessTip("#foodInfor");
		return  true;
	} 
	
}
/**
 * 输入框填写正确提示
 * @param node 输入框节点
 */
function inputSuccessTip(node){
    $(node).parent().removeClass("has-error").addClass("has-success");
    $(node).siblings("span.form-control-feedback").removeClass("glyphicon-remove").addClass("glyphicon-ok");
}

/**
 * 输入框填写错误提示
 * @param node 输入框节点
 */
function inputErrorTip(node){
    $(node).parent().removeClass("has-success").addClass("has-error");
    $(node).siblings("span.form-control-feedback").removeClass("glyphicon-ok").addClass("glyphicon-remove");
}