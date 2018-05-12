package com.ss.file.pojo.ftp;

import java.io.Serializable;
import java.util.Date;

/**
 * FTP配置类
 * 
 * @author 王玉平 2017-12-05
 */
public class FileFtpConf implements Serializable {
	private Integer confId;

	/** ftp IP地址 */
	private String ftpAddress;

	/** ftp 端口号 */
	private Integer ftpPort;

	/** ftp 登录名 */
	private String ftpUsername;

	/** ftp 登录密码 */
	private String ftpPassword;

	/** ftp 基准目录 */
	private String ftpBasePath;

	/** ftp URL */
	private String ftpUrl;

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

	public Integer getConfId() {
		return confId;
	}

	public void setConfId(Integer confId) {
		this.confId = confId;
	}

	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress == null ? null : ftpAddress.trim();
	}

	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername == null ? null : ftpUsername.trim();
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword == null ? null : ftpPassword.trim();
	}

	public String getFtpBasePath() {
		return ftpBasePath;
	}

	public void setFtpBasePath(String ftpBasePath) {
		this.ftpBasePath = ftpBasePath == null ? null : ftpBasePath.trim();
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl == null ? null : ftpUrl.trim();
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
		sb.append(", confId=").append(confId);
		sb.append(", ftpAddress=").append(ftpAddress);
		sb.append(", ftpPort=").append(ftpPort);
		sb.append(", ftpUsername=").append(ftpUsername);
		sb.append(", ftpPassword=").append(ftpPassword);
		sb.append(", ftpBasePath=").append(ftpBasePath);
		sb.append(", ftpUrl=").append(ftpUrl);
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