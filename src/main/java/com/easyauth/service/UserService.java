package com.easyauth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easyauth.domain.DTO.UserDTO;
import com.easyauth.domain.DTO.UserFormLoginDTO;
import com.easyauth.domain.DTO.UserPageQueryDTO;
import com.easyauth.domain.VO.UserVO;
import com.easyauth.domain.entity.User;

public interface UserService extends IService<User> {

    void switchStatus(Integer id, Integer status);

    void add(UserDTO userDTO);

    void edit(UserDTO userDTO);

    Page<UserVO> conditionSearchWithRoleId(UserPageQueryDTO dto);

    Page<UserVO> conditionSearchWithOutRoleId(UserPageQueryDTO dto);

    Page<UserVO> getList(Integer current, Integer size);

    String formLogin(UserFormLoginDTO dto);
}
