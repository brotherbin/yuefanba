
/**
 * 用户注册
 */

function regist(){
	var userName=$.trim($("#username").val());
	var password=$.trim($("#newpwd").val());
	var rePassword=$.trim($("#repwd").val());
	var userType=$.trim($("#usertype").val());
	var validate=$.trim($("#txtvali").val());
	if(userName==null||userName==""){
		new Toast("用户名不能为空！").show();
		return;
	}
	else if(password==null||password==""){
		new Toast("密码不能为空").show();
		return;
	}
	else if(rePassword==null||rePassword==""){
		new Toast("请输入确认密码").show();
		return;
	}
	else if(validate==null||validate==""){
		new Toast("请输入验证码！").show();
		return;
	}
	else if(password!=rePassword){
		new Toast("两次密码不一致！").show();
		return;
	}
	$.ajax({
		type: "POST",
		url:basePath+ "regist/save",
		dataType: "json",
		data: {
			userName: userName,
			password: password,
			validate:validate,
			userType:userType
		},
		success:function(data){
			if(data.msg3=="failed"){
				new Toast("用户名已存在！").show();
			}
			else if (data.state == "ok") {
				// 注册成功回主页
				window.location.href = basePath;
				// basePath = http://localhost:8080/yuefanba
			} else if (data.msg1 == "failed"){
				// 注册失败提示
				new Toast("注册失败，请重新注册！").show();
			}
			else if(data.msg2=="failed"){
				new Toast("验证码错误！").show();
			}
		}
	});
}

/**
 * 判断用户名长度
 * @param me
 */
function checkName(me){
	if($(me).val().trim().length>=12){
		new Toast("用户名不能超出12个字符").show();
	}
}

/**
 * 判断确认密码长度
 * @param me
 */
function checkPassword(me){
	if($(me).val().trim().length>=16){
		new Toast("密码不能超过16个字符").show();
	}
}
/**
 * 判断确认密码长度
 * @param me
 */
function checkRePassword(me){
	if($(me).val().trim().length>=16){
		new Toast("确认密码不能超过16个字符").show();
	}
}


