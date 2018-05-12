package com.ss.file.dao.ftp;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.ftp.FileMain;
import com.ss.platform.base.dao.BaseDAO;

public interface FileMainDao<T> extends BaseDAO<T> {
	
	int deleteByPrimaryKey(Integer confId);

	int insert(FileMain record);

	Integer insertSelective(FileMain record);

	int insertByParams(Map<String, Object> params);

	FileMain selectByPrimaryKey(Integer confId);
	
	List<FileMain> selectByParams(FileMain record);

	int updateByPrimaryKeySelective(FileMain record);

	int updateByParams(Map<String, Object> params);

	int updateByPrimaryKey(FileMain record);
}
