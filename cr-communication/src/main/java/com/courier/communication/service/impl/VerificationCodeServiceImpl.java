package com.courier.communication.service.impl;

import com.courier.communication.service.VerificationCodeService;
import org.springframework.stereotype.Service;

/*
 * Created by sgolitsyn on 4/25/20
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Override
    public String getVerificationCode() {
        return String.valueOf(randDouble(1000, 9999));
    }

    public static int randDouble(double bound1, double bound2) {
        //make sure bound2> bound1
        double min = Math.min(bound1, bound2);
        double max = Math.max(bound1, bound2);
        //math.random gives random number from 0 to 1
        return (int) (min + (Math.random() * (max - min)));
    }
}
