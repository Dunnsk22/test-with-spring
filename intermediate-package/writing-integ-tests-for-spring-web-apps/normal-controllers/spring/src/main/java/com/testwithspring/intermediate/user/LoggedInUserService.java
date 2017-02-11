package com.testwithspring.intermediate.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoggedInUserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedInUserService.class);

    private final UserRepository repository;

    @Autowired
    LoggedInUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading user by username: {}", username);

        User found = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "No user found with username: %s",
                        username
                )));
        LOGGER.info("Found user: {}", found);

        return new LoggedInUser(found);
    }
}
