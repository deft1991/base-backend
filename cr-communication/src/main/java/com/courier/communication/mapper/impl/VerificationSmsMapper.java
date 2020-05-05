package com.courier.communication.mapper.impl;

import com.courier.communication.mapper.Mapper;
import com.courier.communication.model.SmsEntity;
import com.courier.communication.utils.ModelMapperUtils;
import com.couriers.sms.model.VerificationSmsDto;
import org.springframework.stereotype.Service;

@Service
public class VerificationSmsMapper implements Mapper<VerificationSmsDto, SmsEntity> {

    private final ModelMapperUtils modelMapperUtils;

    public VerificationSmsMapper(ModelMapperUtils modelMapperUtils) {
        this.modelMapperUtils = modelMapperUtils;
    }

    @Override
    public VerificationSmsDto convertToDto(SmsEntity smsEntity) {
        return modelMapperUtils.map(smsEntity, VerificationSmsDto.class);
    }

    @Override
    public SmsEntity convertToEntity(VerificationSmsDto verificationSmsDto) {
        return modelMapperUtils.map(verificationSmsDto, SmsEntity.class);
    }
}
