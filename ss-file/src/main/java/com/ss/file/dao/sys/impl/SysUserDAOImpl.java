package com.ss.file.dao.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ss.file.dao.sys.SysUserDAO;
import com.ss.file.mapper.sys.SysUserMapper;
import com.ss.file.pojo.sys.SysUserEO;
import com.ss.file.pojo.sys.SysUserVO;
import com.ss.platform.base.dao.impl.BaseDAOImpl;
import com.ss.platform.base.mapper.BaseEOMapper;

@Repository
public class SysUserDAOImpl<T> extends BaseDAOImpl<T> implements SysUserDAO<T> {

	@Resource
    private SysUserMapper<T> sysUserMapper;
	
	@Override
	public BaseEOMapper<T> getMapper() {
		return sysUserMapper;
	}

	@Override
	public int insertSysUser(SysUserEO systemUserEO) {
		return sysUserMapper.insertSysUser(systemUserEO);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {		
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(SysUserEO record) {
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public SysUserEO selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUserEO record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUserEO record) {
		return sysUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysUserEO> selectByParams(SysUserVO vo) {
		return sysUserMapper.selectByParams(vo);
	}
	
	@Override
	public List<SysUserEO> loadByUsername(String username) {
		return sysUserMapper.loadByUsername(username);
	}
}
