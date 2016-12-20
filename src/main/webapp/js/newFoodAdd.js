$(document).ready(function(){
	foodId = getUrlParam("foodId");
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
		url: basePath+"shopManager/newFoodAddNull",
		dataType: "json",
		data: {

			foodId:foodId,
		},
		success:function(data){
			$("#foodId").val(data.foodId);
			$("#foodName").val(data.foodName);
			$("#foodPrice").val(data.foodPrice);
			$("#foodInfor").val(data.foodInfor);
			$("#foodImg").attr("src", data.foodImg);
			$("#buyNum").val(data.buyNum);
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
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/newFoodAdd.html";
			}
		}
	});
}
function addFood_() {

		if(!foodName_()|!foodPrice_()|!buyNum_()|!foodInfor_())
		{
			new Toast("请按照规范填写，重新提交！").show();
			return  false;
			//window.location.href = basePath + "html/shopMessage.html";
		}else{
			$("#foodInfo").attr("action", basePath + "shopManager/foodAdd");
			$("#foodInfo").submit();
		}
		
	}
	

function foodName_() {
	var foodName = $("#foodName").val();
	if($.trim(foodName).length == 0){
		new Toast("请填写食物名称").show();
		return  false;

	}else if($.trim(foodName).length >10 || $.trim(foodName).length <2){
		new Toast("请输入2-10位之间的食物名称(提示:1个汉字=3个字符)").show();
		return  false;
	}else{
		return  true;
	} 

}
function foodPrice_() {
	var foodPrice = $("#foodPrice").val();
	if($.trim(foodPrice).length == 0){
		new Toast("请填写食品价格").show();
		return  false;
	}else if(!/^[0-9]*$/.test(foodPrice)){
		new Toast("请输入数字(提示:1个汉字=3个字符)").show();
		return  false;
	}else if( $.trim(foodPrice).length >5){
		new Toast("请输入1-4位之间的数字").show();
		return  false;
	}else{
		return  true;
	} 

}
function buyNum_() {
	var buyNum = $("#buyNum").val();
	if($.trim(buyNum).length == 0){
		new Toast("请填写食品数量").show();
		return  false;
	}if(!/^[0-9]*$/.test(buyNum)){
		new Toast("请输入数字").show();
		return  false;
	}else if( $.trim(buyNum).length >5){
		new Toast("请输入1-4位之间的数字").show();
		return  false;
	}else{
		return  true;
	} 

}
function foodInfor_() {
	var foodInfor = $("#foodInfor").val();
	if($.trim(foodInfor).length == 0){
		new Toast("请填写食品简介").show();
		return  false;
	}
	if( $.trim(foodInfor).length >30 || $.trim(foodInfor).length <2){
		new Toast("请输入2-10位之间的食物简介(提示:1个汉字=3个字符)").show();
		return  false;
	}else{
		return  true;
	} 

}
