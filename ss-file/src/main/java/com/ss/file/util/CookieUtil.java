package com.ss.file.util;

import javax.servlet.http.HttpServletRequest;

import com.ss.common.utils.CookieUtils;

public class CookieUtil {

	/**
	 * 获取当前登录用户
	 */
	public static String getLoginUser(HttpServletRequest request) {
		String userInfoString = CookieUtils.getCookieValue(request,
				"standard_user_info");
		if (userInfoString != null) {
			String[] userInfo = userInfoString.split("@@@");
			return userInfo[0];
		}
		return "";
	}
}
