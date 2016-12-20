/*
 * 定义当前显示的餐厅类型
 */
var currShopType = "hot";

$(document).ready(function() {
	initShopData(1, currShopType);
});
/*
 * 监听页面滚动事件，并滚动加载餐厅信息
 */
$(window).scroll(function() {
	if ($(document).scrollTop() + $(window).height() == $(document).height()) {
		if (page.pageNum < page.totalPage && currShopType != "hot") {
			initShopData(++page.pageNum, currShopType);
			$("#loadingRestList").show();
		}
	}
});

/*
 * 定义初始分页信息显示的餐厅类型
 */
var page = {
	pageNum : 1,
	pageSize : 0,
	totalPage : 0,
	totalRow : 0
};

/*
 * 初始化餐厅信息
 */
function initShopData(pageNum, shopType) {
	var url = basePath + "listShop";
	if (shopType == "all" ) {
		shopType = "";
	} else if( shopType == "hot" ) {
		url = basePath + "listHotShop";
	}
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data : {
			pageNum : pageNum,
			pageSize : 12,
			sortTag : 0,
			shopType: shopType
		},
		success : function(data) {
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			if (data.list.length > 0) {
				for (i = 0; i < data.list.length; i ++) {
					data.list[i].shopImg = basePath + data.list[i].shopImg;
				}
				$("#restaurantTmpl").tmpl(data.list).appendTo("#restaurantList");
				$("#restaurantList").show();
				$("#emptyContentTip").hide();
			} else {
				$("#emptyContentTip").show();
			}
			$("#loadingRestList").hide();
		}
	});
}

function listShopByType(me) {
	// 改变页面tab页样式
	$(me).parent().siblings().removeClass("active");
	$(me).parent().addClass("active");
	
	// 清空页面内容并隐藏
	$("#restaurantList").empty().hide();
	$("#emptyContentTip").hide();
	$("#loadingRestList").show();
	
	currShopType = $(me).attr("data-type");
	// 加载信息
	initShopData(1, currShopType);
}