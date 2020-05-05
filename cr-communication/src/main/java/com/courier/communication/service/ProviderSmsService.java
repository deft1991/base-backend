package com.courier.communication.service;

import com.couriers.sms.model.VerificationSmsDto;

/*
 * Created by sgolitsyn on 4/27/20
 */
public interface ProviderSmsService {

    /**
     * Send sms via specialized provider
     *
     * @param smsDto - dto for send
     * @return - VerificationSmsDto with verification code
     */
    VerificationSmsDto sendSms(VerificationSmsDto smsDto);
}
