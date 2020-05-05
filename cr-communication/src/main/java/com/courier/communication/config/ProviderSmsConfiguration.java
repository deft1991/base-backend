package com.courier.communication.config;

/*
 * Created by sgolitsyn on 4/27/20
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("sms")
@EnableConfigurationProperties
public class ProviderSmsConfiguration {

    private AeroSms aeroSms;

    @Getter
    @Setter
    public static class AeroSms{
        private String accountEmail;
        private String apiKey;
        private String sign;
        private String channel;
    }
}

