
/*
 * url:当用户是被拦截而进入到登录页面时记住拦截前的页面
 * initTip:需要登录的提示
 */
var redirectUrl, initTip;

$(document).ready(function(){

	redirectUrl = getUrlParam("redirectUrl");
	initTip = getUrlParam("initTip");

	showInitTip();

	$("#password").keydown(function(e){
		if (e.keyCode==13){
			login();
		}
	});
});

function showInitTip(){
	if (initTip == "loginFirst") {
		new Toast("请先登录").show();
	}
}

/**
 * 用户登录信息
 */

function login(){
	if ($("#userLogin").hasClass("disabled-btn")) {
		return;
	}
	var userName=$.trim($("#username").val());
	var password=$.trim($("#password").val());

	var url = $("#url").val();
	if(userName==null||userName==""){
		new Toast("用户名不能为空").show();
		return;
	}
	else if(password==null||password==""){
		new Toast("密码不能为空").show();
		return;
	}
	$("#userLogin").addClass("disabled-btn").val("登录中");
	$.ajax({
		type: "POST",
		url: basePath + "login/validate",
		dataType: "json",
		data: {
			userName: userName,
			password: password
		},
		success: function(data){
			if (data.state == "user") {
				if (redirectUrl != undefined) {
					// 回到原页面
					window.location.href = redirectUrl;
				} else {
					// 主页
					window.location.href = basePath + "html/index.html";
				}
			} else if (data.state == "shoper"){
				// 后台
				window.location.href = basePath + "html/shopInfoManage.html";
			} else if(data.state=="admin") {
				window.location.href = basePath + "html/admin.html";
			}
			else{
				new Toast(data.msg).show();
				$("#userLogin").removeClass("disabled-btn").val("登录");
			}
		}
	});
}

function inputFocus(me) {
	$(me).parent().addClass("input-focus");
}
function inputBlur(me) {
	$(me).parent().removeClass("input-focus");
}
/**
 * 判断用户密码长度
 * @param me
 */
function checkPassword(me){
	if($(me).val().trim().length==16){
		new Toast("密码不能超过16个字符").show();
	}
}
/**
 * 判断用户名长度
 * @param me
 */
function checkName(me){
	if($(me).val().trim().length==12){
		new Toast("用户名不能超出12个字符").show();
	}
}
