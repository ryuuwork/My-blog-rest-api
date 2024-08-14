package com.tuananhdo.service;

import com.tuananhdo.payload.LoginDTO;
import com.tuananhdo.payload.RegisterDTO;

import javax.management.relation.RoleNotFoundException;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO) throws RoleNotFoundException;
}
