package com.courier.communication.service;

/*
 * Created by sgolitsyn on 4/25/20
 */
public interface VerificationCodeService {

    /**
     *
     * @return String - in future we can send not only digits
     */
    String getVerificationCode();
}
