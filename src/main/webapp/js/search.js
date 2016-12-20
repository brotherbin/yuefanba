/**
 * 
 */
var searchKey;
$(document).ready(function() {
	searchKey = getUrlParam("keyword");
	$("#searchKeySpan").text(searchKey);
	$("#searchText").val(searchKey);
	if (searchKey != null && searchKey != "") {
		initShopData(1, searchKey);
	} else {
		$("#loadingRestList").hide();
		$("#emptyContentTip").show();
	}
});
/*
 * 监听页面滚动事件，并滚动加载餐厅信息
 */
$(window).scroll(function() {
	if ($(document).scrollTop() + $(window).height() == $(document).height()) {
		if (page.pageNum < page.totalPage) {
			initShopData(++page.pageNum, searchKey);
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
function initShopData(pageNum, searchKey) {
	$.ajax({
		type : "POST",
		url : basePath + "doSearch",
		dataType : "json",
		data : {
			pageNum : pageNum,
			pageSize : 20,
			sortTag : 0,
			searchKey: searchKey
		},
		success : function(data) {
			page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;
			if (data.list.length > 0) {
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