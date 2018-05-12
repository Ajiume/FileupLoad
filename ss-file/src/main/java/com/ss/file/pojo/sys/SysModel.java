package com.ss.file.pojo.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 模块信息
 * 
 * @author 王玉平 2017-12-06
 * 
 */
public class SysModel implements Serializable {
	/** id */
	private Integer modelId;

	/** 名称 */
	private String modelName;

	/** 模块路径 */
	private String modelPath;

	/** 备注信息 */
	private String remark;

	/** 创建人 */
	private String createUser;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String updateUser;

	/** 修改时间 */
	private Date updateTime;

	private static final long serialVersionUID = 1L;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}
	
	public String getModelPath() {
		return modelPath;
	}
	
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath == null ? null : modelPath.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", modelId=").append(modelId);
		sb.append(", modelName=").append(modelName);
		sb.append(", remark=").append(remark);
		sb.append(", createUser=").append(createUser);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateUser=").append(updateUser);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}