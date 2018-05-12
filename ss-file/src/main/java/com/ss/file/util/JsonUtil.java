/*
 * @(#)JsonUtil.java 2012-4-20
 *
 * Copyright 2012 SH-BBMF,Inc. All rights reserved.
 */
package com.ss.file.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.Assert;

/**
 * 
 * json工具类
 */
public class JsonUtil {
	private static Log logger = LogFactory.getLog(JsonUtil.class);

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param object
	 *            对象
	 */
	public static void toJson(HttpServletResponse response, Object value) {
		Assert.notNull(response);
		Assert.notNull(value);
		try {
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Map对象转化为JSON字符串
	 * 
	 * @param map
	 * @param resultCode
	 * @return
	 */
	public static String toJsonStr(Map<String, Object> resultMap) throws Exception {
		if (resultMap == null) {
			return null;
		}
		return JSONObject.fromObject(resultMap).toString();
	}

	/**
	 * 对象转化为JSON字符串
	 * 
	 * @param resultObj
	 * @return
	 * @throws Exception
	 */
	public static String toJsonStr(Object resultObj) throws Exception {
		if (resultObj == null) {
			return null;
		}
		return JSONObject.fromObject(resultObj).toString();
	}

	/**
	 * JSON字符串转Map json格式:{"name":"admin","title":"123"}
	 */
	@SuppressWarnings({"rawtypes"})
	public static HashMap<String, String> jsonToMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		// 将json字符串转换成jsonObject
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = (String) jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}
	/**
	 * JSON字符串转Map json格式:{"name":"admin","title":"123"}
	 */
	/*
	 * @SuppressWarnings({"rawtypes", "unchecked" }) public static List<Object>
	 * jsonToList(Object object,Class<?> beanClass) { // HashMap<String, String>
	 * data = new HashMap<String, String>(); // 将json字符串转换成jsonObject
	 * List<Object> list = new ArrayList<Object>(); try { JSONArray jsonArray =
	 * JSONArray.fromObject(object);
	 * 
	 * List<Map> mapListJson = (List)jsonArray; for (int i = 0; i <
	 * mapListJson.size(); i++) { Map<String,Object> obj=mapListJson.get(i);
	 * CompanyVO company = (CompanyVO) BeanMapUtils.getMapToBean(obj,
	 * beanClass); list.add(company); }
	 * 
	 * return list; } catch (Exception e) {
	 * logger.error("JsonUtil jsonToList error : "+e); } return null; }
	 */

	/**
	 * 对象转化为JSON字符串
	 * 
	 * @param resultObj
	 * @return
	 * @throws Exception
	 */
	public static String toJsonStrNotNull(Object resultObj) throws Exception {
		// if (resultObj == null) {
		// return "";
		// }
		// JSON j=(JSON)JSON.toJSON(resultObj);
		return null;
	}

	/**
	 * jsonStr转换为Bean类
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String jsonStr, Class<T> beanCalss) throws Exception {
		if (StringUtils.isBlank(jsonStr)) {
			logger.error("jsonStr is null.");
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return (T) JSONObject.toBean(jsonObject, beanCalss);
	}

	/**
	 * jsonStr转换为Bean类
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T toComplexBean(String jsonStr, Class<T> beanCalss, Map<String, Class> classMap) throws Exception {
		if (StringUtils.isBlank(jsonStr)) {
			logger.error("jsonStr is null.");
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return (T) JSONObject.toBean(jsonObject, beanCalss, classMap);
	}

	/**
	 * HttpServletRequest转换为Bean类
	 * 
	 * @param request
	 * @param beanCalss
	 * @return
	 */
	public static <T> T toBean(HttpServletRequest request, Class<T> beanCalss) throws Exception {

		String jsonStr = getJsonStrFromRequest(request);
		if (StringUtils.isBlank(jsonStr)) {
			logger.error("jsonStr is null.");
			return null;
		}
		return toBean(jsonStr, beanCalss);
		/*
		 * if (isJsonRequest(request)) { String jsonStr =
		 * getJsonStrFromRequest(request); if (StringUtils.isBlank(jsonStr)) {
		 * logger.error("jsonStr is null."); return null; } return
		 * toBean(jsonStr, beanCalss); } else { T obj = beanCalss.newInstance();
		 * BeanUtils.populate(obj, request.getParameterMap()); return obj; }
		 */}

	/**
	 * resquest对象转换为jsonStr
	 * 
	 * @param request
	 * @return
	 */
	public static String getJsonStrFromRequest(HttpServletRequest request) throws Exception {

		DataInputStream in = null;
		request.setCharacterEncoding("utf-8");
		System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		// String jsonStr = new String(br.readLine());
		String inputLine = null;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine).append("\n");
		}
		IOUtils.closeQuietly(in);
		logger.info("jsonStr=" + sb.toString());
		logger.info("request.getContentType()=" + request.getContentType());

		return sb.toString();
		/*
		 * if (isJsonRequest(request)) { DataInputStream in = null;
		 * request.setCharacterEncoding("utf-8");
		 * System.setProperty("sun.net.client.defaultConnectTimeout"
		 * ,String.valueOf(10000)); BufferedReader br = new BufferedReader(new
		 * InputStreamReader(request.getInputStream(), "UTF-8")); // String
		 * jsonStr = new String(br.readLine()); String inputLine = null;
		 * StringBuffer sb = new StringBuffer(); while ((inputLine =
		 * br.readLine()) != null) { sb.append(inputLine).append("\n"); }
		 * IOUtils.closeQuietly(in); logger.info("jsonStr="+sb.toString());
		 * logger.info("request.getContentType()="+request.getContentType());
		 * 
		 * return sb.toString(); } else { Map<String, Object> map = new
		 * HashMap<String, Object>(); BeanUtils.populate(map,
		 * request.getParameterMap()); String str = toJsonStr(map);
		 * 
		 * logger.info("jsonStr="+str);
		 * logger.info("request.getContentType()="+request.getContentType());
		 * return str; }
		 */}

	public static boolean isJsonRequest(HttpServletRequest request) {
		boolean isJsonRequest = false;
		if (StringUtils.isBlank(request.getContentType())) {
			isJsonRequest = false;
		}
		if (request.getContentType().startsWith("text/plain") || request.getContentType().startsWith("text/html") || request.getContentType().startsWith("application/json")) {
			isJsonRequest = true;
		}
		return isJsonRequest;
	}

	/**
	 * json数组转化java对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toArrayObject(String jsonStr, Class<T> beanCalss) throws Exception {
		List<T> list = new ArrayList<T>();
		if (jsonStr != null && !"".equals(jsonStr)) {
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			Collection<T> array = JSONArray.toCollection(jsonArray, beanCalss);
			if (array != null && !array.isEmpty()) {
				Iterator<T> iterator = array.iterator();
				while (iterator.hasNext()) {
					JSONObject jsonObject = JSONObject.fromObject(iterator.next());
					list.add((T) JSONObject.toBean(jsonObject, beanCalss));
				}
			}
		}
		return list;
	}

	/**
	 * json字符串数组转化java对象数组
	 */

	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonstringToObject(String jsonStr, String jsonArrayName, Class<T> beanCalss) throws Exception {
		List<T> list = new ArrayList<T>();
		try {
			if (jsonStr != null && !"".equals(jsonStr)) {
				// 获取一个json对象
				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				if (jsonObject.get(jsonArrayName) == null) {
					return null;
				} else {
					// 获取一个json数组
					JSONArray array = jsonObject.getJSONArray(jsonArrayName);
					// 将json数组转化为数组对象
					if (array != null && array.size() != 0) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject object = (JSONObject) array.get(i);
							T t = (T) JSONObject.toBean(object, beanCalss);
							if (t != null) {
								list.add(t);
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}

		return list;
	}

	/**
	 * json字符串数组中获取指定key值
	 */

	public static String jsonstringGetObject(String jsonStr, String key) throws Exception {
		String result = "";
		if (jsonStr != null && !"".equals(jsonStr)) {
			// 获取一个json对象
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			Object object = jsonObject.get(key);
			if (object != null) {
				result = (String) object;
			}
		}
		return result;
	}

}
