package com.ss.file.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ss.file.controller.BaseManageController;
import com.ss.file.controller.RedirectObject;
import com.ss.file.pojo.sys.SysUserEO;
import com.ss.file.service.sys.SysUserService;
import com.ss.file.util.PropertyUtil;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.exception.BusinessException;
import com.ss.platform.util.MD5;

@Controller
@RequestMapping("/ftp/sysUser")
public class SysUserController extends BaseManageController {

	@Autowired
	private SysUserService sysUserService;
	
	private RedirectObject redirectObject = new RedirectObject();
	
	public RedirectObject getRedirectObject() {
		return redirectObject;
	}

	public void setRedirectObject(RedirectObject redirectObject) {
		this.redirectObject = redirectObject;
	}

	/**
	 * 查询分页数据
	 * 
	 * @param userEO
	 * @param pageParam
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/dataList")
	public String dataList(HttpServletRequest request) throws BusinessException {
		PageParam pageParam = super.url2PageParam(request);
		if (pageParam.getOrder() == null || pageParam.getOrder().equals("")) {
			pageParam.setOrder("seq_id");
			pageParam.setSort("desc");
		}
		PageEO pager = sysUserService.listBySystemUser(pageParam);
		pager.setOrder(pageParam.getSort());
		request.setAttribute("pager", pager);
		request.setAttribute("param", pageParam.getWhere());
		return "system/systemuser_list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request) {
		request.setAttribute("isAdd", getIsAdd(request));
		request.setAttribute("systemUser", null);
		return "system/systemuser_input";
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, Integer id) {
		request.setAttribute("isEdit", getIsEdit(request));

		SysUserEO eo = sysUserService.selectByPrimaryKey(id);
		request.setAttribute("systemUser", eo);
		return "system/systemuser_input";
	}

	@RequestMapping(value = "/delete")
	public String deleteSystemUser(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String[] ids = request.getParameterValues("ids");
		sysUserService.deleteByPrimaryKey(ids);
		return ajax(response, Status.success, "删除成功!");
	}
	
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, SysUserEO user) throws BusinessException {
		String[] roleIds = request.getParameterValues("roleId");
		PageParam pageParam = super.url2PageParam(request);
		Map entityMap = pageParam.getWhere();
		//根据对象id判断用户操作是新增还是修改
		if (entityMap.get("seqId") == null || entityMap.get("seqId").toString().equals("")) {
			if (roleIds != null) {
				entityMap.put("roleIds", roleIds);
			}
			int i = sysUserService.insertByParam(entityMap);
			redirectObject.setLogInfo("[" + entityMap.get("sso_login").toString() + "]系统用户信息新增成功 !");
			redirectObject.setRedirectUrl("ftp/sysUser/dataList");
			request.setAttribute("redirectObject", redirectObject);
		} else {
			if (roleIds != null) {
				entityMap.put("roleIds", roleIds);
			}
			int i = sysUserService.updateByParam(entityMap);
			redirectObject.setLogInfo("[" + entityMap.get("sso_login").toString() + "]系统用户信息修改成功 !");
			redirectObject.setRedirectUrl("ftp/sysUser/dataList");
			request.setAttribute("redirectObject", redirectObject);
		}
		return SUCCESS;
	}

	/**
	 * 登录验证
	 * 
	 * @param userid
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("=========================进入登录Controlle===========================");

		if (request.getParameter("z_Name") != null && request.getParameter("z_Pass") != null) {

			SysUserEO userEO = new SysUserEO();
			userEO.setLoginName(request.getParameter("z_Name"));
			userEO.setLoginPassword(request.getParameter("z_Pass"));

			MD5 md5 = new MD5();
			List<SysUserEO> list = sysUserService.loadByUsername(userEO.getLoginName());
			if (list == null || list.size() < 1) {
				request.setAttribute("error", "登录失败,非法的用户名.");
				request.getRequestDispatcher(("/resource/jsp/admin/index.jsp")).forward(request, response);
				return null;
			} else {
				SysUserEO eo = list.get(0);
				Date date = eo.getLoginTime();
				eo.setLoginIp(request.getRemoteAddr());
				if (eo.getLoginPassword().equals(md5.getMD5ofStr(userEO.getLoginPassword()))) {
					eo.setState(1);
					eo.setLoginTime(new Date());
					eo.setLoginFailureCount(0);
					sysUserService.updateByPrimaryKey(eo);

					addCookie(response, PropertyUtil.loadProperties(PropertyUtil.COOKIE_NAME),
							eo.getLoginName() + "@@@" + eo.getUserName() + "@@@" + java.net.URLEncoder.encode(eo.getDeptName(), "UTF-8"), 3 * 60 * 60);
				} else {
					if (date == null) {
						eo.setLoginFailureCount(1);
					} else
						eo.setLoginFailureCount(eo.getLoginFailureCount() == null ? 1 : eo.getLoginFailureCount().intValue() + 1);
					eo.setState(1);
					sysUserService.updateByPrimaryKey(eo);

					request.setAttribute("error", "您已经连续错误输入密码" + eo.getLoginFailureCount() + "次");
					request.getRequestDispatcher("/resource/jsp/admin/index.jsp").forward(request, response);
					return null;
				}
				SysUserEO userInfo = new SysUserEO();
				if (request.getHeader("x-forwarded-for") == null) {
					userInfo.setLoginIp(request.getRemoteAddr());
				} else
					userInfo.setLoginIp(request.getHeader("x-forwarded-for"));

				String ip = request.getHeader("x-forwarded-for");
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
				System.out.println("========================ip is :" + ip);

				// request.getRequestDispatcher("/resource/jsp/common/page_main.jsp").forward(request,response);
				response.sendRedirect(PropertyUtil.loadProperties(PropertyUtil.SYSTEM_CONTEXT) + "ftp");
				return null;
			}
		} else
			request.setAttribute("error", "登录失败,用户名及密码不能为空！");
		request.getRequestDispatcher("/resource/jsp/admin/index.jsp").forward(request, response);
		return null;
	}

	// 增加一个cookie信息
	private static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	public static void main(String[] args) {
		String sysCode = "app_c_session";
		MD5 md5 = new MD5();
		String genStr = md5.getMD5ofStr(sysCode);
		System.out.println("3650B830B95AF9A47D08605C3074C04A");
		System.out.println(genStr);
	}

}
