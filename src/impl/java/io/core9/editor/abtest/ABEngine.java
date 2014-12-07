package io.core9.editor.abtest;

import io.core9.module.auth.AuthenticationPlugin;
import io.core9.module.auth.User;
import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.request.Request;
import io.core9.server.undertow.CookieImpl;

public class ABEngine {

	private static final String ABCOOKIE = "abtest";

	public static String getTestVars(AuthenticationPlugin auth, Request req) {
		String test = null;
		Cookie abCookie = req.getCookie(ABCOOKIE);
		if(abCookie != null){
			test = abCookie.getValue();
		}


		if(test == null){
			Cookie ckie = req.getCookie("CORE9SESSIONID");
			User user = null;
			if(ckie == null){
				user = auth.getUser(req);
			}else{
				user = auth.getUser(req, ckie);
			}

			test = (String) user.getSession().getAttribute(ABCOOKIE);
			if(test == null){
				user.getSession().setAttribute(ABCOOKIE, "aaa");
				req.getResponse().addCookie(new CookieImpl(new io.undertow.server.handlers.CookieImpl(ABCOOKIE, "aaa")));
			}
		}
		return test;
	}


}
