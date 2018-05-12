package com.ss.file.service.sys;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.sys.SysModel;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;

public interface SysModelService {

	PageEO listSysModel(PageParam pageParam);

	void deleteByPrimaryKey(String[] ids);

	SysModel selectByPrimaryKey(Integer modelId);

	void insertByParams(Map<String, Object> params);

	void updateByParams(Map<String, Object> params);

	List<SysModel> selectAll();

	List<SysModel> selectByParams(SysModel record);
}
