package com.courier.communication.service.impl;

import com.courier.communication.mapper.impl.VerificationSmsMapper;
import com.courier.communication.model.SmsEntity;
import com.courier.communication.repository.SmsRepository;
import com.courier.communication.service.ProviderSmsService;
import com.courier.communication.service.SmsService;
import com.couriers.sms.model.VerificationSmsDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service("smsService")
@Log4j2
public class SmsServiceImpl implements SmsService {

    private final SmsRepository smsRepository;
    private final ProviderSmsService aeroSmsService;
    private final VerificationSmsMapper verificationSmsMapper;
    private final VerificationCodeServiceImpl verificationCodeService;

    public SmsServiceImpl(SmsRepository smsRepository,
                          ProviderSmsService aeroSmsService,
                          VerificationSmsMapper verificationSmsMapper,
                          VerificationCodeServiceImpl verificationCodeService) {
        this.smsRepository = smsRepository;
        this.aeroSmsService = aeroSmsService;
        this.verificationSmsMapper = verificationSmsMapper;
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * Send sms via aeroSmsService.
     * Use id from aeroSmsService for emtity
     * Convert dto to entity
     * Save sent sms
     * Return saved dto with id and verification code
     *
     * @param smsDto - dto for send
     * @return - saved dto with id and exp date time
     */
    @Override
    public VerificationSmsDto sendSms(VerificationSmsDto smsDto) {
        smsDto.setVerificationCode(verificationCodeService.getVerificationCode());
        VerificationSmsDto sentSms = aeroSmsService.sendSms(smsDto);
        SmsEntity smsEntity = verificationSmsMapper.convertToEntity(sentSms);
        smsEntity.setExpirationDate(Timestamp.from(Instant.now().plus(10, ChronoUnit.MINUTES)));
        SmsEntity savedSms = smsRepository.save(smsEntity);
        return verificationSmsMapper.convertToDto(savedSms);
    }
}
