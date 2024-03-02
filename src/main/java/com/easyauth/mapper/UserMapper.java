package com.easyauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyauth.domain.DTO.UserPageQueryDTO;
import com.easyauth.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> pageWithRolesId(@Param("dto") UserPageQueryDTO dto);

    List<Long> selectRolesByUserId(@Param("id") Long id);
}
