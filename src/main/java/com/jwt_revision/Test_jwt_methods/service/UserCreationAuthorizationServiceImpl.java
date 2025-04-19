package com.jwt_revision.Test_jwt_methods.service;

import com.jwt_revision.Test_jwt_methods.model.*;
import com.jwt_revision.Test_jwt_methods.repository.ForgetPasswordRepository;
import com.jwt_revision.Test_jwt_methods.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class UserCreationAuthorizationServiceImpl implements UserCreationAuthorizationService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SendMailService sendMailToUser;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtServiceImpl jwtServiceMethods;

    @Autowired
    ForgetPasswordRepository forgetPasswordRepository;

    AuthorizationResponseEntity response = new AuthorizationResponseEntity();

    @Override
    public ResponseEntity<AuthorizationResponseEntity> createNewUsers(Users users) {
        // check if
//        1. firstname and lastname are alphabets and within specified range
//        2. email should have followed proper format
//        3. moblie number should be 10 digit number only
//        4. password and confirm password should be same



        String firstname= users.getFirstname();
        String lastname= users.getLastname();
        String email= users.getEmail();
        String password= users.getPassword();
        String phoneNum= users.getMobileNum();
        Role role= users.getRole();

        System.out.println("<-----------User before saving: " + users.getMobileNum());
        String confirmPassword= users.getConfirmPassword();

        if(!firstname.matches("^[A-Za-z]{3,14}$")){
            response.setMessage("Firstname should be alphabetical and length should be between 3 to 14");
            return ResponseEntity.status(401).body(response);
        }

        if(!lastname.matches("^[A-Za-z]{3,14}$")){
            response.setMessage("lastname should be alphabetical and length should be between 3 to 14");
            return ResponseEntity.status(401).body(response);
        }

        if(!phoneNum.matches("\\d{10}")){
            response.setMessage("phone number should be numerical and 10 digit length");
            return ResponseEntity.status(401).body(response);
        }

        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            response.setMessage("enter correct email");
            return ResponseEntity.status(401).body(response);
        }

        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirmPassword);

        if(!password.equals(confirmPassword)){
            response.setMessage("password and confirm password does not matches");
            return ResponseEntity.status(401).body(response);
        }

        Users userDets= new Users();
        userDets.setEmail(email);
        userDets.setFirstname(firstname);
        userDets.setLastname(lastname);
        userDets.setMobileNum(phoneNum);
        userDets.setRole(role);
        userDets.setPassword(passwordEncoder.encode(password));

        usersRepository.save(userDets);
        sendMailToUser.sendEmailToUsers("Account created successfully", "Hi " + firstname+" "+lastname+ "!, Thanks for creating your account on our website", email);

        response.setMessage("User created successfully!");
        String token= jwtServiceMethods.generateToken(userDets);
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthorizationResponseEntity> loginUsers(LoginUsers users) {
        //since token will be generated after login only so we will have the following checks:-
        //1. check if username entered by user exists in our db
        //2. check if password is correct for that username

        String providedEmail= users.getEmail();
        Users userDets= usersRepository.findByEmail(providedEmail);
        if(userDets== null){
            response.setMessage("Invalid username or password!");
            return ResponseEntity.status(401).body(response);
        }
        if(!passwordEncoder.matches(users.getPassword(), userDets.getPassword())){
            response.setMessage("Invalid username or password!");
            return ResponseEntity.status(401).body(response);
        }
        response.setMessage("User login successfully!");
        String token= jwtServiceMethods.generateToken(userDets);
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthorizationResponseEntity> validateEmailAndShareOtp(String email) {
        //check if user exists in our db
        //if user exists then share otp for password setup
        Users userDetails= usersRepository.findByEmail(email);
        if(userDetails != null){
            Integer otp= generateOtp();
            sendMailToUser.sendEmailToUsers("otp for setting password", "Hi! "+userDetails.getFirstname()+" "+userDetails.getLastname()+" here is the otp for setting password: "+otp,email);

            ForgetPasswordEntity forgetPasswordDets= new ForgetPasswordEntity();
            forgetPasswordDets.setOtp(otp);
            forgetPasswordDets.setExpirationTime(new Date(System.currentTimeMillis()+ 5 * 60 * 1000));
            forgetPasswordDets.setUsersData(userDetails);

            // check if user already exists in otp_details table

//            ForgetPasswordEntity otpDets= forgetPasswordRepository.findByUserid(userDetails.getId());
//            if(otpDets != null){
//
//            }

            forgetPasswordRepository.save(forgetPasswordDets);
            response.setMessage("Otp is shared to user!");
            response.setToken(null);
            return ResponseEntity.ok(response);
        }
        response.setMessage("Invalid Email!");
        return ResponseEntity.status(401).body(response);
    }

    @Override
    public Integer generateOtp(){
        Random random= new Random();
        return random.nextInt(1000, 99999);
    }

    @Override
    public ResponseEntity<AuthorizationResponseEntity> verifyOtpEnteredByUser(String email, Integer otp) {
        // check if otp is valid
        // check if otp is shared for correct mapped email
        // check if expiration time of otp is surpassed
        ForgetPasswordEntity forgetPasswordDets= forgetPasswordRepository.findByOtp(otp);
        if(forgetPasswordDets != null){
            forgetPasswordDets= forgetPasswordRepository.findByOtpAndEmail(email,otp);
            if(forgetPasswordDets != null){
                Date expTime= forgetPasswordDets.getExpirationTime();
                if(expTime.after(new Date(System.currentTimeMillis()))) {
                    response.setMessage("Otp verification successfull");
                    response.setToken(null);
                    return ResponseEntity.ok(response);
                }
            }
        }
        response.setMessage("Otp verification failed");
        return ResponseEntity.status(400).body(response);
    }

    @Override
    public ResponseEntity<AuthorizationResponseEntity> setNewUserPassword(String email, UpdatePassword updatePassword) {
        // user will provide new password, save it
        if(updatePassword.getPassword().matches(updatePassword.getConfirmPassword())){
            String newPassword= passwordEncoder.encode(updatePassword.getPassword());
            usersRepository.setNewPassword(email, newPassword);
            response.setMessage("Password reset successfully!");
            response.setToken(null);
            return ResponseEntity.ok(response);
        }
        response.setMessage("Password and confirm password does not match!");
        return ResponseEntity.status(400).body(response);
    }
}
