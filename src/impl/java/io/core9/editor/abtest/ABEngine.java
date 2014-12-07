package io.core9.editor.abtest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.core9.module.auth.AuthenticationPlugin;
import io.core9.module.auth.User;
import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.request.Request;
import io.core9.server.undertow.CookieImpl;

public class ABEngine {

	private static final String CORE9SESSIONID = "CORE9SESSIONID";
	private static final String ABCOOKIE = "CORE9DAT";

	public static Map<String, Object> getTestVars(AuthenticationPlugin auth, Request req) {
		String abSessionId = getSessionID(auth, req);
		Map<String, Object> testVars = getTestVarsFromStorage(abSessionId, req.getPath());
		return testVars;
	}

	private static Map<String, Object> getTestVarsFromStorage(String abSessionId, String path) {

		ABStorage storage = ABStorage.getInstance();

		Map<String, Object> data = new HashMap<String, Object>();

		// if new to path (test cache)
			// get test vars for this page
			// update test variation used


		data.put("path-version", storage.getPathVersion(abSessionId));


		return null;
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
