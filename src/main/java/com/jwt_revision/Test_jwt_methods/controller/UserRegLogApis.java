package com.jwt_revision.Test_jwt_methods.controller;

import com.jwt_revision.Test_jwt_methods.model.AuthorizationResponseEntity;
import com.jwt_revision.Test_jwt_methods.model.LoginUsers;
import com.jwt_revision.Test_jwt_methods.model.UpdatePassword;
import com.jwt_revision.Test_jwt_methods.model.Users;
import com.jwt_revision.Test_jwt_methods.service.UserCreationAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("v1/user")
public class UserRegLogApis {

    @Autowired
    UserCreationAuthorizationService userLogRegService;

    @PostMapping("/register")
    public ResponseEntity<AuthorizationResponseEntity> registeration(@RequestBody Users users){
        System.out.println("Check Mobile------> " + users.getMobileNum());
        return userLogRegService.createNewUsers(users);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponseEntity> login(@RequestBody LoginUsers users){
        return userLogRegService.loginUsers(users);
    }

    @PostMapping("/forget-password/verify-email/{email}")
    public ResponseEntity<AuthorizationResponseEntity> forgetPassword(@PathVariable String email){
        return userLogRegService.validateEmailAndShareOtp(email);
    }

    @PostMapping("/verify-otp/{email}/{otp}")
    public ResponseEntity<AuthorizationResponseEntity> verifyOtp(@PathVariable String email, @PathVariable Integer otp){
        return userLogRegService.verifyOtpEnteredByUser(email,otp);
    }

    @PostMapping("/set-new-password/{email}")
    public ResponseEntity<AuthorizationResponseEntity> setNewPassword(@PathVariable String email, @RequestBody UpdatePassword updatePassword){
        return userLogRegService.setNewUserPassword(email, updatePassword);
    }
}
