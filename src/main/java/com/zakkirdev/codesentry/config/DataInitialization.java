package com.zakkirdev.codesentry.config;

import com.zakkirdev.codesentry.repository.RoleRepository;
import com.zakkirdev.codesentry.repository.UserRepository;
import com.zakkirdev.codesentry.repository.entity.Role;
import com.zakkirdev.codesentry.repository.entity.User;
import com.zakkirdev.codesentry.repository.enums.AccessRole;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;

@Component
public class DataInitialization implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitialization.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    private static final String FIRST_NAME = "Super";
    private static final String LAST_NAME = "Admin";
    private static final String USERNAME = "SuperAdmin";
    private static final String EMAIL   = "superadmin@codesentry.com.my";
    private static final String PSWD    = "superadmin";

    private static final Function<AccessRole,Role> registerRole = (accessRole) -> {
        Role role = new Role();
        role.setName(accessRole);
        return role;
    };

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initSuperUser();
    }

    @Transactional
    private void initRole(){
        for(AccessRole role : AccessRole.values()){
            roleRepository.save(registerRole.apply(role));
            LOGGER.info("Role successfully registered: " + role.name());
        }
    }

    @Transactional
    private void initSuperUser() {
        // create a super admin
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PSWD));

        Role role = roleRepository.findByName(AccessRole.ROLE_ADMIN).orElse(null);
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        LOGGER.info("SUPER ADMIN SUCCESSFULLY CREATED");

    }
}
