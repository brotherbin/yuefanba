/**
 * Change by 828471	 on 2015/8/15.
 */

$(document).ready(function(){
	
	checkLogin();
	


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
		url: basePath+"shopManager/shopInfoSelect",
		dataType: "json",
		data: {
			pageNum: 1,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			if(data!=null){
				if(data.shopState==1){
					$("#submit2").show();
					$("#submit1").hide();
				}else if(data.shopState==2){
					$("#submit1").show();
					$("#submit2").hide();
				}
			}else{
				$("#modify").hide();
			}
			$("#shopId").val(data.shopId);
			$("#shoperIDNumber").val(data.hostCard);
			$("#shoperName").val(data.hostName);
			$("#shopName").val(data.shopName);
			$("#shopAddr").val(data.shopAddr);
			$("#userId").val(data.userId);
			$("#shopType").val(data.shopType);
			$("#startTime").val(data.startTime);
			$("#endTime").val(data.endTime);
			$("#shopNotice").val(data.shopNotice);
			$("#busState").val(data.busState);
			$("#sendTime").val(data.sendTime);
			$("#shopTel").val(data.shopTel);
			$("#shopImgFile").attr("src", data.shopImg);
			$("#startPrice").val(data.startPrice);
			$("#sendAdd").val(data.sendAdd);
			$("#shopState").val(data.shopState);
		}
	});
}
function shopInfoMessage_() {
	if(!checkIDNumber()|!checkShoperName()|!checkTel()|!checkShopName()|!checkStartPrice()|
			!checkSendAdd()|!checkShopAddr())
	{
		new Toast("请按照规范填写，重新提交！").show();
		return false;
	}else{
		$("#shopInfo").attr("action", basePath + "shopManager/shopInfoModify");
		$("#shopInfo").submit();
	}	
	}
function checkLogin(){
	$.ajax({
		type: "POST",
		url: basePath + "login/checkLogin",
		dataType: "json",
		data:{},
		success: function(data){
			if (data.isLogin && data.loginUser.userType == 1) {
				// 写你们要执行的js函数，如我的是 initShopData(1, currShopType);
				initOrderData(1);
				diseditable();
				window.setInterval('isNewOrder()', 5000); 
				
			} else {
				window.location.href = basePath + "html/login.html?initTip=loginFirst&redirectUrl=" +
				basePath + "html/shopInfoManage.html";
			}
		}
	});
}


/**
 * 将输入框置为可编辑状态
 */
function editable_(){
    $("input").attr("readonly", false);
}

function diseditable(){
    $("input").attr("readonly", true);
}


/**
 * 验证商家姓名
 * @param me
 */
function checkShoperName(){
    var val = $("#shoperName").val().trim();
    if (val.length < 1) {
        inputErrorTip("#shoperName");
        return   false;
    } else {
        inputSuccessTip("#shoperName");
        return  true;
    }
}

/**
 * 验证身份证号码
 * @param me
 */
function checkIDNumber(){
    var val = $("#shoperIDNumber").val().trim();
    // 身份证号码正则表达式
    var rg_IDNo = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
    if (rg_IDNo.test(val)) {
        inputSuccessTip("#shoperIDNumber");
        return   true;
    } else {
        inputErrorTip("#shoperIDNumber");
        return   false;
    }
}

/**
 * 验证餐厅名称
 * @param me
 */
function checkShopName(){
    var  val= $("#shopName").val().trim();
    if (val.length < 1) {
        inputErrorTip("#shopName");
        return  false
    } else {
        inputSuccessTip("#shopName");
        return  true;
    }
}

/**
 * 验证手机号码
 * @param me
 */
function checkTel(){
    var val = $("#shopTel").val().trim();
    var rgTel = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
    if (rgTel.test(val)) {
        inputSuccessTip("#shopTel");
        return  true;
    } else {
        inputErrorTip("#shopTel");
        return  false;
    }
}

/**
 * 验证起送价
 * @param me
 */
function checkStartPrice(){
    var val = $("#startPrice").val().trim();
    var rg = /^[1-9]\d{0,2}$/;
    if (rg.test(val)) {
        inputSuccessTip("#startPrice");
        return  true;
    } else {
        inputErrorTip("#startPrice");
        return  false;
    }
}

/**
 * 验证配送费
 * @param me
 */
function checkSendAdd(){
    var val = $("#sendAdd").val().trim();
    var rg = /^[0-9]\d{0,2}$/;
    if (rg.test(val)) {
        inputSuccessTip("#sendAdd");
        return  true;
    } else {
        inputErrorTip("#sendAdd");
        return  false;
    }
}

/**
 * 验证餐厅地址
 * @param me
 */
function checkShopAddr(){
    var val = $("#shopAddr").val().trim();
    if (val.length < 6) {
        inputErrorTip("#shopAddr");
        return  false;
    } else {
        inputSuccessTip("#shopAddr");
        return  true;
    }
}

/**
 * 选择餐厅类型
 */
function chooseShopType() {
    var types = "";
    $("input[name=shopTypeCheckBox]:checked").each(function(i){
        if (0==i) {
            types = $(this).val();
        } else {
            types += ("," + $(this).val());
        }
    });
    $("#shopType").val(types);
}

/**
 * 输入框填写正确提示
 * @param node 输入框节点
 */
function inputSuccessTip(node){
    $(node).parent().removeClass("has-error").addClass("has-success");
    $(node).siblings("span.form-control-feedback").removeClass("glyphicon-remove").addClass("glyphicon-ok");
}

/**
 * 输入框填写错误提示
 * @param node 输入框节点
 */
function inputErrorTip(node){
    $(node).parent().removeClass("has-success").addClass("has-error");
    $(node).siblings("span.form-control-feedback").removeClass("glyphicon-ok").addClass("glyphicon-remove");
}