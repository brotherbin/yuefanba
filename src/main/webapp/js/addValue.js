$(document).ready(function(){
	checkLogin();
});

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
				
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/addValue.html";
			}
		}
	});
}
function addValue(){
	var addMoney=$("#addMoney").val();
	$.ajax({
		type: "POST",
		url:basePath+ "valuecard/addMoney",
		dataType: "json",
		data: {
			addMoney:addMoney
		},
		success:function(data){
			if(data.status=="yes"){
				new Toast("充值成功！").show();
				window.location.href = basePath +"html/valuecard.html";
				
			}
			else if(data.status=="no"){
				new Toast("充值失败！").show();
				
			}
		}
	});
}

function switchStatus(me){
	$(me).parent().siblings(".statusChange").removeClass("statusChange");
	$(me).parent().addClass("statusChange");
}
