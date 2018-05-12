package com.ss.file.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 银行编码映射类
 * @author lijh
 *
 */
public class ContentDescribeMap {

	public static final Map<String, String> contentDescribeMap = new HashMap<String, String>();
	
	static { 
		
		//文档文件
		contentDescribeMap.put("txt", "记事本");
		contentDescribeMap.put("doc", "word文档");
		contentDescribeMap.put("docx", "word文档");
		contentDescribeMap.put("docm", "word文档");
		contentDescribeMap.put("pdf", "pdf文档");
		contentDescribeMap.put("wps", "wps文档");
		contentDescribeMap.put("rtf", "文本文件");
		contentDescribeMap.put("html", "网页文件");
		contentDescribeMap.put("xls", "excel工作表");
		contentDescribeMap.put("xlsx", "excel工作表");
		contentDescribeMap.put("ppt", "ppt文档");
		contentDescribeMap.put("pptx", "ppt文档");
		
		//压缩文件
		contentDescribeMap.put("rar", "压缩包");
		contentDescribeMap.put("zip", "压缩包");
		contentDescribeMap.put("gz", "压缩包");
		
		//图片文件
		contentDescribeMap.put("bmp", "图形文件");
		contentDescribeMap.put("gif", "图形文件");
		contentDescribeMap.put("jpg", "图形文件");
		contentDescribeMap.put("pic", "图形文件");
		contentDescribeMap.put("png", "图形文件");
		contentDescribeMap.put("tif", "图形文件");
		contentDescribeMap.put("jpeg", "图形文件");
		
		//声音文件
		contentDescribeMap.put("mid", "声卡声乐文件");
		contentDescribeMap.put("mp3", "声卡声乐文件");
		contentDescribeMap.put("wma", "声卡声乐文件");
		
		//视频文件
		contentDescribeMap.put("rm", "视频文件");
		contentDescribeMap.put("avi", "视频文件");
		contentDescribeMap.put("flv", "视频文件");
		contentDescribeMap.put("swf", "视频文件");
		
		//其他类型
		contentDescribeMap.put("iso", "镜像文件");
		contentDescribeMap.put("exe", "安装包");
		contentDescribeMap.put("tmp", "临时文件");
		contentDescribeMap.put("mdf", "虚拟光驱镜像文件");
		
	}
}
