package com.ss.file.controller.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ss.common.pojo.VOResult;
import com.ss.common.utils.FtpUtil;
import com.ss.file.controller.BaseManageController;
import com.ss.file.controller.RedirectObject;
import com.ss.file.pojo.ftp.FileFtpConf;
import com.ss.file.pojo.ftp.FileMain;
import com.ss.file.pojo.sys.SysModel;
import com.ss.file.service.ftp.FileFtpConfService;
import com.ss.file.service.ftp.FileMainService;
import com.ss.file.service.sys.SysModelService;
import com.ss.file.util.ContentDescribeMap;
import com.ss.file.util.CookieUtil;
import com.ss.file.util.JsonUtil;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.exception.BusinessException;
import com.ss.platform.util.MD5;

@Controller
@RequestMapping("/ftp/file")
public class FileMainController extends BaseManageController {
	
	private static final int RESULT_CODE_SUCCESS = 1;
	
	private static final int RESULT_CODE_FAILURE = 0;

	@Autowired
	private FileMainService fileService;

	@Autowired
	private SysModelService modelService;

	@Autowired
	private FileFtpConfService confService;

	@Value("${file_root_path}")
	private String fileRootPath;
	
	@Value("${sys_code}")
	private String sysCode;
	
	@Value("${sys_path}")
	private String sysPath;

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
			pageParam.setOrder("fd_id");
			pageParam.setSort("desc");
		}

		PageEO pager = fileService.listFileMain(pageParam);
		pager.setOrder(pageParam.getSort());
		request.setAttribute("pager", pager);
		request.setAttribute("param", pageParam.getWhere());
		request.setAttribute("models", modelService.selectAll());
		request.setAttribute("types", new HashSet<>(ContentDescribeMap.contentDescribeMap.values()));
		return "ftp/file_list";
	}

	@RequestMapping(value = "/uploadPage")
	public String uploadPage(HttpServletRequest request) throws BusinessException {
		List<SysModel> models = modelService.selectAll();
		List<FileFtpConf> ftpConfs = confService.selectAll();
		request.setAttribute("models", models);
		request.setAttribute("ftpConfs", ftpConfs);
		return "ftp/file_upload";
	}

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, Integer fdId) throws BusinessException {
		request.setAttribute("isAdd", false);
		FileMain fileMain = fileService.selectByPrimaryKey(fdId);
		request.setAttribute("fileMain", fileMain);
		return "ftp/file_input";
	}

	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Integer fdId) throws BusinessException {
		FileMain fileMain = fileService.selectByPrimaryKey(fdId);
		request.setAttribute("fileMain", fileMain);
		return "ftp/file_detail";
	}

	/**
	 * 下载文件
	 * 
	 * @param fileId
	 * @return
	 * @throws URISyntaxException
	 * @throws FileNotFoundException
	 */
	@ResponseBody
	@RequestMapping(value = "/download/file/{fileId}")
	public ResponseEntity<Resource> serveFile(@PathVariable String fileId) throws URISyntaxException, FileNotFoundException {

		FileMain main = fileService.selectByPrimaryKey(Integer.valueOf(fileId));
		if (main != null) {
			Resource file = loadAsResource(main.getFdFilePath() + "/" + main.getFdDestName());
			if (file != null) {
				main.setDownloadSum((main.getDownloadSum() != null ? main.getDownloadSum() : 0) + 1);
				fileService.updateByPrimaryKeySelective(main);
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
			}
		}
		return null;
	}

	private Path load(String filename) throws URISyntaxException {
		return Paths.get(new URI("file:" + filename));
	}

	private Resource loadAsResource(String filename) throws URISyntaxException, FileNotFoundException {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
		} catch (MalformedURLException e) {
			throw new FileNotFoundException("找不到文件：" + filename);
		}
		return null;
	}

	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		String[] ids = request.getParameterValues("ids");
		String result = fileService.deleteByPrimaryKey(ids);
		return ajax(response, Status.success, result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/delete")
	public Map<String, Object> deleteApi(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		String resultMsg = "";
		
		// 判断token合法性======================================================
		MD5 md5 = new MD5();
		String appToken = "";
		String appSign = "";
		String sysSign = "";

		Map<String, String> headerMap = getHeadersInfo(request); // 获取请求头信息,保存到headerMap中
		appToken = headerMap.get("token");
		appSign = headerMap.get("sign");

		// 身份参数为空,不允许访问系统接口
		if (appToken == null || appSign == null || "".equals(appSign)) {
			result.put("result", RESULT_CODE_SUCCESS);
			result.put("msg", "没有权限访问");
			return result;
		}

		sysSign = md5.getMD5ofStr(appToken + sysCode); // 根据用户token信息和系统编码进行MD5加密,与app的签名字段信息进行对比,一致则认为合法访问,否则,非法访问.

		logger.info("############requestSign：" + appSign);
		logger.info("############sysSign：" + sysSign);

		if (appToken != null && appSign != null && !appSign.equals(sysSign)) {
			resultMsg = "没有权限访问.";
			result.put("msg", resultMsg);
			result.put("result", "0");
			return result;
		}
		logger.info("===================================================token校验通过=============================================");
		String[] ids = request.getParameterValues("ids");
		String msg = "";
		try {
			msg = fileService.deleteByPrimaryKey(ids);
			result.put("result", RESULT_CODE_SUCCESS);
		} catch (Exception e) {
			result.put("result", RESULT_CODE_FAILURE);
			msg = e.getMessage();
			logger.error(e.getMessage());
		}
		result.put("data", "");
		result.put("msg", msg);
		return result;
	}

	@RequestMapping(value = "/localUpload")
	public String localUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file)
			throws BusinessException {
		if (request.getParameter("fdModelName") == null) {
			redirectObject.setLogInfo("请选择模块!");
			request.setAttribute("redirectObject", redirectObject);
			return ERROR;
		}

		List<FileFtpConf> confList = confService.selectAll();
		if (confList == null || confList.size() == 0) {
			redirectObject.setLogInfo("没找到FTP服务器!");
			request.setAttribute("redirectObject", redirectObject);
			return ERROR;
		}
		FileFtpConf conf = confList.get(0);

		String modelName = request.getParameter("fdModelName");
		SysModel paramModel = new SysModel();
		paramModel.setModelName(modelName);
		SysModel model = modelService.selectByParams(paramModel).get(0);
		boolean uploadSuccess = false;
		String fileSrcName = request.getParameter("fileName");
		if (fileSrcName == null || fileSrcName.equals("")) {
			fileSrcName = file.getOriginalFilename();
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String contentType = fileSrcName.substring(fileSrcName.lastIndexOf("."));
		// 在原始文件名后面加上时间戳
		String fileDestName = fileSrcName.substring(0, fileSrcName.lastIndexOf(".")) + "_" + sdf.format(date) + contentType;
		try {
			uploadSuccess = FtpUtil.uploadFile(conf.getFtpAddress(), conf.getFtpPort(), conf.getFtpUsername(), conf.getFtpPassword(),
					conf.getFtpBasePath(), model.getModelPath(), fileDestName, file.getInputStream());
		} catch (IOException e) {
			uploadSuccess = false;
			e.printStackTrace();
		}
		if (!uploadSuccess) {
			redirectObject.setLogInfo("上传失败!");
			request.setAttribute("redirectObject", redirectObject);
			return ERROR;
		}
		FileMain fileMain = new FileMain();
		String loginUser = CookieUtil.getLoginUser(request);
		fileMain.setCreateUser(loginUser);
		fileMain.setCreateTime(date);
		fileMain.setFdAttLocation(conf.getFtpAddress());
		fileMain.setFdCreatorId(loginUser);
		fileMain.setFdModelId(model.getModelId());
		fileMain.setFdModelName(model.getModelName());
		fileMain.setFdSrcName(fileSrcName);
		fileMain.setFdDestName(fileDestName);
		fileMain.setFdContentType(contentType);
		fileMain.setFdSize(new BigDecimal(file.getSize()));
		// fileMain.setFdAttType("doc");
		fileMain.setFdContentDescribe(ContentDescribeMap.contentDescribeMap.get(contentType.substring(1)));
		fileMain.setDownloadSum(0);
		fileMain.setFdFilePath(fileRootPath + conf.getFtpBasePath() + model.getModelPath());
		fileService.insertSelective(fileMain);
		return SUCCESS;
	}

	@RequestMapping(value = "/localDownload")
	public String localDownload(HttpServletRequest request, HttpServletResponse response, Integer fdId) throws IOException {
		FileMain fileMain = fileService.selectByPrimaryKey(fdId);
		SysModel model = modelService.selectByPrimaryKey(fileMain.getFdModelId());
		String ftpIp = fileMain.getFdAttLocation();
		FileFtpConf conf = new FileFtpConf();
		conf.setFtpAddress(ftpIp);
		conf.setFtpUrl(fileMain.getFdFilePath());
		List<FileFtpConf> confList = confService.selectByParams(conf);
		request.setAttribute("redirectObject", redirectObject);
		if (confList == null || confList.size() != 1) {
			redirectObject.setLogInfo("没有找到ftp服务器!");
			return ERROR;
		}
		String host = confList.get(0).getFtpAddress();
		int port = confList.get(0).getFtpPort();
		String username = confList.get(0).getFtpUsername();
		String password = confList.get(0).getFtpPassword();
		String remotePath = confList.get(0).getFtpUrl() + model.getModelPath();
		String fileName = fileMain.getFdDestName();
		boolean downloadSuccess = false;
		File tempFile = new File("");
		String tempPath = tempFile.getCanonicalPath();
		downloadSuccess = FtpUtil.downloadFile(host, port, username, password, remotePath, fileName, tempPath);
		if (!downloadSuccess) {
			redirectObject.setLogInfo("下载失败，请检查ftp服务器是否可用!");
			return ERROR;
		}
		addDownloadSum(fileMain);
		// 这里是提供给用户默认的文件名
		String defaultName = fileMain.getFdSrcName();
		File tmpFile = new File(tempPath + "//" + fileName);
		setOutputStream(response, tmpFile, defaultName);
		tmpFile.delete();
		redirectObject.setLogInfo("下载成功");
		redirectObject.setRedirectUrl("ftp/file/dataList");
		return null;
	}

	@RequestMapping(value = "/upload")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files)
			throws BusinessException {
		if (request.getParameter("modelName") == null) {
			return null;
		}
		String modelName = request.getParameter("modelName");
		MultipartFile file = files[0];
		String loginUser = CookieUtil.getLoginUser(request);
		
		FileMain fm = saveFile(file, modelName, loginUser);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("fdId", fm.getFdId());
		data.put("filePath", fm.getFdFilePath());

		redirectObject.setLogInfo("[" + fm.getFdSrcName() + "]文件上传成功 !");

		redirectObject.setRedirectUrl("ftp/file/dataList");
		request.setAttribute("redirectObject", redirectObject);
		return SUCCESS;
	}

	private static void renderData(HttpServletResponse response, Object x) {
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			pw = response.getWriter();
			if (x instanceof String) {
				logger.info("打印信息,{}", x);
				pw.print(x);
			} else {
				JSONArray array = JSONArray.fromObject(x);
				String result = array.toString();
				pw.print(result);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	
	/**
	 * 文件上传API请求
	 * @param request
	 * @param response
	 * @param files
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/api/upload")
	public Map<String, Object> uploadFileApi(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files)
			throws BusinessException {
		logger.debug("---------------------------------------------开始进入上传接口-----------------------------------------------");
		Map<String, Object> result = new HashMap<String, Object>();
		String resultMsg = "";
		
		// 判断token合法性======================================================
		MD5 md5 = new MD5();
		String appToken = "";
		String appSign = "";
		String sysSign = "";

		Map<String, String> headerMap = getHeadersInfo(request); // 获取请求头信息,保存到headerMap中
		appToken = headerMap.get("app_token");
		appSign = headerMap.get("sign");
		
		// 身份参数为空,不允许访问系统接口
		if (appToken == null || appSign == null || "".equals(appSign)) {
			result.put("msg", "没有权限访问");
			result.put("result", RESULT_CODE_FAILURE);
			return result;
		}

		sysSign = md5.getMD5ofStr(appToken + sysCode); // 根据用户token信息和系统编码进行MD5加密,与app的签名字段信息进行对比,一致则认为合法访问,否则,非法访问.
		
		logger.info("############requestSign：" + appSign);
		logger.info("############sysSign：" + sysSign);

		if (appToken != null && appSign != null && !appSign.equals(sysSign)) {
			resultMsg = "没有权限访问.";
			result.put("msg", resultMsg);
			result.put("result", "0");
			return result;
		}
		logger.info("===================================================token校验通过=============================================");
				
		
		if (request.getParameter("modelName") == null) {
			result.put("result", RESULT_CODE_FAILURE);
			result.put("msg", "modelName不能为空");
			return result;
		}
		String modelName = request.getParameter("modelName");
		String loginUser = CookieUtil.getLoginUser(request);
		
		try {
			Map<String, Integer> fileMap = new HashMap<>();
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				FileMain fm = saveFile(file, modelName, loginUser);
				fileMap.put(fm.getFdSrcName(), fm.getFdId());
			}
			result.put("data", fileMap);
			result.put("result", RESULT_CODE_SUCCESS);
			resultMsg = "文件上传成功";
		} catch (Exception e) {
			resultMsg = "文件上传失败";
			result.put("data", e.getMessage());
			logger.error(e.toString());
		}
		result.put("msg", resultMsg);
		return result;
	}
	
	/**
	 * 上传文件实现
	 * @param file
	 * @param modelName
	 * @param loginUser
	 * @return
	 */
	private FileMain saveFile(MultipartFile file, String modelName, String loginUser) {

		List<FileFtpConf> confList = confService.selectAll();
		if (confList == null || confList.size() == 0) {
			return null;
		}
		FileFtpConf conf = confList.get(0);

		
		SysModel paramModel = new SysModel();
		paramModel.setModelName(modelName);
		SysModel model = modelService.selectByParams(paramModel).get(0);
		boolean uploadSuccess = false;
		String fileSrcName = file.getOriginalFilename();
//		String fileId = request.getParameter("fileId");
//		if (fileSrcName == null || fileSrcName.equals("")) {
//			fileSrcName = file.getOriginalFilename();
//		}
		Date date = new Date();
		SimpleDateFormat sencondSdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat daySdf = new SimpleDateFormat("dd");
		String contentType = fileSrcName.substring(fileSrcName.lastIndexOf("."));
		// 在原始文件名后面加上时间戳
		String fileDestName = fileSrcName.substring(0, fileSrcName.lastIndexOf(".")) + "_" + sencondSdf.format(date) + contentType;
		String filePath = model.getModelPath() + "/" + monthSdf.format(date) + "/" + daySdf.format(date);
		try {
			uploadSuccess = FtpUtil.uploadFile(conf.getFtpAddress(), conf.getFtpPort(), conf.getFtpUsername(), conf.getFtpPassword(),
					conf.getFtpBasePath(), filePath, fileDestName, file.getInputStream());
		} catch (IOException e) {
			uploadSuccess = false;
			e.printStackTrace();
		}
		if (!uploadSuccess) {
			return null;
		}
		FileMain fileMain = new FileMain();
		fileMain.setCreateUser(loginUser);
		fileMain.setCreateTime(date);
		fileMain.setFdAttLocation(conf.getFtpAddress());
		fileMain.setFdCreatorId(loginUser);
		fileMain.setFdModelId(model.getModelId());
		fileMain.setFdModelName(model.getModelName());
		fileMain.setFdSrcName(fileSrcName);
		fileMain.setFdDestName(fileDestName);
		fileMain.setFdContentType(contentType);
		fileMain.setDownloadSum(0);
		fileMain.setFdContentDescribe(ContentDescribeMap.contentDescribeMap.get(contentType.substring(1)));
		fileMain.setFdSize(new BigDecimal(file.getSize()));
//		if (fileId != null) {
//			fileMain.setFdFileId(fileId);
//		}
		fileMain.setFdAttType("doc");
		fileMain.setFdFilePath(fileRootPath + conf.getFtpBasePath() + filePath);
		fileService.insertSelective(fileMain);
		List<FileMain> list = fileService.selectByParams(fileMain);
		FileMain fm = null;
		if (list != null && list.size() > 0) {
			fm = list.get(0);
		}
		return fm;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadApi")
	public VOResult upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files)
			throws BusinessException {
		if (request.getParameter("modelName") == null) {
			return VOResult.build(0, "请传入模块信息！");
		}
		MultipartFile file = files[0];

		List<FileFtpConf> confList = confService.selectAll();
		if (confList == null || confList.size() == 0) {
			return VOResult.build(0, "没有配置FTP服务器！");
		}
		FileFtpConf conf = confList.get(0);

		String modelName = request.getParameter("modelName");
		SysModel paramModel = new SysModel();
		paramModel.setModelName(modelName);
		SysModel model = modelService.selectByParams(paramModel).get(0);
		boolean uploadSuccess = false;
		String fileSrcName = request.getParameter("fileName");
		String fileId = request.getParameter("fileId");
		if (fileSrcName == null || fileSrcName.equals("")) {
			fileSrcName = file.getOriginalFilename();
		}
		Date date = new Date();
		SimpleDateFormat sencondSdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
		String contentType = fileSrcName.substring(fileSrcName.lastIndexOf("."));
		// 在原始文件名后面加上时间戳
		String fileDestName = fileSrcName.substring(0, fileSrcName.lastIndexOf(".")) + "_" + sencondSdf.format(date) + contentType;
		String filePath = model.getModelPath() + "/" + monthSdf.format(date);
		try {
			uploadSuccess = FtpUtil.uploadFile(conf.getFtpAddress(), conf.getFtpPort(), conf.getFtpUsername(), conf.getFtpPassword(),
					conf.getFtpBasePath(), filePath, fileDestName, file.getInputStream());
		} catch (IOException e) {
			uploadSuccess = false;
			e.printStackTrace();
		}
		if (!uploadSuccess) {
			return VOResult.build(0, "上传失败");
		}
		FileMain fileMain = new FileMain();
		String loginUser = CookieUtil.getLoginUser(request);
		fileMain.setCreateUser(loginUser);
		fileMain.setCreateTime(date);
		fileMain.setFdAttLocation(conf.getFtpAddress());
		fileMain.setFdCreatorId(loginUser);
		fileMain.setFdModelId(model.getModelId());
		fileMain.setFdModelName(model.getModelName());
		fileMain.setFdSrcName(fileSrcName);
		fileMain.setFdDestName(fileDestName);
		fileMain.setFdContentType(contentType);
		fileMain.setFdSize(new BigDecimal(file.getSize()));
		if (fileId != null) {
			fileMain.setFdFileId(fileId);
		}
		fileMain.setFdAttType("doc");
		fileMain.setFdFilePath(filePath);
		fileService.insertSelective(fileMain);
		List<FileMain> list = fileService.selectByParams(fileMain);
		FileMain fm = null;
		if (list != null && list.size() > 0) {
			fm = list.get(0);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("fdId", fm.getFdId());
		data.put("filePath", fm.getFdFilePath());
		return VOResult.build(1, "上传成功！", data);
	}

	@ResponseBody
	@RequestMapping(value = "/download")
	public VOResult download(HttpServletRequest request, HttpServletResponse response, Integer fdId) throws IOException {
		FileMain fileMain = fileService.selectByPrimaryKey(fdId);
		SysModel model = modelService.selectByPrimaryKey(fileMain.getFdModelId());
		String ftpIp = fileMain.getFdAttLocation();
		FileFtpConf conf = new FileFtpConf();
		conf.setFtpAddress(ftpIp);
		conf.setFtpUrl(fileMain.getFdFilePath());
		List<FileFtpConf> confList = confService.selectByParams(conf);
		if (confList == null || confList.size() != 1) {
			return VOResult.build(0, "没有找到FTP服务器");
		}
		conf = confList.get(0);
		String fileUrl = conf.getFtpUrl() + model.getModelPath() + "/" + fileMain.getFdDestName();
		return VOResult.build(1, "OK", fileUrl);
	}

	/**
	 * 将下载的文件输出流设置到response中
	 * 
	 * @param tmpFile
	 *            临时文件
	 * @param filename
	 *            下载的默认文件名
	 */
	protected void setOutputStream(HttpServletResponse response, File tmpFile, String filename) {
		response.reset();
		// 指明这是一个下载的response
		response.setContentType("application/x-download");
		// 双重解码、防止乱码
		try {
			filename = URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		InputStream is = null;
		// 打印流
		PrintWriter pw = null;
		try {
			is = new FileInputStream(tmpFile);
			pw = new PrintWriter(response.getOutputStream());
			int ch;
			while ((ch = is.read()) != -1) {
				pw.write(ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 打印流的所有输出内容，必须关闭这个打印流才有效
			pw.close();
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加下载次数
	 */
	protected void addDownloadSum(FileMain fileMain) {
		Integer downloadSum = fileMain.getDownloadSum();
		if (downloadSum == null) {
			downloadSum = 0;
		}
		downloadSum = downloadSum + 1;
		fileMain.setDownloadSum(downloadSum);
		fileService.updateByPrimaryKeySelective(fileMain);
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
	
	/**
	 * 进件附件上传
	 * ps.这段代码因为某些原因返回格式定死了，所以写的有些恶心，请后期维护的小伙伴见谅
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/api/upload/jinjian")
	public Map<String, Object> upload(HttpServletRequest request){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String modelName = "jinjian";
		String loginUser = CookieUtil.getLoginUser(request);
		
		Map<String, Object> map = new HashMap<>();
		try {
			request.setCharacterEncoding("UTF-8");
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				
				if (multiRequest.getContentLength() > 50 * 1024 * 1024) {	//文件上传不能大于50M
					resultMap.put("result", RESULT_CODE_FAILURE);
					resultMap.put("msg", "文件不能大于50M");
					return resultMap;
				}
				System.out.println();
				
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				List<Map<String, String>> certificates = new ArrayList<>();
				while (iter.hasNext()) {
					 
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file == null)
						continue ;
					String keyName = file.getName();
					logger.info("文件名:{}",keyName);
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (StringUtils.isBlank(myFileName)){
						logger.info("{},文件不存在",myFileName);
						continue;
					}
					
					FileMain fm = saveFile(file, modelName, loginUser);
					String basePath = fm.getFdFilePath();
					String filePath = sysPath + basePath.replace("/home/vsftp/ROOT", "");
					
					if (keyName.startsWith("certificate")) {
						Map<String, String> certificate = new HashMap<>();
						certificate.put("name", fm.getFdDestName());
						certificate.put("path", filePath + "/" + fm.getFdDestName());
						certificates.add(certificate);
					} else {
						map.put(keyName + "Name", fm.getFdDestName());
						map.put(keyName + "Path", filePath + "/" + fm.getFdDestName());
					}
				}
				map.put("certificate", certificates);
			}
			resultMap.put("result", RESULT_CODE_SUCCESS);
			resultMap.put("msg", "文件上传成功");
			resultMap.put("data", "[" + JsonUtil.toJsonStr(map) + "]");
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			resultMap.put("result", RESULT_CODE_FAILURE);
			resultMap.put("msg", "文件上传失败");
		}
		return resultMap;
	}
	
	/**
	 * 提车附件上传
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/api/upload/sign")
	public Map<String, Object> signUpload(HttpServletRequest request){
		logger.debug("---------------------------------------------开始进入上传接口-----------------------------------------------");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String modelName = "sign";
		String loginUser = CookieUtil.getLoginUser(request);
		
		List<String> urls = new ArrayList<>();
		try {
			request.setCharacterEncoding("UTF-8");
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				
				if (multiRequest.getContentLength() > 50 * 1024 * 1024) {	//文件上传不能大于50M
					resultMap.put("result", RESULT_CODE_FAILURE);
					resultMap.put("msg", "文件不能大于50M");
					return resultMap;
				}
				System.out.println();
				
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file == null)
						continue ;
					String keyName = file.getName();
					logger.info("文件名:{}",keyName);
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (StringUtils.isBlank(myFileName)){
						logger.info("{},文件不存在",myFileName);
						continue;
					}
					
					FileMain fm = saveFile(file, modelName, loginUser);
					String basePath = fm.getFdFilePath();
					String filePath = sysPath + basePath.replace("/home/vsftp/ROOT", "");
					urls.add(filePath + "/" + fm.getFdDestName());
				}
			}
			resultMap.put("result", RESULT_CODE_SUCCESS);
			resultMap.put("msg", "文件上传成功");
			resultMap.put("data", urls);
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			resultMap.put("result", RESULT_CODE_FAILURE);
			resultMap.put("msg", "文件上传失败");
		}
		return resultMap;
	}
	
	/**
	 * 提车附件上传
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/api/upload/vehicle")
	public Map<String, Object> vehicleUpload(HttpServletRequest request){
		logger.debug("---------------------------------------------开始进入上传接口-----------------------------------------------");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String modelName = "vehicle";
		String loginUser = CookieUtil.getLoginUser(request);
		
		Map<String, String> urls = new HashMap<>();
		try {
			request.setCharacterEncoding("UTF-8");
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				
				if (multiRequest.getContentLength() > 50 * 1024 * 1024) {	//文件上传不能大于50M
					resultMap.put("result", RESULT_CODE_FAILURE);
					resultMap.put("msg", "文件不能大于50M");
					return resultMap;
				}
				System.out.println();
				
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file == null)
						continue ;
					String keyName = file.getName();
					logger.info("文件名:{}",keyName);
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (StringUtils.isBlank(myFileName)){
						logger.info("{},文件不存在",myFileName);
						continue;
					}
					
					FileMain fm = saveFile(file, modelName, loginUser);
					String basePath = fm.getFdFilePath();
					String filePath = sysPath + basePath.replace("/home/vsftp/ROOT", "");
					urls.put(keyName, fm.getFdDestName());
					urls.put(keyName + "Path", filePath + "/" + fm.getFdDestName());
				}
			}
			resultMap.put("result", RESULT_CODE_SUCCESS);
			resultMap.put("msg", "文件上传成功");
			resultMap.put("data", JsonUtil.toJsonStr(urls));
//			resultMap.put("data", urls);
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			resultMap.put("result", RESULT_CODE_FAILURE);
			resultMap.put("msg", "文件上传失败");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/api/upload/common")
	public Map<String, Object> commonUpload(HttpServletRequest request){
		logger.debug("---------------------------------------------开始进入上传接口-----------------------------------------------");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String modelName = request.getParameter("model");
		if (StringUtils.isEmpty(modelName)) {
			modelName = "default";
		}
		
		String loginUser = CookieUtil.getLoginUser(request);
		
		Map<String, Object> urls = new HashMap<>();
		try {
			request.setCharacterEncoding("UTF-8");
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				
				if (multiRequest.getContentLength() > 50 * 1024 * 1024) {	//文件上传不能大于50M
					resultMap.put("result", RESULT_CODE_FAILURE);
					resultMap.put("msg", "文件不能大于50M");
					return resultMap;
				}
				System.out.println();
				
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file == null)
						continue ;
					String keyName = file.getName();
					logger.info("文件名:{}",keyName);
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (StringUtils.isBlank(myFileName)){
						logger.info("{},文件不存在",myFileName);
						continue;
					}
					
					FileMain fm = saveFile(file, modelName, loginUser);
					String basePath = fm.getFdFilePath();
					String filePath = sysPath + basePath.replace("/home/vsftp/ROOT", "");
					urls.put(keyName, filePath + "/" + fm.getFdDestName());
				}
			}
			resultMap.put("result", RESULT_CODE_SUCCESS);
			resultMap.put("msg", "文件上传成功");
			resultMap.put("data", urls);
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			resultMap.put("result", RESULT_CODE_FAILURE);
			resultMap.put("msg", "文件上传失败");
		}
		return resultMap;
	}
}
