package com.easyauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyauth.domain.DTO.UserDTO;
import com.easyauth.domain.entity.User;

public interface UserService extends IService<User> {
    public User getByUsername(String username);

    void switchStatus(Long id, Long status);

    void add(UserDTO userDTO);

    void edit(UserDTO userDTO);
}
