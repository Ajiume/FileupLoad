package com.ss.file.dao.sys;

import java.util.List;

import com.ss.file.pojo.sys.Role;
import com.ss.platform.base.dao.BaseDAO;

public interface RoleDAO<T> extends BaseDAO<T> {
   
	int insertRole(Role record);
	
	int deleteByPrimaryKey(Long id);

	int insertSelective(Role record);

	Role selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);
	
	List<Role> selectByParams(Role record);
}
