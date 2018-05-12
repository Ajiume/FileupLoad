package com.ss.file.service.sys.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.file.dao.sys.RoleDAO;
import com.ss.file.pojo.sys.Role;
import com.ss.file.service.sys.RoleService;
import com.ss.platform.base.dao.BaseDAO;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.base.serviceimpl.BaseServiceImpl;
import com.ss.platform.exception.RTException;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	
	@Autowired
	private RoleDAO<Role> roleDAO;

	public RoleServiceImpl() {
	}

	@Override
	public BaseDAO getDao() {
		return roleDAO;
	}

	@Override
	public int insertRole(Role role) {
		return roleDAO.insertRole(role);
	}

	/**
	 * 查询页列表
	 * 
	 * @param page
	 * @return
	 * @throws RTException
	 */
	@Override
	public PageEO listByRole(PageParam pageParam) {
		return this.roleDAO.queryByList(pageParam);
	}

	@Override
	public boolean deleteByPrimaryKey(String[] ids) {
		if (ids != null) {
			for (String seqId : ids) {
				int i = roleDAO.deleteByPrimaryKey(Long.parseLong(seqId));
			}
		}
		return true;
	}

	/*
	 	private Long seqId;
    private Date createTime;
    private Long createUser;
    private String description;
    private Boolean isSystem;
    private String roleName;
    private String authorityListStore;
	 * */
	
	@Override
	public int insertByParam(Map map) {
		Role role = new Role();
		if (map != null) {
			role.setRoleName(map.get("roleName") != null ? map.get("roleName").toString() : null);
			role.setIsSystem(map.get("isSystem") != null && map.get("isSystem").equals("1") ? true : false);
			role.setDescription(map.get("description") != null ? map.get("description").toString() : null);
			role.setAuthorityListStore(map.get("authorityListStore") != null ? map.get("authorityListStore").toString() : null);
			role.setCreateTime(new Date());
			role.setCreateUser(1L);
			return roleDAO.insertSelective(role);
		}
		return -1;
	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		return roleDAO.selectByPrimaryKey(id);
	}

	@Override
	public int updateByParam(Map map) {		
		if (map != null) {
			if (map.get("seqId") != null && map.get("seqId").toString().length() > 0) {
				Role role = roleDAO.selectByPrimaryKey(Long.parseLong(map.get("seqId").toString()));
				role.setRoleName(map.get("roleName") != null ? map.get("roleName").toString() : null);
				role.setIsSystem(map.get("isSystem") != null && map.get("isSystem").equals("1") ? true : false);
				role.setDescription(map.get("description") != null ? map.get("description").toString() : null);
				role.setAuthorityListStore(map.get("authorityListStore") != null ? map.get("authorityListStore").toString() : null);
				return roleDAO.updateByPrimaryKeySelective(role);
			} else {
				return -1;
			}
		}
		return -1;
	}

	@Override
	public int updateByPrimaryKey(Role role) {
		return roleDAO.updateByPrimaryKey(role);
	}

	@Override
	public List<Role> selectByParams(Role role) {
		return roleDAO.selectByParams(role);
	}

	private String getRoleIds(String[] roleIds) {
		if (roleIds != null && roleIds.length > 0) {
			StringBuilder ids = new StringBuilder();
			for (String role : roleIds) {
				ids.append(role + ",");
			}
			return ids.toString().substring(0, ids.toString().length() - 1);
		}
		return null;
	}

}
