package com.myproject.expensetrackerapi.controller;

import com.myproject.expensetrackerapi.entity.User;
import com.myproject.expensetrackerapi.model.JwtResponse;
import com.myproject.expensetrackerapi.model.LoginModel;
import com.myproject.expensetrackerapi.model.UserModel;
import com.myproject.expensetrackerapi.service.CustomUserDetailsService;
import com.myproject.expensetrackerapi.service.UserService;
import com.myproject.expensetrackerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {


       /* Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getEmail(),login.getPassword()));*/

        authenticate(login.getEmail(),login.getPassword());

        final UserDetails userDetails=userDetailsService.loadUserByUsername(login.getEmail());

        final  String token=jwtTokenUtil.generateToken(userDetails);

        //  SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email,String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }catch (DisabledException e) {
            throw new Exception("User disabled");
        }catch (BadCredentialsException e) {
            throw new Exception("Bad credentials");
        }
    }



    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
