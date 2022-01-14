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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserEntityMapper.mapToUserEntity;
import static com.lukaszplawiak.projectapp.service.impl.mapper.UserResponseDtoMapper.mapToUserResponseDto;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.info("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            logger.info("User " + email + " found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(UserRequestDto userRequestDto) {
        User user = mapToUserEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Save new user " + user.getFirstName() + " to database");
        return userRepository.save(user);
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

    @Override
    public User getUser(String email) {
        logger.info("Fetch user " + email + " to database");
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> usersResponse = users.stream()
                .map(user -> mapToUserResponseDto(user)).collect(Collectors.toList());
        logger.info("Fetch all users");
        return usersResponse;
    }



}
