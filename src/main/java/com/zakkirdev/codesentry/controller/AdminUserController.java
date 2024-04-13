package com.zakkirdev.codesentry.controller;

import com.zakkirdev.codesentry.controller.request.AdminUserReq;
import com.zakkirdev.codesentry.repository.RoleRepository;
import com.zakkirdev.codesentry.repository.UserRepository;
import com.zakkirdev.codesentry.repository.entity.User;
import com.zakkirdev.codesentry.repository.enums.AccessRole;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/modify")
    public void modifyUserAccess(@RequestBody AdminUserReq request){
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        // aggregate the request into action modifier
        if(StringUtils.isNotBlank(request.getRole())){
            AccessRole accessRole = AccessRole.getRoleByName(request.getRole());
            // remove existing role, one user only one role
            user.getRoles().clear();
            roleRepository.findByName(accessRole).ifPresent(role -> Objects.requireNonNull(user).addRole(role));
        }

        // finally save user data
        userRepository.save(user);
    }


}
