package com.github.xwanlion.lifeauctioneer.util;

import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.cookie.Cookie;

import java.util.List;
import java.util.UUID;

/**
 * Cookie 辅助类
 * 
 */
public class CookieUtils {

	public final static String SESSION_ID_KEY = "JSESSIONID";

	public final static String SOCKET_TAG_KEY = "SOCKET_TAG";

	/**
	 * 获得cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie name
	 * @return if exist return cookie, else return null.
	 */
	public static Cookie getCookie(HttpRequest request, String name) {
		List<Cookie> cookies = request.getCookies();
		if (cookies != null && cookies.size() > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param expiry
	 * @param domain
	 * @return
	 */
	public static Cookie addCookie(HttpRequest request, HttpResponse response, String name, String value, Integer expiry, String domain) {
		Cookie cookie = new Cookie(name, value);
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		if (domain!= null && domain.length() > 0) {
			cookie.setDomain(domain);
		}

		String ctx = request.getPath();
		if (ctx == null || ctx.length() == 0) ctx = "/";
		cookie.setPath( ctx);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * 取消cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void cancelCookie(HttpRequest request, HttpResponse response, String name, String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getPath();
		if (ctx == null || ctx.length() == 0) ctx = "/";
		cookie.setPath(ctx);
		if (domain!= null && domain.length() > 0) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	public static void setSessionId(HttpRequest request, HttpResponse response) {
		String uuid = UUID.randomUUID().toString();
		CookieUtils.addCookie(request, response, CookieUtils.SESSION_ID_KEY, uuid, null, null);
	}

	public static String getSessionId(HttpRequest request, HttpResponse response) {
		Cookie sessionId = CookieUtils.getCookie(request, SESSION_ID_KEY);
		return sessionId.getValue();
	}

}
