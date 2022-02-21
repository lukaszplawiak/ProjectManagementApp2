package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
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
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = mapToUserEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.trace("Save new user " + user.getEmail() + " to database");
        userRepository.save(user);
        return mapToUserResponseDto(user);
    }

    @Override
    public Role saveRole(Role role) {
        roleNameValidation(role);
        logger.trace("Save new role " + role.getName() + " to database");
        return roleRepository.save(role);
    }

    @Override
    public User getUser(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserResponseDto> getDtoUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapToUserResponseDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        recordNotFoundInDatabase(email, roleName);
        User user = userRepository.getByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        logger.trace("Add new role " + roleName + " to user " + email);
    }

    private void roleNameValidation(Role role) {
        if (role.getName() == null || role.getName().length() < 1) {
            logger.trace("Role must be 1 or more characters");
            throw new IllegalInputException();
        }
    }

    private void recordNotFoundInDatabase(String email, String roleName) {
        if (userRepository.findByEmail(email) == null || roleRepository.findByName(roleName) == null) {
            logger.trace("Record not found in database");
            throw new IllegalInputException();
        }
    }
}