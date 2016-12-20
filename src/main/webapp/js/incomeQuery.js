/**
 * 
 */
$(document).ready(function(){
	initOrderData(1);
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
		url: "shopManager/listOrder",
		dataType: "json",
		data: {
			pageNum: 1,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			/*page.pageNum = data.pageNumber;
			page.pageSize = data.pageSize;
			page.totalPage = data.totalPage;
			page.totalRow = data.totalRow;*/
			
			$("#orderTmpl").tmpl(data).appendTo("#orderList");
			$("#orderList").show();
			$("#loadingOrderList").hide();
		}
	});
}