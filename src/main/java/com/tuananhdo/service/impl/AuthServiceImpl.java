package com.tuananhdo.service.impl;

import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.BlogAPIException;
import com.tuananhdo.payload.LoginDTO;
import com.tuananhdo.payload.RegisterDTO;
import com.tuananhdo.repository.RoleRepository;
import com.tuananhdo.repository.UserRepository;
import com.tuananhdo.security.JwtTokenProvider;
import com.tuananhdo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOfEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDTO registerDTO) throws RoleNotFoundException {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!");
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Cannot find any role:" + roles));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully!";
    }
}
