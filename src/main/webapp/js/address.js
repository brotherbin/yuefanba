/**
 * @author 田粉粉
 */
$(document).ready(function(){
	checkLogin();
});
$(window).scroll(function(){
	if ( $(document).scrollTop()+$(window).height()==$(document).height() ){
		if ( page.pageNum < page.totalPage ){
			initShopData(++ page.pageNum);
			$("#loadingOrdertList").show();
		}
	}
});
var page = {
		pageNum: 1,
		pageSize: 0,
		totalPage: 0,
		totalRow: 0
};
//var basePath="http://hqsftc00828502m.sf.com:8080/yuefanba/";
/**
 * 判断是否登录
 */
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin && data.loginUser.userType==0) {				
				listAddress(1);
				province();	
				
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" + basePath + "html/address.html";
			}
		}
	});
}
function showAddrPopBox() {
	$("#addrPopBox").show();
	$("#position").show();
}
function hideAddrPopBox() {
	$("#addrPopBox").hide();
	addrTag = 0;
	$("#edit").html("新添加地址");	
}
/**
 * 地址保存
 */
var addrTag = 0;
function addAddress(){
	var url;
	  if (addrTag == 0) {		 
		  url =basePath+ "userInfo/save";	
		  var getName = $("#name").val().trim();
		  var userPhone = $("#phone").val().trim();
		  var provence = $("#provinceList").val().trim();
		  var strs = new Array();
		  strs = provence.split("-");
		  var provenceName = strs[1];
		  var city = $("#cityList").val().trim();
		  strs = city.split("-");
		  var cityName = strs[1];
	      var area = $("#areaList").val();
	      if (area==null) area = "";
		  var addrDetail = $("#addrDetail").val().trim();	
		  if (getName==null || getName=="") {			
			  new Toast("姓名不能为空").show();
				return;
			}
		  if ($("#name").val().length>9) {			
			  new Toast("请输入正确的姓名").show();
				return;
			}
		  var re =/^1[3|4|5|7|8][0-9]\d{4,8}$/;
			if (!re.test(userPhone)) {
				new Toast("请填写正确电话号码").show();
				return;
			}
			if (provenceName==null || provenceName=="") {
				new Toast("省份不能为空").show();
				return;
			}
			if (cityName==null || cityName=="") {
				new Toast("城市不能为空").show();
				return;
			}
			if (addrDetail==null || addrDetail=="") {
				new Toast("详细地址不能为空").show();
				return;
			}
		   $.ajax({
			   type: "POST",
			   url: url,
		       dataType: "json",
			   data: {
				  getName: getName, 
				  userPhone: userPhone,
				  userAddress: provenceName +cityName+area+ addrDetail
			   },
		       success: function(data){	
				   if (data.status=="ok") {
					  var addrinfo = {
							getName: getName,
							userPhone: userPhone,
							userAddress: provenceName +cityName+area+ addrDetail
					  }
					  $("#addressTmpl").tmpl(addrinfo).appendTo("#addressList");
					  $("#loadingOrdertList").show();
					  hideAddrPopBox();					
					 location.reload([true]);
				   } else {			
					//..
				   }
				 $("#loadingOrdertList").hide();
			  }
		  });
	 } else {
		      url = basePath+"userInfo/updateAddress"	
			  var updateId = $("#updateId").val().trim();
			  var getName = $("#name").val().trim();
			  var userPhone = $("#phone").val().trim();			
			  var addrDetail = $("#addrDetail").val().trim();	
			  if (getName==null || getName=="") {			
				  new Toast("姓名不能为空").show();
					return;
			  }			
			  var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
			  if (!re.test(userPhone)) {
					new Toast("请填写正确电话号码").show();
					return;
			  }
			
			 if (addrDetail==null || addrDetail=="") {
				    new Toast("详细地址不能为空").show();
					return;
			  }
			  $.ajax({
				    type: "POST",
				    url: url,
				    dataType: "json",
				    data: {
					     updateId:updateId,
					     getName: getName, 
					     userPhone: userPhone,
					     userAddress: addrDetail
				     },
				    success: function(data){	
				 	      if (data.status=="yes") {				   
						        var addrinfo = {
								    getName: getName,
								    userPhone: userPhone,
								    userAddress: addrDetail
						        }
						        $("#addressTmpl").tmpl(addrinfo).appendTo("#addressList");   
						        $("#loadingOrdertList").show();
						        hideAddrPopBox();						     
						        location.reload([true]);
					      } else {
						       //..
					      }
					     $("#loadingOrdertList").hide();
				 }
			 });
	  }	
}
/**
 * 编辑地址
 * @param update
 */
function updateAddress(upad){	
		showAddrPopBox();
		addrTag = 1;	
		$("#edit").html("修改地址");
		$("#position").hide();
		var updateId = $(upad).attr("update-id");
		$("#updateId").val(updateId);		   
		var name = $(upad).parent().siblings(".dizhi").text();
		var userPhone=$(upad).parent().siblings(".dizhi1").text();
		var userAddress=$(upad).parent().siblings(".dizhi2").text();				
		$("#name").val(name);
		$("#phone").val(userPhone);
		$("#addrDetail").val(userAddress);		
}
/**
 * 地址显示
 */
function listAddress(pageNum){
	$.ajax({
			type: "POST",
			url: basePath+"userInfo/listAddress",
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
				  for (i = 0 ; i < data.list.length; i ++) {
					  data.list[i].userAddress = data.list[i].userAddress.replace(/-/g,"");
				  }
				  $("#addressTmpl").tmpl(data.list).appendTo("#addressList");
				  $("#addressList").show();
			  } else {
				//..
			  }
			  $("#loadingOrdertList").hide();
			  $("#div1").show();
		  }
	});
}
/**
 * 删除地址
 * @param addre
 */
function deleteAddress(addre){
	var id = $(addre).attr("data-id");
	$.ajax({
			type: "POST",
			url:basePath+ "userInfo/deleteAddress",
			dataType: "json",
			data: {
				id: id
			},
			success: function(data){
				if(data.status == "success"){	
					$(addre).parent().parent().remove();				
					//location.reload([true]);
				}		
			}
	});	
}
/**
 * 获取省份 
 */
function province(){	
	$.ajax({
			type: "POST",
			url: basePath+"userInfo/province",
			dataType: "json",
			success: function(data){
				if (data.length > 0) {	
						$("#provinceList").html("<option>--请选择--</option>");
						$("#provinceTmpl").tmpl(data).appendTo("#provinceList");
				}else {
				//..
				}					
			}
	});	
}
/**
 * 获取城市
 */
function city(){		
	var province = $("#provinceList").val();
	var strs = new Array();
	strs = province.split("-");
	var provinceCode = strs[0];
	$.ajax({
			type: "POST",
			url: basePath+"userInfo/city",
			dataType: "json",
			data: {
				code: provinceCode
			},
			success: function(data){
				if (data.length > 0) {	
						$("#cityList").empty();
						$("#cityList").html("<option>--请选择--</option>");
						$("#cityTmpl").tmpl(data).appendTo("#cityList");
				}else {
			   //
				}
			}
	});
	area();
}
/**
 * 获取地区
 */
function area(){
	var city = $("#cityList").val();
	var strs = new Array();
	strs = city.split("-");
	var cityCode = strs[0];
	$.ajax({
			type: "POST",
			url: basePath+"userInfo/area",
			dataType: "json",
			data: {
				cityCode: cityCode
			},
			success: function(data){	
				if (data.length > 0) {
					$("#areaList").empty();		
					//$("#areaList").html("<option>--请选择--</option>");
					$("#areaTmpl").tmpl(data).appendTo("#areaList");		
				}else {	
					//..
				}
			}
	});	
}
/**
 * 输入判断
 */
function checkName(me) {
	if ($(me).val().trim().length >= 10){
		new Toast("姓名不能超过10个字").show();   	
	}		
}
function checkAddress(me) {
	if ($(me).val().trim().length >= 30){
		new Toast("地址不能超过30个字").show();	
	}		
}