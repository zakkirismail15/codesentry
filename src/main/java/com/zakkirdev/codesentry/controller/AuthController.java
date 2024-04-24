package com.zakkirdev.codesentry.controller;

import com.zakkirdev.codesentry.controller.request.LoginRequest;
import com.zakkirdev.codesentry.controller.request.SignUpRequest;
import com.zakkirdev.codesentry.controller.response.ErrorResponse;
import com.zakkirdev.codesentry.controller.response.LoginResponse;
import com.zakkirdev.codesentry.repository.RoleRepository;
import com.zakkirdev.codesentry.repository.UserRepository;
import com.zakkirdev.codesentry.repository.entity.Role;
import com.zakkirdev.codesentry.repository.entity.User;
import com.zakkirdev.codesentry.repository.enums.AccessRole;
import com.zakkirdev.codesentry.util.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.NoSuchElementException;

import static com.zakkirdev.codesentry.util.error.GeneralError.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        String token = jwtUtil.createToken(user);
        LoginResponse loginResponse = new LoginResponse(email, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){
        // add check for username exists in DB
        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role roles = roleRepository.findByName(AccessRole.ROLE_USER).orElse(null);
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
