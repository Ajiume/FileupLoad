package com.ss.file.mapper.sys;

import java.util.List;

import com.ss.file.pojo.sys.Role;
import com.ss.platform.base.mapper.BaseEOMapper;

public interface RoleMapper<T> extends BaseEOMapper<T> {
    int deleteByPrimaryKey(Long seqId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long seqId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> selectByParams(Role record);
}