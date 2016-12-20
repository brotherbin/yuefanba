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
				selValue(1);
				ValueList(1);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/valuecard.html";
			}
		}
	});
}


$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			selValue(++ page.pageNum);
		}
	}
});

$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			ValueList(++ page.pageNum);
		}
	}
});

var page = {
	pageNum: 0,
	pageSize: 0,
	totalPage: 0,
	totalRow: 0
};

function ValueList(pageNum){
	$.ajax({
		type: "POST",
		url:basePath+ "valuecard/selectValueList",
		dataType: "json",
		data: {
			pageNum: pageNum,
			pageSize:20,
			sortTag: 0
		},
		success: function(data){
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			if (data.list.length > 0) {
				$("#valueListTmpl").tmpl(data.list).appendTo("#valueList");
				$("#valueList").show();
			} 
		}
	});
}

function selValue(pageNum){
	$.ajax({
		type: "POST",
		url:basePath+ "valuecard/ValueCardSelect",
		dataType: "json",
		data: {
			pageNum: pageNum,
			pageSize:20,
			sortTag: 0
		},
		success: function(data){
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			if (data.list.length > 0) {
				$("#selmoneyTmpl").tmpl(data.list).appendTo("#selMoney");
				$("#selMoney").show();
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
			if(data.upmsg=="ok"){
				new Toast("充值成功！").show();
			}
			else if(data.upmsg=="no"){
				new Toast("充值失败！").show();
			}
		}
	});
}

function switchStatus(me){
	$(me).parent().siblings(".statusChange").removeClass("statusChange");
	$(me).parent().addClass("statusChange");
}
