package com.yuefanba.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.yuefanba.config.Protocol;
import com.yuefanba.model.User;

/**
 * 用户登录验证拦截器，当用户点击进入个人中心时需验证
 * @author SongDaBin
 *
 */
public class UserAuthInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		HttpServletRequest request = controller.getRequest();
		String requestUrl = request.getRequestURL().toString();
		User user = (User)controller.getSessionAttr("loginUser");
		if (user != null && user.getInt("userType") == Protocol.USER_TYPE_OF_USER) {
			ai.invoke();
		} else {
			String url = request.getAttribute("basePath") + "login?initTip=loginFirst&url=" + requestUrl;
			controller.redirect(url);
		}
	}
}
