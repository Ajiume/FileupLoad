package com.ss.file.service.sys;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.sys.SysUserEO;
import com.ss.file.pojo.sys.SysUserVO;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;

public interface SysUserService {

	PageEO listBySystemUser(PageParam pageParam);

	public int insertSysUser(SysUserEO systemUserEO);

	boolean deleteByPrimaryKey(String[] ids);
	
	int insertByParam(Map map);

	SysUserEO selectByPrimaryKey(Integer id);

	int updateByParam(Map map);

	int updateByPrimaryKey(SysUserEO record);

	List<SysUserEO> selectByParams(SysUserVO systemUserVO);

	List<SysUserEO> loadByUsername(String username);
}
