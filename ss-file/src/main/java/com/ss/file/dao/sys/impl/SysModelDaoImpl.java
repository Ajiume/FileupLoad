package com.ss.file.dao.sys.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ss.file.dao.sys.SysModelDao;
import com.ss.file.mapper.sys.SysModelMapper;
import com.ss.file.pojo.sys.SysModel;
import com.ss.platform.base.dao.impl.BaseDAOImpl;
import com.ss.platform.base.mapper.BaseEOMapper;

@Repository
public class SysModelDaoImpl<T> extends BaseDAOImpl<T> implements SysModelDao<T> {

	@Resource
	private SysModelMapper<T> modelMapper;

	@Override
	public BaseEOMapper<T> getMapper() {
		return modelMapper;
	}

	public int deleteByPrimaryKey(Integer fdId) {
		return modelMapper.deleteByPrimaryKey(fdId);
	}

	public int insert(SysModel record) {
		return modelMapper.insert(record);
	}

	public int insertSelective(SysModel record) {
		return modelMapper.insertSelective(record);
	}

	public int insertByParams(Map<String, Object> params) {
		return modelMapper.insertByParams(params);
	}

	public SysModel selectByPrimaryKey(Integer modelId) {
		return modelMapper.selectByPrimaryKey(modelId);
	}

	public List<SysModel> selectByParams(SysModel record) {
		return modelMapper.selectByParams(record);
	}

	public int updateByPrimaryKeySelective(SysModel record) {
		return modelMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByParams(Map<String, Object> params) {
		return modelMapper.updateByParams(params);
	}

	public int updateByPrimaryKey(SysModel record) {
		return modelMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysModel> selectAll() {
		return modelMapper.selectAll();
	}

}
