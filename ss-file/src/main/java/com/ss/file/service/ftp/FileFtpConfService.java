package com.ss.file.service.ftp;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.ftp.FileFtpConf;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;

public interface FileFtpConfService {

	PageEO listFileFtpConf(PageParam pageParam);

	void deleteByPrimaryKey(String[] ids);

	FileFtpConf selectByPrimaryKey(Integer confId);
	
	List<FileFtpConf> selectByParams(FileFtpConf record);

	void insertByParams(Map<String, Object> params);

	void updateByParams(Map<String, Object> params);

	List<FileFtpConf> selectAll();

}
