package com.ss.file.pojo.ftp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 上传的文件信息
 * 
 * @author 王玉平 2017-12-06
 * 
 */
public class FileMain implements Serializable {

	private Integer fdId;

	/** 文件所属模块Id */
	private Integer fdModelId;

	/** 文件所属模块名称 */
	private String fdModelName;

	/** 文件Key */
	private String fdKey;

	/** 文件原始名称 */
	private String fdSrcName;

	/** 文件目标名称 */
	private String fdDestName;

	/** 文件内容类型 */
	private String fdContentType;

	/** 文件类型 */
	private String fdAttType;

	/** 文件创建时间 */
	private Date docCreateTime;

	/** 文件路径Id */
	private String fdFilePath;

	/** 文件用户Id */
	private String fdPersonId;

	/** 文件最近打开时间 */
	private Date fdLastOpenTime;

	/** 文件大小 */
	private BigDecimal fdSize;

	/** 文件位置 */
	private String fdAttLocation;

	/** 文件顺序 */
	private Integer fdOrder;

	/** 文件创建者 */
	private String fdCreatorId;

	/** 下载次数 */
	private Integer downloadSum;

	/** 文件Id */
	private String fdFileId;

	/** 创建人 */
	private String createUser;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String updateUser;

	/** 修改时间 */
	private Date updateTime;
	
	/** 内容描述 */
	private String fdContentDescribe;

	private static final long serialVersionUID = 1L;

	public Integer getFdId() {
		return fdId;
	}

	public void setFdId(Integer fdId) {
		this.fdId = fdId;
	}

	public Integer getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(Integer fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName == null ? null : fdModelName.trim();
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey == null ? null : fdKey.trim();
	}

	public String getFdSrcName() {
		return fdSrcName;
	}

	public void setFdSrcName(String fdSrcName) {
		this.fdSrcName = fdSrcName == null ? null : fdSrcName.trim();
	}

	public String getFdDestName() {
		return fdDestName;
	}

	public void setFdDestName(String fdDestName) {
		this.fdDestName = fdDestName == null ? null : fdDestName.trim();
	}

	public String getFdContentType() {
		return fdContentType;
	}

	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType == null ? null : fdContentType
				.trim();
	}

	public String getFdAttType() {
		return fdAttType;
	}

	public void setFdAttType(String fdAttType) {
		this.fdAttType = fdAttType == null ? null : fdAttType.trim();
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdFilePath() {
		return fdFilePath;
	}

	public void setFdFilePath(String fdFilePath) {
		this.fdFilePath = fdFilePath == null ? null : fdFilePath.trim();
	}

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId == null ? null : fdPersonId.trim();
	}

	public Date getFdLastOpenTime() {
		return fdLastOpenTime;
	}

	public void setFdLastOpenTime(Date fdLastOpenTime) {
		this.fdLastOpenTime = fdLastOpenTime;
	}

	public BigDecimal getFdSize() {
		return fdSize;
	}

	public void setFdSize(BigDecimal fdSize) {
		this.fdSize = fdSize;
	}

	public String getFdAttLocation() {
		return fdAttLocation;
	}

	public void setFdAttLocation(String fdAttLocation) {
		this.fdAttLocation = fdAttLocation == null ? null : fdAttLocation
				.trim();
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId == null ? null : fdCreatorId.trim();
	}

	public Integer getDownloadSum() {
		return downloadSum;
	}

	public void setDownloadSum(Integer downloadSum) {
		this.downloadSum = downloadSum;
	}

	public String getFdFileId() {
		return fdFileId;
	}

	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId == null ? null : fdFileId.trim();
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
	
	public String getFdContentDescribe() {
		return fdContentDescribe;
	}

	public void setFdContentDescribe(String fdContentDescribe) {
		this.fdContentDescribe = fdContentDescribe;
	}

	@Override
	public String toString() {
		return "FileMain [fdId=" + fdId + ", fdModelId=" + fdModelId + ", fdModelName=" + fdModelName + ", fdKey=" + fdKey + ", fdSrcName="
				+ fdSrcName + ", fdDestName=" + fdDestName + ", fdContentType=" + fdContentType + ", fdAttType=" + fdAttType + ", docCreateTime="
				+ docCreateTime + ", fdFilePath=" + fdFilePath + ", fdPersonId=" + fdPersonId + ", fdLastOpenTime=" + fdLastOpenTime + ", fdSize="
				+ fdSize + ", fdAttLocation=" + fdAttLocation + ", fdOrder=" + fdOrder + ", fdCreatorId=" + fdCreatorId + ", downloadSum="
				+ downloadSum + ", fdFileId=" + fdFileId + ", createUser=" + createUser + ", createTime=" + createTime + ", updateUser=" + updateUser
				+ ", updateTime=" + updateTime + ", fdContentDescribe=" + fdContentDescribe + "]";
	}
}