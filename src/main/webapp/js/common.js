/*
 * 服务器访问根路径
 */
var basePath = "http://localhost:8080/yuefanba/";

/*
 * 监听页面滚动事件，判断是否显示“回到顶部”按钮
 */
$(window).scroll(function() {
	if ($(document).scrollTop() > 0) {
		$("#toTop").show();
	} else {
		$("#toTop").hide();
	}
});

/*
 * 登出
 */
function logout() {
	$.ajax({
		type: "POST",
		url: basePath + "login/logout",
		dataType: "json",
		data: {},
		success: function(data) {
			if (data.logout) {
				window.location.href = basePath + "html/index.html";
			} else {
				new Toast("系统繁忙").show();
			}
		}
	});
}

/**
 * 用Jquery仿照Android的Toast效果
 * 用法：new Toast("msg").show();
 */
var Toast = function(msg) {
	this.context = $("body");
	this.message = msg;
	this.fadeTime = 1000;
	this.showTime = 2000;
	this.bottom = 50;
	this.left;
	this.init();
}
var msgEntity;
Toast.prototype = {
	init : function() {
		$("#t-o-a-s-t-M-s-g").remove();
		var msgDiv = "<div id='t-o-a-s-t-M-s-g'><span>" + this.message + "</span></div>";
		this.context.append(msgDiv);
		msgEntity = $("#t-o-a-s-t-M-s-g");
		this.left = this.context.width()/2 - msgEntity.find('span').width()/2;
		msgEntity.css({
			'position': 'fixed',
			'bottom': this.bottom,
			'left': this.left,
			'background-color': '#ef2d4b',
			'color': '#fff',
			'font-size': '1.0em',
			'font-family': '微软雅黑',
			'padding': 10,
			'margin': 10,
			'border-radius': 10,
			'z-index': 999
		})
		msgEntity.hide();
	},
	show: function() {
		msgEntity.fadeIn(this.fadeTime);
		msgEntity.delay(this.showTime);
		msgEntity.fadeOut(this.fadeTime);
	}
}

/*
 * 获取地址栏参数
 */
var getUrlParam = function getUrlParam(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1));
    var sURLVariables = sPageURL.split('&');
    var sParamName, sParamValue, i, nameIndex;
    for (i = 0; i < sURLVariables.length; i++) {
    	nameIndex = sURLVariables[i].indexOf("=");
    	sParamName = sURLVariables[i].substring(0,nameIndex);
    	sParamValue = sURLVariables[i].substring(nameIndex+1, sURLVariables[i].length);
        if (sParamName === sParam) {
            return sParamName === undefined ? undefined : sParamValue;
        }
    }
};
