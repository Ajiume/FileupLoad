package com.ss.file.dao.sys;

import java.util.List;

import com.ss.file.pojo.sys.SysUserEO;
import com.ss.file.pojo.sys.SysUserVO;
import com.ss.platform.base.dao.BaseDAO;

public interface SysUserDAO<T> extends BaseDAO<T> {
   
	public int insertSysUser(SysUserEO systemUserEO);
	
	int deleteByPrimaryKey(Integer id);

	int insertSelective(SysUserEO record);

	SysUserEO selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysUserEO record);

	int updateByPrimaryKey(SysUserEO record);
	
	List<SysUserEO> selectByParams(SysUserVO vo);

	List<SysUserEO> loadByUsername(String username);

}
