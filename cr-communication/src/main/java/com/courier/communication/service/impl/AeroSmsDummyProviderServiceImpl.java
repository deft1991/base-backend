package com.courier.communication.service.impl;

/*
 * Dummy sms provider for sms service
 * Created by sgolitsyn on 4/27/20
 */

import com.courier.communication.service.ProviderSmsService;
import com.couriers.sms.model.AeroSmsResponceDto;
import com.couriers.sms.model.VerificationSmsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!prod")
@Service
@RequiredArgsConstructor
public class AeroSmsDummyProviderServiceImpl implements ProviderSmsService {

    private String aeroResponceBody = "{\"success\":true,\"data\":{\"id\":266082741,\"from\":\"SMS Aero\",\"number\":\"79995185386\",\"text\":\"7595\",\"status\":8,\"extendStatus\":\"moderation\",\"channel\":\"DIRECT\",\"cost\":2.69,\"dateCreate\":1587995045,\"dateSend\":1587995045},\"message\":null}";
    private int status = 200;
    private final ObjectMapper objectMapper;

    /**
     * In dummy service we do not set ID because we can have collision.
     * Prod service give us guaranties about sms id
     *
     * @param smsDto - dto for send
     * @return VerificationSmsDto
     */
    @SneakyThrows
    @Override
    public VerificationSmsDto sendSms(VerificationSmsDto smsDto) {
        AeroSmsResponceDto aeroSmsResponceDto = objectMapper.readValue(aeroResponceBody, AeroSmsResponceDto.class);
        // get(0) because we always send 1 sms and get 1 data result
        smsDto.setResponceSmsId(aeroSmsResponceDto.getData().get(0).getId());
        return smsDto;
    }
}
