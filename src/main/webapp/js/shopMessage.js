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
		url: basePath+"shopManager/shopInfoSelect",
		dataType: "json",
		data: {
			pageNum: 1,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			if(data!=null){
				if(data.shopState==1){
					$("#submit2").show();
					$("#submit1").hide();
				}else if(data.shopState==2){
					$("#submit1").show();
					$("#submit2").hide();
				}
			}else{
				$("#modify").hide();
			}
			$("#shopId").val(data.shopId);
			$("#hostCard").val(data.hostCard);
			$("#hostName").val(data.hostName);
			$("#shopName").val(data.shopName);
			$("#shopAddr").val(data.shopAddr);
			$("#userId").val(data.userId);
			$("#shopType").val(data.shopType);
			$("#startTime").val(data.startTime);
			$("#endTime").val(data.endTime);
			$("#shopNotice").val(data.shopNotice);
			$("#busState").val(data.busState);
			$("#sendTime").val(data.sendTime);
			$("#shopTel").val(data.shopTel);
			$("#shopImg").attr("src", data.shopImg);
			$("#startPrice").val(data.startPrice);
			$("#sendAdd").val(data.sendAdd);
			$("#shopState").val(data.shopState);
		}
	});
}
function shopInfo_() {
	if(!hostname_()|!hostCard_()|!shopName_()|!shopAddr_()|!shopTel_()|!shopNotice_()|!sendAdd_()|!startPrice_())
	{
		new Toast("请按照规范填写，重新提交！").show();
		return false;
		//window.location.href = basePath + "html/shopMessage.html";
	}else{
		$("#shopInfo").attr("action", basePath + "shopManager/shopInfoModify");
		$("#shopInfo").submit();
	}
	
}
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin && data.loginUser.userType==1) {
				// 写你们要执行的js函数，如我的是 initShopData(1, currShopType);
				initOrderData(1);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/shopInfoManage.html";
			}
		}
	});
}
//使页面不可编辑 
function readonlychange() {
	document.getElementById("hostName").readOnly="";
	document.getElementById("hostCard").readOnly="";
	document.getElementById("shopName").readOnly="";
	document.getElementById("shopAddr").readOnly="";
	document.getElementById("shopTel").readOnly="";
	document.getElementById("startPrice").readOnly="";
	document.getElementById("sendAdd").readOnly="";
	document.getElementById("shopNotice").readOnly="";
	document.getElementById("sendTime").readOnly="";
	document.getElementById("shopType").readOnly="";
	document.getElementById("startTime").readOnly="";
	document.getElementById("endTime").readOnly="";
	//document.getElementById("busState").readOnly="";
	document.getElementById("shopState").readOnly="";
	//document.getElementById("shopApplyTime").readOnly="";
	//document.getElementById("shopReplyTime").readOnly="";




} 
/**
 * 界面重載
 */
function reload() {
	window.location.href = basePath + "html/shopMessage.html";

} 
/**
 * 店主名校驗
 * @returns {Boolean}
 */
function hostname_(){
	var hostName = $("#hostName").val();
	if($.trim(hostName).length == 0){
		new Toast("姓名不能为空").show();
		return  false;
	}else if($.trim(hostName).length >50 || $.trim(hostName).length <2){

		new Toast("请输入2-50位之间的姓名").show();
		return  false;
	}
	else{

		return true;
	}
}
/**
 * 身份證校驗
 * @returns {Boolean}
 */
function hostCard_(){
	var hostCard = $("#hostCard").val();
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x|Ⅹ)$)/; 
	if($.trim(hostCard).length == 0){
		new Toast("请输入身份证号码").show();
		return  false;

	}else if(!reg.test(hostCard)){

		new Toast("请输入合法的身份证").show();
		return  false;
	}
	else{

		return true;
	}
}
/**
 * 商家店名校验
 * @returns {Boolean}
 */
function shopName_(){
	var shopName = $("#shopName").val();
	if($.trim(shopName).length == 0){
		new Toast("请输入商家名称").show();
		return  false;

	}
	else if($.trim(shopName).length >30 || $.trim(shopName).length <2){

		new Toast("请输入2-30位之间的商家名称").show();
		return  false;
	}
	else{

		return true;
	}
}
/**
 * 餐厅地址校验
 * @returns {Boolean}
 */
function shopAddr_() {
	var shopAddr = $("#shopAddr").val();
	if($.trim(shopAddr).length == 0){
		new Toast("请输入餐厅(单位)企业地址").show();
		return  false;
	}else if($.trim(shopAddr).length > 100||!/[^u4E00-u9FA5]{2,}$/.test(shopAddr)){
		new Toast("请输入合法的中文餐厅地址").show();
		return  false;
	}else{
		return  true;
	} 

}
/**
 * 商家联系方式校验
 * @returns {Boolean}
 */
function shopTel_(){
	var shopTel = $("#shopTel").val();
	var reg = /^1[3|4|5|8][0-9]\d{8}$/;
	var reg1 = /^[0-9]{3,4}-[0-9]{7,8}$/;
	var reg2 = /^\([0-9]{3,4}\)[0-9]{7,8}$/;
	var reg3 = /^[0-9]{3,4}\([0-9]{7,8}\)$/;
	if($.trim(shopTel).length == 0){
		new Toast("请输入企业电话").show();
		return  false;

	}else if(!reg.test(shopTel)&&!reg1.test(shopTel)&&!reg2.test(shopTel)&&!reg3.test(shopTel)){

		new Toast("请输入正确的电话").show();
		return  false;
	}
	else{

		return true;
	}
}
/**
 * 商家公告校验
 * @returns {Boolean}
 */
function shopNotice_() {
	var shopNotice = $("#shopNotice").val();
	if($.trim(shopNotice).length == 0){
		new Toast("请输入店铺公告").show();
		return  false;

	}else if( $.trim(shopNotice).length >50 || $.trim(shopNotice).length <2){
		new Toast("请输入2-50位之间的食物简介(提示:1个汉字=3个字符)").show();
		return  false;
	}else{
		return  true;
	} 

}
/**
 * 配送费校验
 * @returns {Boolean}
 */
function sendAdd_() {
	var sendAdd = $("#sendAdd").val();
	if($.trim(sendAdd).length == 0){
		new Toast("请填写配送费").show();
		return  false;

	}else if(!/^[0-9]*$/.test(sendAdd)){
		new Toast("请输入数字").show();
		return  false;
	}else if( $.trim(sendAdd).length >5){
		new Toast("请输入1-4位之间的数字").show();
		return  false;
	}else{
		return  true;
	} 

}
/**
 * 起送价校验
 * @returns {Boolean}
 */
function startPrice_() {
	var startPrice = $("#startPrice").val();
	if($.trim(startPrice).length == 0){
		new Toast("请填写起送价").show();
		return  false;

	}else if(!/^[0-9]*$/.test(startPrice)){
		new Toast("请输入数字").show();
		return  false;
	}else if( $.trim(startPrice).length >5){
		new Toast("请输入1-4位之间的数字").show();
		return  false;
	}else{
		return  true;
	} 

}