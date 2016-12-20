/**
 * 
 */
$(document).ready(function() {
	checkLogin();
});
function checkLogin() {
	$.ajax({
		type : "POST",
		url : basePath + "login/checkLogin",
		dataType : "json",
		data : {},
		success : function(data) {
			if (data.isLogin) {
				// 写你们要执行的js函数，如我的是 initShopData(1, currShopType);

			} else {
				window.location.href = basePath
						+ "html/login.html?initTip=loginFirst&redirectUrl="
						+ basePath + "html/complaint.html";
			}
		}
	});
}
/**
 * 存入数据库并返回转态
 */
function submitComplaint() {
		var orderId = $("#order").val();
		var content = $("#content").val().trim();
		var telnumber = $("#telnumber").val();
		if (document.getElementById('order').value.length == 0) {
			new Toast("订单号不能为空，请输入订单号！").show();
			document.getElementById('order').focus();
			return false;
		}
		if (content==null||content==""){
			new Toast("投诉内容不能为空，请输入投诉内容！").show();
			return;
		}
		
		function checkName(me) {
			if ($(me).val().trim().length >= 200){
				new Toast("姓名不能超过200个字").show();   	
			}		
		}
		
		if (document.getElementById('telnumber').value.length == 0) {
			new Toast("电话号码不能为空，请输入电话号码！").show();
			document.getElementById('telnumber').focus();
			return false;
		}
		
		var re= /^1[3|4|5|7|8][0-9]\d{4,8}$/;
		if (!re.test(telnumber)) {
			new Toast("请填写正确电话号码").show();
			return;
		}
		
		$.ajax({
			type : "POST",
			url : basePath + "complaint/doComplaint",// 向服务器提出请求，执行controller里面的doComplaint的这个方法，而需要干什么就是这个方法里里面写
			dataType : "json",
			data : {
				orderId : orderId,
				content : content
			},
			success : function(data) {
				if (data.status == "no") {
					new Toast("订单不存在，请输入正确的订单").show();
				}
				if (data.status == "ok") {
					$("#addrPopBox").show();//点击提交后弹出提示框
				}
			}
		});
}
/**
 * 点击确定回到主页
 */
	function jumpBack() {
		window.location.href = basePath + "html/index.html";//保存成功后点击确定页面跳转到主页
}
