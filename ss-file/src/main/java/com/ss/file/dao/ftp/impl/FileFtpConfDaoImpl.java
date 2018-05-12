package com.ss.file.dao.ftp.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ss.file.dao.ftp.FileFtpConfDao;
import com.ss.file.mapper.ftp.FileFtpConfMapper;
import com.ss.file.pojo.ftp.FileFtpConf;
import com.ss.platform.base.dao.impl.BaseDAOImpl;
import com.ss.platform.base.mapper.BaseEOMapper;

@Repository
public class FileFtpConfDaoImpl<T> extends BaseDAOImpl<T> implements FileFtpConfDao<T> {

	@Resource
	private FileFtpConfMapper<T> confMapper;

	@Override
	public BaseEOMapper<T> getMapper() {
		return confMapper;
	}

	public int deleteByPrimaryKey(Integer confId) {
		return confMapper.deleteByPrimaryKey(confId);
	}

	public int insert(FileFtpConf record) {
		return confMapper.insert(record);
	}

	public int insertSelective(FileFtpConf record) {
		return confMapper.insertSelective(record);
	}

	public int insertByParams(Map<String, Object> params) {
		return confMapper.insertByParams(params);
	}

	public FileFtpConf selectByPrimaryKey(Integer confId) {
		return confMapper.selectByPrimaryKey(confId);
	}

	public List<FileFtpConf> selectByParams(FileFtpConf record) {
		return confMapper.selectByParams(record);
	}

	public int updateByPrimaryKeySelective(FileFtpConf record) {
		return confMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByParams(Map<String, Object> params) {
		return confMapper.updateByParams(params);
	}

	public int updateByPrimaryKey(FileFtpConf record) {
		return confMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FileFtpConf> selectAll() {
		return confMapper.selectAll();
	}

}
