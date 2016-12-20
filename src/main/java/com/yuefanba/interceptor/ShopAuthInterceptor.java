package com.yuefanba.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.yuefanba.config.Protocol;
import com.yuefanba.model.User;

/**
 * 商家用户登录验证
 * @author 833901
 *
 */
public class ShopAuthInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();

	

		User user = (User)controller.getSessionAttr("loginUser");

		if (user != null && user.getInt("userType") == Protocol.USER_TYPE_OF_SHOP) {
			ai.invoke();
		} else {
			controller.redirect("/login");
		}
	}

}
