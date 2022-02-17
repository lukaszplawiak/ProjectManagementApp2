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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        userFirstNameValidation(userRequestDto);
        userLastNameValidation(userRequestDto);
        userEmailValidation(userRequestDto);
        userPasswordValidation(userRequestDto);
        User user = mapToUserEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Save new user " + user.getFirstName() + " to database");
        userRepository.save(user);
        return mapToUserResponseDto(user);
    }

    @Override
    public Role saveRole(Role role) {
        roleNameValidation(role);
        logger.info("Save new role " + role.getName() + " to database");
        return roleRepository.save(role);
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
    public void addRoleToUser(String email, String roleName) {
        recordNotFoundInDatabase(email, roleName);
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        logger.info("Add new role " + roleName + " to user " + email);
    }

    @Override
    public void deleteUser(String email) {
        userNotFoundInDatabase(email);
        try {
        userRepository.deleteUserByEmail(email);
        logger.info("Deleted user: " + email);
        } catch (Exception e) {
            logger.info("There are other users' projects or tasks that are based on projects or tasks of user: " + email);
            throw new RuntimeException(e);
        }
    }

    private void userFirstNameValidation(UserRequestDto userRequestDto) {
        if (userRequestDto.getFirstName() == null || userRequestDto.getFirstName().length() < 1) {
            logger.info("User's first name must not be blank");
            throw new IllegalInputException();
        }
    }

    private void userLastNameValidation(UserRequestDto userRequestDto) {
        if (userRequestDto.getLastName() == null || userRequestDto.getLastName().length() < 1) {
            logger.info("User's last name must not be blank");
            throw new IllegalInputException();
        }
    }

    private void userEmailValidation(UserRequestDto userRequestDto) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userRequestDto.getEmail());
        if (!matcher.matches()) {
            logger.info("User's email must be properly formatted");
            throw new IllegalInputException();
        }
    }

    private void userPasswordValidation(UserRequestDto userRequestDto) {
        if (userRequestDto.getPassword() == null || userRequestDto.getPassword().length() < 4) {
            logger.info("User's password must be 4 or more characters");
            throw new IllegalInputException();
        }
    }

    private void roleNameValidation(Role role) {
        if (role.getName() == null || role.getName().length() < 1) {
            logger.info("Role must be 1 or more characters");
            throw new IllegalInputException();
        }
    }

    private void recordNotFoundInDatabase(String email, String roleName) {
        if (userRepository.findByEmail(email) == null || roleRepository.findByName(roleName) == null) {
            logger.info("Record not found in database");
            throw new IllegalInputException();
        }
    }

    private void userNotFoundInDatabase(String email) {
        if (userRepository.findByEmail(email) == null) {
            logger.info("User not found in database");
            throw new IllegalInputException();
        }
    }
}