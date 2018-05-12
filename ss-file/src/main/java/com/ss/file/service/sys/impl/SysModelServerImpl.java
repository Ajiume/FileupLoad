package com.ss.file.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.file.dao.sys.SysModelDao;
import com.ss.file.pojo.sys.SysModel;
import com.ss.file.service.sys.SysModelService;
import com.ss.platform.base.dao.BaseDAO;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.base.serviceimpl.BaseServiceImpl;

@Service
public class SysModelServerImpl extends BaseServiceImpl<SysModel> implements
		SysModelService {

	@Autowired
	private SysModelDao<SysModel> modelDao;

	@Override
	public PageEO listSysModel(PageParam pageParam) {
		return modelDao.queryByList(pageParam);
	}

	@Override
	public BaseDAO<SysModel> getDao() {
		return modelDao;
	}

	@Override
	public void deleteByPrimaryKey(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			modelDao.deleteByPrimaryKey(Integer.valueOf(ids[i]));
		}
	}

	@Override
	public SysModel selectByPrimaryKey(Integer modelId) {
		return modelDao.selectByPrimaryKey(modelId);
	}

	@Override
	public void insertByParams(Map<String, Object> params) {
		modelDao.insertByParams(params);
	}

	@Override
	public void updateByParams(Map<String, Object> params) {
		modelDao.updateByParams(params);
	}

	@Override
	public List<SysModel> selectAll() {
		return modelDao.selectAll();
	}

	@Override
	public List<SysModel> selectByParams(SysModel record) {
		return modelDao.selectByParams(record);
	}

}
