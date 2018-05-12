package com.ss.file.controller.ftp;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss.file.controller.BaseManageController;
import com.ss.file.controller.RedirectObject;
import com.ss.file.pojo.ftp.FileFtpConf;
import com.ss.file.service.ftp.FileFtpConfService;
import com.ss.file.util.CookieUtil;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.exception.BusinessException;

@Controller
@RequestMapping("/ftp/conf")
public class FtpConfController extends BaseManageController {

	@Autowired
	private FileFtpConfService confService;
	

	private RedirectObject redirectObject = new RedirectObject();

	public RedirectObject getRedirectObject() {
		return redirectObject;
	}

	public void setRedirectObject(RedirectObject redirectObject) {
		this.redirectObject = redirectObject;
	}

	
	@RequestMapping("/dataList")
	public String dataList(HttpServletRequest request) throws BusinessException {
		PageParam pageParam = super.url2PageParam(request);
		if (pageParam.getOrder() == null || pageParam.getOrder().equals("")) {
			pageParam.setOrder("conf_id");
			pageParam.setSort("desc");
		}

		PageEO pager = confService.listFileFtpConf(pageParam);
		pager.setOrder(pageParam.getSort());
		request.setAttribute("pager", pager);
		request.setAttribute("param", pageParam.getWhere());
		return "ftp/conf_list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request) throws BusinessException {
		request.setAttribute("isAdd", true);
		request.setAttribute("fileFtpConf", null);
		return "ftp/conf_input";
	}
	

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, Integer confId) throws BusinessException {
		request.setAttribute("isAdd", false);
		FileFtpConf fileFtpConf = confService.selectByPrimaryKey(confId);
		request.setAttribute("fileFtpConf", fileFtpConf);
		return "ftp/conf_input";
	}
	
	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Integer confId) throws BusinessException {
		FileFtpConf fileFtpConf = confService.selectByPrimaryKey(confId);
		request.setAttribute("fileFtpConf", fileFtpConf);
		return "ftp/conf_detail";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String[] ids = request.getParameterValues("ids");
		confService.deleteByPrimaryKey(ids);
		return ajax(response, Status.success, "删除成功!");
	}
	

	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, FileFtpConf black) throws BusinessException {
		PageParam pageParam = super.url2PageParam(request);
		Map<String, Object> map = pageParam.getWhere();
		Date date = new Date();
		String operator = CookieUtil.getLoginUser(request);
		// id为空，说明是新增；否则为修改
		if (map.get("confId") == null || map.get("confId").equals("")) {
			map.put("createTime", date);
			map.put("createUser", operator);
			confService.insertByParams(map);
			redirectObject.setLogInfo("[" + map.get("ftpAddress") + "]ftp配置信息新增成功 !");
		} else {
			map.put("updateTime", date);
			map.put("updateUser", operator);
			confService.updateByParams(map);
			redirectObject.setLogInfo("[" + map.get("ftpAddress") + "]ftp配置信息修改成功 !");
		}
		redirectObject.setRedirectUrl("ftp/conf/dataList");
		request.setAttribute("redirectObject", redirectObject);
		return SUCCESS;
	}
	
}
