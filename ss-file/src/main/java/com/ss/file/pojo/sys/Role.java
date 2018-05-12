package com.ss.file.pojo.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Role implements Serializable {

	private static final long serialVersionUID = -3115988674090166863L;

	private Long seqId;

    private Date createTime;

    private Long createUser;

    private String description;

    private Boolean isSystem;

    private String roleName;
    
    private String authorityListStore;
    
    private Map<String, String> authorityList;

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

	public String getAuthorityListStore() {
		return authorityListStore;
	}

	public void setAuthorityListStore(String authorityListStore) {
		this.authorityListStore = authorityListStore;
	}

	public Map<String, String> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(Map<String, String> authorityList) {
		this.authorityList = authorityList;
	}
}