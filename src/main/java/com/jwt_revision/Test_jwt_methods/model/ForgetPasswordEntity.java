package com.jwt_revision.Test_jwt_methods.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "otp_details")
public class ForgetPasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "otp", nullable = false)
    private Integer otp;
    @Column(name = "otp_expiration_time")
    private Date expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private Users usersData;

    public ForgetPasswordEntity(UUID id, Integer otp, Date expirationTime, Users usersData) {
        this.id = id;
        this.otp = otp;
        this.expirationTime = expirationTime;
        this.usersData = usersData;
    }

    public ForgetPasswordEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Users getUsersData() {
        return usersData;
    }

    public void setUsersData(Users usersData) {
        this.usersData = usersData;
    }
}
