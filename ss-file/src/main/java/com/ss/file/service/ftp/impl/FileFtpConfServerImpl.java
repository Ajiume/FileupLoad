package com.ss.file.service.ftp.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.file.dao.ftp.FileFtpConfDao;
import com.ss.file.pojo.ftp.FileFtpConf;
import com.ss.file.service.ftp.FileFtpConfService;
import com.ss.platform.base.dao.BaseDAO;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.base.serviceimpl.BaseServiceImpl;

@Service
public class FileFtpConfServerImpl extends BaseServiceImpl<FileFtpConf> implements
		FileFtpConfService {

	@Autowired
	private FileFtpConfDao<FileFtpConf> ftpConfDao;

	@Override
	public PageEO listFileFtpConf(PageParam pageParam) {
		return ftpConfDao.queryByList(pageParam);
	}

	@Override
	public BaseDAO<FileFtpConf> getDao() {
		return ftpConfDao;
	}

	@Override
	public void deleteByPrimaryKey(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ftpConfDao.deleteByPrimaryKey(Integer.valueOf(ids[i]));
		}
	}

	@Override
	public FileFtpConf selectByPrimaryKey(Integer confId) {
		return ftpConfDao.selectByPrimaryKey(confId);
	}

	@Override
	public void insertByParams(Map<String, Object> params) {
		ftpConfDao.insertByParams(params);
	}

	@Override
	public void updateByParams(Map<String, Object> params) {
		ftpConfDao.updateByParams(params);
	}

	@Override
	public List<FileFtpConf> selectAll() {
		return ftpConfDao.selectAll();
	}

	@Override
	public List<FileFtpConf> selectByParams(FileFtpConf record) {
		return ftpConfDao.selectByParams(record);
	}

}
