package com.ss.file.mapper.ftp;

import java.util.List;
import java.util.Map;

import com.ss.file.pojo.ftp.FileMain;
import com.ss.platform.base.mapper.BaseEOMapper;

public interface FileMainMapper<T> extends BaseEOMapper<T> {
	
    int deleteByPrimaryKey(Integer fdId);

    int insert(FileMain record);

    Integer insertSelective(FileMain record);

    int insertByParams(Map<String, Object> params);
    
    FileMain selectByPrimaryKey(Integer fdId);

    int updateByPrimaryKeySelective(FileMain record);

    int updateByPrimaryKey(FileMain record);

    int updateByParams(Map<String, Object> params);
    
    List<FileMain> selectByParams(FileMain record);
}