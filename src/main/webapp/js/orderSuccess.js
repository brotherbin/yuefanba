$(document).ready(function(){
	var orderId = getUrlParam("orderId");
	var orderTel = getUrlParam("orderTel");
	if (orderId == undefined || orderTel == undefined) {
		window.location.href = basePath + "html/index.html";
	}
	$("#orderIdSpan").text(orderId);
	$("#orderTelSpan").text(orderTel);
});