package com.ss.file.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ss.file.util.JsonUtil;
import com.ss.platform.base.controller.BaseController;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.exception.BusinessException;
import com.ss.platform.util.StringUtil;

public class BaseManageController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(BaseManageController.class);
	private static final boolean HEADER_NO_CACHE = true;
	public static final String SUCCESS = "common/success";         //操作完毕，跳转到成功页面
	public static final String ERROR = "common/error";         //操作失败，跳转到失败页面
	private static final String HEADER_JSON_CONTENT_TYPE = "text/plain";
	private static final String HEADER_ENCODING = "UTF-8";
	public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
	public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称

	// 操作状态（警告、错误、成功）
	public enum Status {
		warn, error, success
	}

	/**
	 * 从request中获取参数集合 封装成PageParam对象
	 * 
	 * 注意:url中传递的参数名字不重复 如果重复 取第一个值
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	protected PageParam url2PageParam(HttpServletRequest request) throws BusinessException {
		// 分页四个参数固定
		Map<String, String[]> map = request.getParameterMap();
		PageParam pageParam = new PageParam();
		Map<String, Object> bm = new HashMap<String, Object>();

		String key, value;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue().length >= 1 ? map.get(key)[0].trim() : "";
			if (StringUtil.equals(key, "page")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setPage(Integer.parseInt(value));
				}
				continue;
			} else if (StringUtil.equals(key, "pageNumber")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setPage(Integer.parseInt(value));
				}
				continue;
			} else if (StringUtil.equals(key, "rows")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setRows(Integer.parseInt(value));
				}
				continue;
			} else if (StringUtil.equals(key, "pageSize")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setRows(Integer.parseInt(value));
				}
				continue;
			} else if (StringUtil.equals(key, "order")) {
				if (!StringUtil.isEmpty(value)) {
					if (value != null && value.equals("desc")) {
						pageParam.setSort("desc");
					} else {
						pageParam.setSort("asc");
					}
				}
				continue;
			} else if (StringUtil.equals(key, "orderBy")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setOrder(value);
				}
				continue;
			}
			try {
				// bm.put(key, new String(value.getBytes("ISO8859-1"),
				// "UTF-8")); // 更新:
				// liuwz
				// 2015-04-01
				bm.put(key, value);
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("==================转换字符串出错=================");
			}
		}

		pageParam.setWhere(bm);
		return pageParam;
	}

	/**
	 * 从request中获取参数集合 封装成PageParam对象
	 * 
	 * 注意:url中传递的参数名字不重复 如果重复 取第一个值
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	protected PageParam url2PageParamByGBK(HttpServletRequest request) throws BusinessException {
		// 分页四个参数固定
		Map<String, String[]> map = request.getParameterMap();
		PageParam pageParam = new PageParam();
		Map<String, Object> bm = new HashMap<String, Object>();

		String key, value;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue().length >= 1 ? map.get(key)[0].trim() : null;
			if (StringUtil.equals(key, "page")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setPage(Integer.parseInt(value));
				}
				continue;
			} else if (StringUtil.equals(key, "rows")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setRows(Integer.parseInt(value));
				}
				continue;
			} else if (StringUtil.equals(key, "sort")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setSort(value);
				}
				continue;
			} else if (StringUtil.equals(key, "order")) {
				if (!StringUtil.isEmpty(value)) {
					pageParam.setOrder(value);
				}
				continue;
			}
			try {
				bm.put(key, new String(URLDecoder.decode(value, "utf-8")));
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("==================转换字符串出错=================");
			}
		}

		pageParam.setWhere(bm);
		return pageParam;
	}

	// 根据操作状态、消息内容输出AJAX
	protected String ajax(HttpServletResponse response, Status status, String message) {
		initResponse(response, HEADER_JSON_CONTENT_TYPE);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
		jsonMap.put(MESSAGE_PARAMETER_NAME, message);
		JsonUtil.toJson(response, jsonMap);
		return null;
	}

	/**
	 * 输出参数
	 * 
	 * @param response
	 * @param map
	 */
	protected void jsonOutPut(HttpServletResponse response, Map<String, Object> map) {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = JSONObject.fromObject(map).toString();
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("BaseB2BController jsonOutPut IOException:" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 初始化请求头
	 * 
	 * @param response
	 * @param contentType
	 * @return
	 */
	private HttpServletResponse initResponse(HttpServletResponse response, String contentType) {
		response.setContentType(contentType + ";charset=" + HEADER_ENCODING);
		if (HEADER_NO_CACHE) {
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		return response;
	}

	// 判断是否为添加
	public Boolean getIsAdd(HttpServletRequest request) {
		String id = request.getParameter("seqId");
		if (id == null) {
			return true;
		} else {
			return false;
		}
	}

	// 判断是否为编辑
	public Boolean getIsEdit(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (id != null) {
			return true;
		} else {
			return false;
		}
	}
}
