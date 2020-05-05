package com.courier.communication.model;

import com.couriers.sms.model.VerificationSmsDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "sms", schema = "crsms")
public class SmsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String externalUserId;

    @Column(nullable = false, columnDefinition = "User phone number")
    private Double phoneNumber;

    @Column(nullable = false, columnDefinition = "Type of sms")
    @Enumerated(EnumType.STRING)
    private VerificationSmsDto.SmsTypeEnum smsType;

    @Column(nullable = false, columnDefinition = "Verification code for user")
    private String verificationCode;

    @Column(nullable = false, columnDefinition = "Responce sms id from provider")
    private String responceSmsId;

    @Column(nullable = false, columnDefinition = "Expiration date for verification code")
    private Timestamp expirationDate;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdDate;

    @LastModifiedDate
    private Timestamp updatedDate;
}
