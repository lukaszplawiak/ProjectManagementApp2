package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.RoleRepository;
import com.lukaszplawiak.projectapp.repository.UserRepository;
import com.lukaszplawiak.projectapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserEntityMapper.mapToUserEntity;
import static com.lukaszplawiak.projectapp.service.impl.mapper.UserResponseDtoMapper.mapToUserResponseDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(String email) {
        logger.info("Fetch user " + email + " from database");
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserResponseDto> getDtoUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> usersResponse = users.stream()
                .map(user -> mapToUserResponseDto(user))
                .collect(Collectors.toList());
        logger.info("Fetch all users");
        return usersResponse;
    }

    @Override
    public List<Role> getRoles() {
        logger.info("Fetch all roles");
        return roleRepository.findAll();
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = mapToUserEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Save new user " + user.getFirstName() + " to database");
        userRepository.save(user);
        return mapToUserResponseDto(user);
    }

    @Override
    public Role saveRole(Role role) {
        logger.info("Save new role " + role.getName() + " to database");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email); // validacje itp
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        logger.info("Add new role " + roleName + " to user " + email);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteUserByEmail(email);
        logger.info("Deleted user: " + email);
    }
}
