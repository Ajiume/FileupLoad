package com.ss.file.dao.ftp.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ss.file.dao.ftp.FileMainDao;
import com.ss.file.mapper.ftp.FileMainMapper;
import com.ss.file.pojo.ftp.FileMain;
import com.ss.platform.base.dao.impl.BaseDAOImpl;
import com.ss.platform.base.mapper.BaseEOMapper;

@Repository
public class FileMainDaoImpl<T> extends BaseDAOImpl<T> implements FileMainDao<T> {

	@Resource
	private FileMainMapper<T> fileMainMapper;

	@Override
	public BaseEOMapper<T> getMapper() {
		return fileMainMapper;
	}

	public int deleteByPrimaryKey(Integer fdId) {
		return fileMainMapper.deleteByPrimaryKey(fdId);
	}

	public int insert(FileMain record) {
		return fileMainMapper.insert(record);
	}

	public Integer insertSelective(FileMain record) {
		return fileMainMapper.insertSelective(record);
	}

	public int insertByParams(Map<String, Object> params) {
		return fileMainMapper.insertByParams(params);
	}

	public FileMain selectByPrimaryKey(Integer fdId) {
		return fileMainMapper.selectByPrimaryKey(fdId);
	}

	public List<FileMain> selectByParams(FileMain record) {
		return fileMainMapper.selectByParams(record);
	}

	public int updateByPrimaryKeySelective(FileMain record) {
		record.setUpdateTime(new Date());
		return fileMainMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByParams(Map<String, Object> params) {
		return fileMainMapper.updateByParams(params);
	}

	public int updateByPrimaryKey(FileMain record) {
		record.setUpdateTime(new Date());
		return fileMainMapper.updateByPrimaryKey(record);
	}

}
