package com.smileshark.mapper;

import com.smileshark.entity.user.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdministratorMapper {
    List<Administrator> getAdministratorNameList();
    Administrator selectAdministratorByAccount(@Param("administratorAccount") String administratorAccount);
    Administrator selectAdministratorById(@Param("administratorId") Integer administratorId);
}
