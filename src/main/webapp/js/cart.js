// 定义购物车
var Cart = function(shopId, startPrice, incomePrice) {
	this.shopId = shopId;  // 商家id
	this.foodList = [];	// 菜品信息列表
	this.startPrice = parseFloat(startPrice);  // 起送价
	this.incomePrice = parseFloat(incomePrice);  // 配送价
	this.amount = parseFloat(0);  // 购物车总价
	this.getName = "";	// 收货人姓名
	this.orderAddr = "";	// 收货人地址
	this.orderTel = "";	// 收货人电话
	this.payWay = 1; // 支付方式
}
/*
 * 根据foodID查找该Food，若存在则返回其下标，若不存在则返回-1；
 */
Cart.prototype.findFood = function(foodId) {
	var itemCount = this.foodList.length;
	for (i = 0; i < itemCount; i ++) {
		if (this.foodList[i].foodId == foodId) {
			return i;
		}
	}
	return -1;
}
/*
 * 向购物车添加food的方法
 * return -1：最多只能添加20个菜品；-2：每个菜品最多点10份
 */
Cart.prototype.addFood = function(foodId, foodName, foodPrice) {
	if (this.foodList.length >= 20) {
		return -1;
	}
	var index = this.findFood(foodId);
	if (index == -1) {
		var food = {
				foodId: foodId,
				foodName: foodName,
				foodPrice: parseFloat(foodPrice),
				foodCount: parseInt(1),
				foodAmount: parseFloat(foodPrice)
		}
		if (this.foodList.length==0) {
			this.foodList.push(food);
		} else {
			// 添加至数组的第一个位置
			this.foodList.length ++;
			for (i = this.foodList.length-1; i > 0; i --) {
				this.foodList[i] = this.foodList[i-1];
			}
			this.foodList[0] = food;
		}
	} else {
		if (this.foodList[index].foodCount >= 20) {
			return -2;
		}
		this.foodList[index].foodCount ++;
		this.foodList[index].foodAmount += this.foodList[index].foodPrice;
	}
	this.refresh();
	return true;
}
/*
 * 从购物车中移除food的方法
 */
Cart.prototype.removeFood = function(foodId) {
	var index = this.findFood(foodId);
	if (index==-1)
		return;
	// 删除food数组中下标为index的元素
	for (var i = index; i < this.foodList.length -1 ; i ++) {
		this.foodList[i] = this.foodList[i+1]; 
	}
	this.foodList.length --;
	this.refresh();
}

/*
 * 增加购物车中某个Food的数量
 */
Cart.prototype.addFoodCount = function(foodId) {
	var index = this.findFood(foodId);
	if (index==-1) 
		return;
	if (this.foodList[index].foodCount >= 10)
		return false
	this.foodList[index].foodCount ++;
	this.foodList[index].foodAmount += this.foodList[index].foodPrice;
	this.refresh();
	return true;
}
/*
 * 减少购物车中某个Food的数量
 */
Cart.prototype.reduceFoodCount = function(foodId) {
	var index = this.findFood(foodId);
	if (index == -1) 
		return;
	if (this.foodList[index].foodCount > 1) { //如果food数量大于1则减一
		this.foodList[index].foodCount --;
		this.foodList[index].foodAmount -= this.foodList[index].foodPrice;
	} else { //否则从foodList中删除该food
		this.removeFood(foodId);
	}
	this.refresh();
}
/*
 * 清空购物车
 */
Cart.prototype.clear = function(){
	this.foodList.length = 0;
	this.amount = parseFloat(0);
	this.refresh();
}
/*
 * 结算购物车
 */
Cart.prototype.calculateAmount = function(){
	var tempAmount = parseFloat(0);
	for (i = 0; i < this.foodList.length; i ++) {
		tempAmount += this.foodList[i].foodAmount;
	}
	this.amount = tempAmount + this.incomePrice;
}
/*
 * 刷新购物车界面
 */
Cart.prototype.refresh = function(){
	this.calculateAmount();
	this.save();
	if (this.foodList.length < 1) {
		// 隐藏购物车头部和主体
		$("#cartHeader").hide();
		$("#cartPanel").hide();
		
		// 显示“购物车为空”
		$("#cartEmptyTip").show();
		$("#cartAmount").hide();
		$("#cartOrderBtn").hide();
		$("#cartLackingTip").hide();
		return;
	} else {
		// 显示购物车头部和主体
		$("#cartHeader").show();
		$("#cartPanel").show();
		// 加载购物车内容
		$("#cartItemList").empty();
		$("#cartItemTmpl").tmpl(this.foodList).appendTo("#cartItemList");
		$("#cartItemList").append($("#cartSendAddTmpl").html());
		$("#sendAddTd").text(this.incomePrice);
		$("#cartAmountValue").text(this.amount);
		$("#cartEmptyTip").hide();
		$("#cartAmount").show();
		// 金额小于起送价
		if (cart.amount < cart.startPrice) {
			$("#lackingPrice").text(this.startPrice - this.amount);
			$("#cartOrderBtn").hide();
			$("#cartLackingTip").show();
		} else {
			$("#cartOrderBtn").show();
			$("#cartLackingTip").hide();
		}	
	}
}

/*
 * 验证购物车是否可以提交成订单
 */
Cart.prototype.validate = function() {
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
	if (this.shopId == null || this.shopId == "") {
		return false;
	}
	if (this.foodList.length < 1) {
		return false;
	}
	if (this.getName == null || this.getName == "") {
		return false;
	}
	if (this.orderAddr == null && this.orderAddr == "") {
		return false;
	}
	if (re.test(this.orderTel)==false) {
		return false;
	}
	if (this.amount < 0) {
		return false;
	}
	return true;
}

/*
 * 将购物车信息保存至服务器端
 */
Cart.prototype.save = function(){
	var cartJsonStr = JSON.stringify(this);
	$.ajax({
		type: "POST",
		url: basePath + "shop/saveCart",
		data: {
			cartJsonStr: cartJsonStr
		},
		success: function(data){
			
		}
	});
}

/*
 * 购物车信息下单
 */
Cart.prototype.saveOrder = function() {
	if (!this.validate()) {
		return;
	}
	$.ajax({
		type: "POST",
		url: basePath + "shop/saveOrder",
		data: {
			cartJsonStr: JSON.stringify(this)
		}
	});
}