package com.lukaszplawiak.projectapp.service.impl;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            logger.info("User " + username + " found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Save new user " + user.getName() + " to database");
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        logger.info("Save new role " + role.getName() + " to database");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username); // validacje itp
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        logger.info("Add new role " + roleName + " to user " + username);
    }

    @Override
    public User getUser(String username) {
        logger.info("Fetch user " + username + " to database");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        logger.info("Fetch all users");
        return userRepository.findAll();
    }

}
