/**
 * 
 */
$(document).ready(function(){
	checkLogin();
});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			initShopData(++ page.pageNum);
		}
	}
});
var page = {
		pageNum: 1,
		pageSize: 0,
		totalPage: 0,
		totalRow: 0
};


//判断是否登陆，跳转登陆界面
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin && data.loginUser.userType == 0) {
				initShopData(1);
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/collection.html";
			}
		}
	});
}


function initShopData(pageNum){
	$.ajax({
		type: "POST",

		url: basePath+"collection/listCollection",

		dataType: "json",
		data: {
			pageNum: pageNum,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			if (data.list.length > 0) {
				$("#collectionTmpl").tmpl(data.list).appendTo("#collectionList");
				$("#collectionList").show();
			} else {
				$("#emptyTip").show();
			}
		}
	});
}

function deleteCollection(me){
	var shopId = $(me).attr("data-shopId");
	$.ajax({
		type: "POST",

		url:basePath + "collection/deleteCollection",

		dataType: "json",
		data: {
			shopId: shopId
		},
		success: function(data){
			if(data.status == "ok"){

				$(me).parent().remove();
				if( $('.restaurant-list').html() == ""){
					$(".empty-con").show();
				}
			}

		}
	});
}

function switchCollectBtn(me, tag) {
	if (tag==1) {
		$(me).find("a.esc-collection").show();
	} else {
		$(me).find("a.esc-collection").hide();
	}
}
function addCollection(me){

	var userId = $(me).attr("data-userId");
	var shopId = $(me).attr("data-shopId");
	var collectTime = $(me).attr("data-collectTime");
	$.ajax({
		type: "POST",

		url:basePath +  "collection/addCollection",

		dataType: "json",
		data: {
			shopId: shopId,
			userId: userId,
			collectTime: collectTime
		},

		success: function(data){


			location.reload([true])  ;


		}
	});
}