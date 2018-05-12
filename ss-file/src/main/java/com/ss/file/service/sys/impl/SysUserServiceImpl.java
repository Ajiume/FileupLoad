package com.ss.file.service.sys.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.file.dao.sys.SysUserDAO;
import com.ss.file.pojo.sys.SysUserEO;
import com.ss.file.pojo.sys.SysUserVO;
import com.ss.file.service.sys.SysUserService;
import com.ss.platform.base.dao.BaseDAO;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.base.serviceimpl.BaseServiceImpl;
import com.ss.platform.exception.RTException;
import com.ss.platform.util.MD5;

@Service("SysUserService")
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDAO<SysUserEO> sysUserDAO;

	public SysUserServiceImpl() {
	}

	@Override
	public BaseDAO getDao() {
		return sysUserDAO;
	}

	@Override
	public int insertSysUser(SysUserEO systemUserEO) {
		return sysUserDAO.insertSysUser(systemUserEO);
	}

	/**
	 * 查询页列表
	 * 
	 * @param page
	 * @return
	 * @throws RTException
	 */
	@Override
	public PageEO listBySystemUser(PageParam pageParam) {
		return this.sysUserDAO.queryByList(pageParam);
	}

	@Override
	public boolean deleteByPrimaryKey(String[] ids) {
		if (ids != null) {
			for (String seqId : ids) {
				int i = sysUserDAO.deleteByPrimaryKey(Integer.parseInt(seqId));
			}
		}
		return true;
	}

	@Override
	public int insertByParam(Map map) {
		SysUserEO systemUser = new SysUserEO();
		if (map != null) {
			MD5 md5 = new MD5();
			systemUser.setLoginName(map.get("sso_login") != null ? map.get("sso_login").toString() : null);
			systemUser.setLoginPassword(map.get("sso_pwd") != null ? md5.getMD5ofStr(map.get("sso_pwd").toString()) : null);
			systemUser.setState(map.get("state") != null ? Integer.parseInt(map.get("state").toString()) : 0);
			systemUser.setEmail(map.get("email") != null ? map.get("email").toString() : null);
			systemUser.setUserName(map.get("userName") != null ? map.get("userName").toString() : null);
			systemUser.setDeptName(map.get("deptName") != null ? map.get("deptName").toString() : null);
			systemUser.setSystemCode("DT");
			systemUser.setRoleId(map.get("roleIds") != null ? getRoleIds((String[])map.get("roleIds")) : null);
			systemUser.setCreateTime(new Date());
			systemUser.setCreateUser("admin");
			return sysUserDAO.insertSelective(systemUser);
		}
		return -1;
	}

	@Override
	public SysUserEO selectByPrimaryKey(Integer id) {
		return sysUserDAO.selectByPrimaryKey(id);
	}

	@Override
	public int updateByParam(Map map) {		
		if (map != null) {
			if (map.get("seqId") != null && map.get("seqId").toString().length() > 0) {
				SysUserEO systemUser = sysUserDAO.selectByPrimaryKey(Integer.parseInt(map.get("seqId").toString()));
				MD5 md5 = new MD5();
				systemUser.setLoginName(map.get("sso_login") != null ? map.get("sso_login").toString() : null);
				if (!systemUser.getLoginPassword().equals(map.get("sso_pwd").toString())) {
					systemUser.setLoginPassword(map.get("sso_pwd") != null ? md5.getMD5ofStr(map.get("sso_pwd").toString()) : null);
				}
				systemUser.setState(map.get("state") != null ? Integer.parseInt(map.get("state").toString()) : 0);
				systemUser.setEmail(map.get("email") != null ? map.get("email").toString() : null);
				systemUser.setUserName(map.get("userName") != null ? map.get("userName").toString() : null);
				systemUser.setDeptName(map.get("deptName") != null ? map.get("deptName").toString() : null);
				systemUser.setRoleId(map.get("roleIds") != null ? getRoleIds((String[])map.get("roleIds")) : null);
				systemUser.setUpdateTime(new Date());
				systemUser.setUpdateUser("admin");
				return sysUserDAO.updateByPrimaryKeySelective(systemUser);
			} else {
				return -1;
			}
		}
		return -1;
	}

	@Override
	public int updateByPrimaryKey(SysUserEO record) {
		return sysUserDAO.updateByPrimaryKey(record);
	}

	@Override
	public List<SysUserEO> selectByParams(SysUserVO vo) {
		return sysUserDAO.selectByParams(vo);
	}

	@Override
	public List<SysUserEO> loadByUsername(String username) {
		return this.sysUserDAO.loadByUsername(username);
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
	
	// @Resource
	// private SystemUserDAO<SystemUser> systemUserDAO;
	//
	// @Override
	// public BaseDAO getDao() {
	// return systemUserDAO;
	// }
	//
	// public PageEO listByPage(PageParam pageParam) {
	// PageEO pageEO = this.systemUserDAO.queryByList(pageParam);
	// return pageEO;
	// }

}
