package com.ss.file.dao.sys;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.sys.SysModel;
import com.ss.platform.base.dao.BaseDAO;

public interface SysModelDao<T> extends BaseDAO<T> {
	
	int deleteByPrimaryKey(Integer confId);

	int insert(SysModel record);

	int insertSelective(SysModel record);

	int insertByParams(Map<String, Object> params);

	SysModel selectByPrimaryKey(Integer confId);
	
	List<SysModel> selectByParams(SysModel record);

	int updateByPrimaryKeySelective(SysModel record);

	int updateByParams(Map<String, Object> params);

	int updateByPrimaryKey(SysModel record);

	List<SysModel> selectAll();
}
