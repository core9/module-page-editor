package io.core9.editor.abtest;

import java.util.UUID;

import io.core9.module.auth.AuthenticationPlugin;
import io.core9.module.auth.User;
import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.request.Request;
import io.core9.server.undertow.CookieImpl;

public class ABEngine {

	private static final String CORE9SESSIONID = "CORE9SESSIONID";
	private static final String ABCOOKIE = "CORE9DAT";

	public static String getTestVars(AuthenticationPlugin auth, Request req) {
		String abSessionId = getSessionID(auth, req);
		// attach test data
		return abSessionId;
	}

	public static String getSessionID(AuthenticationPlugin auth, Request req) {
		Cookie abCookie = req.getCookie(ABCOOKIE);
		if (abCookie != null) {
			return abCookie.getValue();
		}
		String uuid = UUID.randomUUID().toString();
		String abSessionId = null;
		User user = getUser(auth, req);
		abSessionId = (String) user.getSession().getAttribute(ABCOOKIE);
		if (abSessionId == null) {
			user.getSession().setAttribute(ABCOOKIE, uuid);
			req.getResponse().addCookie(new CookieImpl(new io.undertow.server.handlers.CookieImpl(ABCOOKIE, uuid).setPath("/")));
		} else {
			req.getResponse().addCookie(new CookieImpl(new io.undertow.server.handlers.CookieImpl(ABCOOKIE, abSessionId).setPath("/")));
		}
		return abSessionId;
	}

	private static User getUser(AuthenticationPlugin auth, Request req) {
		if (req.getCookie(CORE9SESSIONID) == null) {
			return auth.getUser(req);
		}
		return auth.getUser(req, req.getCookie(CORE9SESSIONID));
	}

}
