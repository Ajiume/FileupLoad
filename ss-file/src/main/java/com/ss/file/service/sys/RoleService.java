package com.ss.file.service.sys;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.sys.Role;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;

public interface RoleService {

	PageEO listByRole(PageParam pageParam);

	public int insertRole(Role record);

	boolean deleteByPrimaryKey(String[] ids);
	
	int insertByParam(Map map);

	Role selectByPrimaryKey(Long id);

	int updateByParam(Map map);

	int updateByPrimaryKey(Role record);

	List<Role> selectByParams(Role record);
}
