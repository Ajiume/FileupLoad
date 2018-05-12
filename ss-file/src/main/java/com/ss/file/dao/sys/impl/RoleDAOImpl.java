package com.ss.file.dao.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ss.file.dao.sys.RoleDAO;
import com.ss.file.mapper.sys.RoleMapper;
import com.ss.file.pojo.sys.Role;
import com.ss.platform.base.dao.impl.BaseDAOImpl;
import com.ss.platform.base.mapper.BaseEOMapper;

@Repository
public class RoleDAOImpl<T> extends BaseDAOImpl<T> implements RoleDAO<T> {

	@Resource
    private RoleMapper<T> roleMapper;
	
	@Override
	public BaseEOMapper<T> getMapper() {
		return roleMapper;
	}

	@Override
	public int insertRole(Role record) {
		return roleMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {		
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(Role record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Role> selectByParams(Role record) {
		return roleMapper.selectByParams(record);
	}
	

}
