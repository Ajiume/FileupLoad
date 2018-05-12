package com.ss.file.service.ftp.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.file.dao.ftp.FileMainDao;
import com.ss.file.pojo.ftp.FileMain;
import com.ss.file.service.ftp.FileFtpConfService;
import com.ss.file.service.ftp.FileMainService;
import com.ss.platform.base.dao.BaseDAO;
import com.ss.platform.base.page.PageEO;
import com.ss.platform.base.page.PageParam;
import com.ss.platform.base.serviceimpl.BaseServiceImpl;

@Service
public class FileMainServerImpl extends BaseServiceImpl<FileMain> implements
		FileMainService {
	
	public static Logger logger = LoggerFactory.getLogger(FileMainServerImpl.class);

	@Autowired
	private FileMainDao<FileMain> fileMainDao;
	
	@Autowired
	private FileFtpConfService confService;
	
	@Override
	public PageEO listFileMain(PageParam pageParam) {
		return fileMainDao.queryByList(pageParam);
	}

	@Override
	public BaseDAO<FileMain> getDao() {
		return fileMainDao;
	}

	@Override
	public String deleteByPrimaryKey(String[] ids) {
		
//		List<FileFtpConf> confList = confService.selectAll();
//		if (confList == null || confList.size() == 0) {
//			return "没找到FTP服务器!";
//		}
//		FileFtpConf conf = confList.get(0);
		
		for (int i = 0; i < ids.length; i++) {
			int id = Integer.valueOf(ids[i]);
			FileMain fileMain = fileMainDao.selectByPrimaryKey(id);
			if (fileMain != null) {
				fileMainDao.deleteByPrimaryKey(id);
				String fileName = fileMain.getFdFilePath() + "/" + fileMain.getFdDestName();
				File file = new File(fileName);
		        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		        if (file.exists() && file.isFile()) {
		            if (file.delete()) {
		            	logger.info("删除单个文件" + fileName + "成功！");
		            } else {
		            	logger.info("删除单个文件" + fileName + "失败！");
		            }
		        } else {
		        	logger.info("删除单个文件失败：" + fileName + "不存在！");
		        }
				
			} else {
				return "没有找到该文件!";
			}
		}
		return "删除成功!";
	}

	@Override
	public FileMain selectByPrimaryKey(Integer fdId) {
		return fileMainDao.selectByPrimaryKey(fdId);
	}

	@Override
	public int insertByParams(Map<String, Object> params) {
		return fileMainDao.insertByParams(params);
	}

	@Override
	public int updateByParams(Map<String, Object> params) {
		return fileMainDao.updateByParams(params);
	}

	@Override
	public Integer insertSelective(FileMain record) {
		return fileMainDao.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FileMain record) {
		return fileMainDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<FileMain> selectByParams(FileMain record) {
		return fileMainDao.selectByParams(record);
	}

}
