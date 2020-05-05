package com.courier.communication.service.impl;

import com.courier.communication.config.ProviderSmsConfiguration;
import com.courier.communication.service.ProviderSmsService;
import com.couriers.sms.model.AeroSmsResponceDto;
import com.couriers.sms.model.VerificationSmsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Need to add header APPLICATION_JSON
 * Need to add basic auth to header with account email :  secret_key
 * Need to change @ in account email on %40
 * Example :
 * headers.setContentType(MediaType.APPLICATION_JSON);
 * headers.setBasicAuth("deft1991@gmail.com", "aVbYV5t8AgjcPk8RWBcRgF2O6ri3");
 * "https://deft1991%40gmail.com:aVbYV5t8AgjcPk8RWBcRgF2O6ri3@gate.smsaero.ru/v2/sms/send?
 * number=79992032439&text=3610.8609792036136&sign=SMS Aero&channel=DIRECT"
 * Created by sgolitsyn on 4/25/20
 */

@Profile("prod")
@Service
@RequiredArgsConstructor
public class AeroSmsProviderServiceImpl implements ProviderSmsService {

    public static final String SEND_SMS_URL_TEMPLATE = "%s:%s@gate.smsaero.ru/v2/sms/send?number=%.0f&text=%s&sign=%s&channel=%s";
    private final ProviderSmsConfiguration aeroSmsConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    //todo in future change type on more common
    public VerificationSmsDto sendSms(VerificationSmsDto smsDto) {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.setBasicAuth("deft1991@gmail.com", "aVbYV5t8AgjcPk8RWBcRgF2O6ri3");
        HttpEntity request = new HttpEntity(headers);

        String fooResourceUrl = String.format(SEND_SMS_URL_TEMPLATE,
                aeroSmsConfig.getAeroSms().getAccountEmail(),
                aeroSmsConfig.getAeroSms().getApiKey(),
                smsDto.getPhoneNumber(),
                smsDto.getVerificationCode(),
                aeroSmsConfig.getAeroSms().getSign(),
                aeroSmsConfig.getAeroSms().getChannel());

        ResponseEntity<String> responce = restTemplate.exchange(
                fooResourceUrl, HttpMethod.GET, request, String.class);

        AeroSmsResponceDto aeroSmsResponceDto = objectMapper.readValue(responce.getBody(), AeroSmsResponceDto.class);
        // get(0) because we always send 1 sms and get 1 data result
        smsDto.setResponceSmsId(aeroSmsResponceDto.getData().get(0).getId());
        return smsDto;
    }
}
