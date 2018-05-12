package com.ss.file.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.file.controller.BaseManageController;
import com.ss.file.controller.RedirectObject;
import com.ss.file.pojo.sys.Role;
import com.ss.file.service.sys.RoleService;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.exception.BusinessException;

@Controller
@RequestMapping("/ftp/role")
public class RoleController extends BaseManageController {

	@Autowired
	private RoleService roleService;
	
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
		PageEO pager = roleService.listByRole(pageParam);
		pager.setOrder(pageParam.getSort());
		request.setAttribute("pager", pager);
		request.setAttribute("param", pageParam.getWhere());
		return "system/role_list";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request) {
		request.setAttribute("isAdd", getIsAdd(request));
		request.setAttribute("role", null);
		return "system/role_input";
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, Long id) {
		request.setAttribute("isEdit", getIsEdit(request));
		Role role = roleService.selectByPrimaryKey(id);
		request.setAttribute("role", role);
		return "system/role_input";
	}

	@RequestMapping(value = "/delete")
	public String deleteRole(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String[] ids = request.getParameterValues("ids");
		roleService.deleteByPrimaryKey(ids);
		return ajax(response, Status.success, "删除成功!");
	}
	
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request) throws BusinessException {
		String[] permissionIds = request.getParameterValues("permissionId");
		PageParam pageParam = super.url2PageParam(request);
		Map entityMap = pageParam.getWhere();
		//根据对象id判断用户操作是新增还是修改
		if (entityMap.get("seqId") == null || entityMap.get("seqId").toString().equals("")) {
			if (permissionIds != null) {
				entityMap.put("permissionIds", permissionIds);
			}
			int i = roleService.insertByParam(entityMap);
			redirectObject.setLogInfo("[" + entityMap.get("roleName").toString() + "]角色信息新增成功 !");
			redirectObject.setRedirectUrl("ftp/role/dataList");
			request.setAttribute("redirectObject", redirectObject);
		} else {
			if (permissionIds != null) {
				entityMap.put("permissionIds", permissionIds);
			}
			int i = roleService.updateByParam(entityMap);
			redirectObject.setLogInfo("[" + entityMap.get("roleName").toString() + "]角色信息修改成功 !");
			redirectObject.setRedirectUrl("ftp/role/dataList");
			request.setAttribute("redirectObject", redirectObject);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询分页数据
	 * 
	 * @param userEO
	 * @param pageParam
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Object getList(HttpServletRequest request) throws BusinessException {
		PageParam pageParam = super.url2PageParam(request);
		if (pageParam.getOrder() == null || pageParam.getOrder().equals("")) {
			pageParam.setOrder("seq_id");
			pageParam.setSort("desc");
		}
		return roleService.listByRole(pageParam);
	}

}
