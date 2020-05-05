package com.courier.communication.controller;

import com.courier.communication.service.SmsService;
import com.couriers.sms.api.SmsApi;
import com.couriers.sms.model.VerificationSmsDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class SmsController implements SmsApi {

    private final SmsService userService;

    public SmsController(@Qualifier("smsService") SmsService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<VerificationSmsDto> sendVerificationSms(OidcUser loggedInUser, @Valid VerificationSmsDto verificationSmsDto) {
        VerificationSmsDto smsDto = userService.sendSms(verificationSmsDto);
        return new ResponseEntity<>(smsDto, HttpStatus.OK);
    }
}
