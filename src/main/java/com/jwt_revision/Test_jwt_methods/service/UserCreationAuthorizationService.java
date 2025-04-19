package com.jwt_revision.Test_jwt_methods.service;

import com.jwt_revision.Test_jwt_methods.model.AuthorizationResponseEntity;
import com.jwt_revision.Test_jwt_methods.model.LoginUsers;
import com.jwt_revision.Test_jwt_methods.model.UpdatePassword;
import com.jwt_revision.Test_jwt_methods.model.Users;
import org.springframework.http.ResponseEntity;

public interface UserCreationAuthorizationService {

    public ResponseEntity<AuthorizationResponseEntity> loginUsers(LoginUsers users);

    public ResponseEntity<AuthorizationResponseEntity> createNewUsers(Users users);

    public ResponseEntity<AuthorizationResponseEntity> validateEmailAndShareOtp(String email);

    Integer generateOtp();

    public ResponseEntity<AuthorizationResponseEntity> verifyOtpEnteredByUser(String email, Integer otp);

    public ResponseEntity<AuthorizationResponseEntity> setNewUserPassword(String email, UpdatePassword updatePassword);
}
