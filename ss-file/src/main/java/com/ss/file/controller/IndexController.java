package com.ss.file.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss.file.util.PropertyUtil;

/**
 * 首页框架控制器
 * @author liuwz
 * @since 2017-8-29
 *
 */
@Controller
@RequestMapping("/ftp")
public class IndexController extends BaseManageController {
	
	//系统后台管理中心
	@RequestMapping(value = "/main" )
	public String main(HttpServletRequest request){
		return "common/page_main";
	}
	
	// 后台Header
	@RequestMapping("/header")
	public String header() {
		return "common/page_header";
	}
	
	// 后台菜单(默认系统管理菜单)
	@RequestMapping("/confMenu")
	public String menu() {
		return "common/page_conf_menu";
	}
	// 后台中间(显示/隐藏菜单)
	@RequestMapping("/middle")
	public String middle() {
		return "common/page_middle";
	}
	
	// 后台首页
	@RequestMapping("/index")
	public String index() {
		return "common/page_index";
	}
	
	//注销后台
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie killMyCookie = new Cookie(PropertyUtil.loadProperties(PropertyUtil.COOKIE_NAME), null);  
		killMyCookie.setMaxAge(0);  
		killMyCookie.setPath("/");  
		response.addCookie(killMyCookie);
		response.sendRedirect(PropertyUtil.loadProperties(PropertyUtil.SYSTEM_CONTEXT) + "login");
		return null;
	}
	
	// 获取JAVA版本
	public String getJavaVersion() {
		return System.getProperty("java.version");
	}
	
	// 获取系统名称
	public String getOsName() {
		return System.getProperty("os.name");
	}
	
	// 获取系统构架
	public String getOsArch() {
		return System.getProperty("os.arch");
	}
	
	// 获取系统版本
	public String getOsVersion() {
		return System.getProperty("os.version");
	}
	
	// 获取Server信息
	public String getServerInfo(HttpServletRequest request) {
		return StringUtils.substring(request.getSession().getServletContext().getServerInfo(), 0, 30);
	}
	
	// 获取Servlet版本
	public String getServletVersion(HttpServletRequest request) {
		return request.getSession().getServletContext().getMajorVersion() + "." + request.getSession().getServletContext().getMinorVersion();
	}
	
}
