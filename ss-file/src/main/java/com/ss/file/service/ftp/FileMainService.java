package com.ss.file.service.ftp;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.ftp.FileMain;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;

public interface FileMainService {

	PageEO listFileMain(PageParam pageParam);

	String deleteByPrimaryKey(String[] ids);

	FileMain selectByPrimaryKey(Integer fdId);
	
	List<FileMain> selectByParams(FileMain record);

	Integer insertSelective(FileMain record);

	int insertByParams(Map<String, Object> params);

	int updateByParams(Map<String, Object> params);

	int updateByPrimaryKeySelective(FileMain record);
}
