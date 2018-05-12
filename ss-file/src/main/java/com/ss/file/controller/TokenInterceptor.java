package com.ss.file.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ss.platform.util.MD5;

/**
 * token及签名拦截器
 * @author lijh
 *
 */
public class TokenInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
	
	private static final String SYS_CODE = "app_c_session";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse resp, Object handler) throws Exception {
		
		// 判断token合法性======================================================
		MD5 md5 = new MD5();
		String appToken = "";
		String appSign = "";
		String sysSign = "";
		
		Map<String, String> headerMap = getHeadersInfo(request); // 获取请求头信息,保存到headerMap中
		appToken = headerMap.get("app_token");
		appSign = headerMap.get("sign");
		
		logger.debug("app_token:" + appToken);
		logger.debug("sign:" + appSign);
		
		// 身份参数为空,不允许访问系统接口
		if (appToken == null || appSign == null || "".equals(appSign)) {
			return false;
		}
		
		sysSign = md5.getMD5ofStr(appToken + SYS_CODE); // 根据用户token信息和系统编码进行MD5加密,与app的签名字段信息进行对比,一致则认为合法访问,否则,非法访问.
		
		logger.info("############requestSign：" + appSign);
		logger.info("############sysSign：" + sysSign);
		
		if (appToken != null && appSign != null && !appSign.equals(sysSign)) {
			return false;
		}
		logger.info("===================================================token校验通过=============================================");
		return true;
	}

	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception {
	
	}
	
	/**
	 * 获取Request中的Header参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
