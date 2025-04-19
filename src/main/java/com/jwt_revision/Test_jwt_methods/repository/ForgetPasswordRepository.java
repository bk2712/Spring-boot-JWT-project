package com.jwt_revision.Test_jwt_methods.repository;

import com.jwt_revision.Test_jwt_methods.model.ForgetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPasswordEntity, UUID> {

    public ForgetPasswordEntity findByOtp(Integer otp);

//    @Query("Select fp from ForgetPasswordEntity where fp.usersData.email=?1 and fp.otp= ?2")
//    public ForgetPasswordEntity findByOtpAndEmail(String email, Integer otp);

    @Query("SELECT fp FROM ForgetPasswordEntity fp WHERE fp.usersData.email = ?1 AND fp.otp = ?2")
    public ForgetPasswordEntity findByOtpAndEmail(String email, Integer otp);

    @Query("Select fp FROM ForgetPasswordEntity fp where fp.usersData.id= :userid")
    public ForgetPasswordEntity findByUserid(@Param("userid") UUID userid);

}
