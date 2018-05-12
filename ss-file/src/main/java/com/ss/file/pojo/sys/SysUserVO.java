package com.ss.file.pojo.sys;

import java.io.Serializable;
import java.util.Date;

public class SysUserVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer seqId;

    private String userName;

    private String systemCode;

    private String loginName;

    private String loginPassword;

    private Integer state;

    private Integer category;

    private String createUser;

    private Date createTimeStart;
    
    private Date createTimeEnd;

    private String updateUser;

    private Date updateTimeStart;

    private Date  updateTimeEnd;
    
    private String loginIp;

    private Date loginTimeStart;
    
    private Date loginTimeEnd;

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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

   

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

  

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Date getLoginTimeStart() {
		return loginTimeStart;
	}

	public void setLoginTimeStart(Date loginTimeStart) {
		this.loginTimeStart = loginTimeStart;
	}

	public Date getLoginTimeEnd() {
		return loginTimeEnd;
	}

	public void setLoginTimeEnd(Date loginTimeEnd) {
		this.loginTimeEnd = loginTimeEnd;
	}

    
}