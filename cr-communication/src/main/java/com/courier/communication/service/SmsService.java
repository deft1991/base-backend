package com.courier.communication.service;

import com.couriers.sms.model.VerificationSmsDto;

public interface SmsService {

    /**
     * Send verification sms and save to data base
     *
     * @param smsDto - dto for send
     * @return - VerificationSmsDto with verification code
     */
    VerificationSmsDto sendSms(VerificationSmsDto smsDto);

}
