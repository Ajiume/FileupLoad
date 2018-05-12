package com.ss.file.mapper.sys;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.sys.SysModel;
import com.ss.platform.base.mapper.BaseEOMapper;

public interface SysModelMapper<T> extends BaseEOMapper<T> {
	int deleteByPrimaryKey(Integer modelId);

	int insert(SysModel record);

	int insertSelective(SysModel record);

	SysModel selectByPrimaryKey(Integer modelId);

	int updateByPrimaryKeySelective(SysModel record);

	int updateByPrimaryKey(SysModel record);

	int insertByParams(Map<String, Object> params);

	List<SysModel> selectByParams(SysModel record);

	int updateByParams(Map<String, Object> params);

	List<SysModel> selectAll();
}