package com.ss.file.pojo.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户-实体对象
 * @author liuwz
 * @since 2017-03-31
 */
public class SysUserEO implements Serializable {

	private static final long serialVersionUID = -6795206635212904778L;
	/**
	 * 主键id
	 */
	private Integer seqId;
	/**
	 * 用户名
	 */
    private String userName;
    /**
     * 系统编码
     */
    private String systemCode;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String loginPassword;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     *状态： 1=正常2=锁定
     */
    private Integer state;
    /**
     * 1:普通管理员  2: 超级管理员
     */
    private String roleId;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 登录ip
     */
    private String loginIp;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 部门名称
     */
    private String deptName;          //部门名称
    /**
     * 全名
     */
    private String fullName;          //全名
    /**
     * 登录失败次数统计
     */
    private Integer loginFailureCount;        //登录失败次数统计
    
    public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}