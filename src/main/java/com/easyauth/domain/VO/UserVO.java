package com.easyauth.domain.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime lastLogin;
    private LocalDateTime registerTime;
    private Long status;

    private List<Long> rolesId;
}
