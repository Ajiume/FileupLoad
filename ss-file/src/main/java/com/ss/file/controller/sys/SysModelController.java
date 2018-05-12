package com.ss.file.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.file.controller.BaseManageController;
import com.ss.file.controller.RedirectObject;
import com.ss.file.pojo.sys.SysModel;
import com.ss.file.service.sys.SysModelService;
import com.ss.file.util.CookieUtil;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.exception.BusinessException;

@Controller
@RequestMapping("/sys/model")
public class SysModelController extends BaseManageController {

	@Autowired
	private SysModelService modelService;
	

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
			pageParam.setOrder("model_id");
			pageParam.setSort("desc");
		}

		PageEO pager = modelService.listSysModel(pageParam);
		pager.setOrder(pageParam.getSort());
		request.setAttribute("pager", pager);
		request.setAttribute("param", pageParam.getWhere());
		return "sys/model_list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request) throws BusinessException {
		request.setAttribute("isAdd", true);
		request.setAttribute("sysModel", null);
		return "sys/model_input";
	}
	

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, Integer modelId) throws BusinessException {
		request.setAttribute("isAdd", false);
		SysModel sysModel = modelService.selectByPrimaryKey(modelId);
		request.setAttribute("sysModel", sysModel);
		return "sys/model_input";
	}
	
	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Integer modelId) throws BusinessException {
		SysModel sysModel = modelService.selectByPrimaryKey(modelId);
		request.setAttribute("sysModel", sysModel);
		return "sys/model_detail";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String[] ids = request.getParameterValues("ids");
		modelService.deleteByPrimaryKey(ids);
		return ajax(response, Status.success, "删除成功!");
	}
	

	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, SysModel fileMain) throws BusinessException {
		PageParam pageParam = super.url2PageParam(request);
		Map<String, Object> map = pageParam.getWhere();
		Date date = new Date();
		String operator = CookieUtil.getLoginUser(request);
		// id为空，说明是新增；否则为修改
		if (map.get("modelId") == null || map.get("modelId").equals("")) {
			map.put("createTime", date);
			map.put("createUser", operator);
			modelService.insertByParams(map);
			redirectObject.setLogInfo("[" + map.get("modelName") + "]模块信息新增成功 !");
		} else {
			map.put("updateTime", date);
			map.put("updateUser", operator);
			modelService.updateByParams(map);
			redirectObject.setLogInfo("[" + map.get("modelName") + "]模块信息修改成功 !");
		}
		redirectObject.setRedirectUrl("sys/model/dataList");
		request.setAttribute("redirectObject", redirectObject);
		return SUCCESS;
	}
	
	/**
	 * 判断模块名称是否唯一
	 */
	@ResponseBody
	@RequestMapping("checkModelNameUnique")
	public boolean checkModelNameUnique(HttpServletRequest request) {
		String modelName = request.getParameter("modelName");
		String originalModelName = request.getParameter("originalModelName");
		SysModel model = new SysModel();
		model.setModelName(modelName);
		List<SysModel> models = modelService.selectByParams(model);
		if (models == null || models.size() == 0) {
			return true;
		}
		if (originalModelName.equals("")) {
			return false;
		}
		if (models.size() == 1 && modelName.equals(originalModelName)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断模块路径是否唯一
	 */
	@ResponseBody
	@RequestMapping("checkModelPathUnique")
	public boolean checkModelPathUnique(HttpServletRequest request) {
		String modelPath = request.getParameter("modelPath");
		String originalModelPath = request.getParameter("originalModelPath");
		SysModel model = new SysModel();
		model.setModelPath(modelPath);
		List<SysModel> models = modelService.selectByParams(model);
		if (models == null || models.size() == 0) {
			return true;
		}
		if (originalModelPath.equals("")) {
			return false;
		}
		if (models.size() == 1 && modelPath.equals(originalModelPath)) {
			return true;
		}
		return false;
	}
	
}
