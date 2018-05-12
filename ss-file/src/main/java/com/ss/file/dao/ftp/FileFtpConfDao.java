package com.ss.file.dao.ftp;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.ftp.FileFtpConf;
import com.ss.platform.base.dao.BaseDAO;

public interface FileFtpConfDao<T> extends BaseDAO<T> {
	
	int deleteByPrimaryKey(Integer confId);

	int insert(FileFtpConf record);

	int insertSelective(FileFtpConf record);

	int insertByParams(Map<String, Object> params);

	FileFtpConf selectByPrimaryKey(Integer confId);
	
	List<FileFtpConf> selectByParams(FileFtpConf record);

	int updateByPrimaryKeySelective(FileFtpConf record);

	int updateByParams(Map<String, Object> params);

	int updateByPrimaryKey(FileFtpConf record);

	List<FileFtpConf> selectAll();
}
