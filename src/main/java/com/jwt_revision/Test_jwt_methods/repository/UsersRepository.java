package com.jwt_revision.Test_jwt_methods.repository;


import com.jwt_revision.Test_jwt_methods.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    public Users findByEmail(String email);

    @Modifying
    @Transactional
    @Query("Update Users u set u.password= :password where u.email= :email")
    public void setNewPassword(@Param("email") String email, @Param("password") String password);
}
