package com.ss.file.mapper.sys;

import java.util.List;

import com.ss.file.pojo.sys.SysUserEO;
import com.ss.file.pojo.sys.SysUserVO;
import com.ss.platform.base.mapper.BaseEOMapper;

public interface SysUserMapper<T> extends BaseEOMapper<T> {

	public int insertSysUser(SysUserEO systemUserEO);

	int deleteByPrimaryKey(Integer id);

	int insertSelective(SysUserEO record);

	SysUserEO selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysUserEO record);

	int updateByPrimaryKey(SysUserEO record);

	List<SysUserEO> selectByParams(SysUserVO vo);

	public Long countPageParam();

	public List<SysUserEO> selectByParams();
	
	public List<SysUserEO> loadByUsername(String username);
}
