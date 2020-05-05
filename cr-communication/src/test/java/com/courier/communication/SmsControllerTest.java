package com.courier.communication;

import com.couriers.sms.model.VerificationSmsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Created by sgolitsyn on 4/27/20
 */
@ControllerTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class SmsControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @WithMockUser("spring")
    @Test
    public void sendSms() throws Exception {
        this.mvc.perform(post("/sms/sendSms")
                .content(objectMapper.writeValueAsString(createTestDataForSave()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("externalUserId").exists())
                .andExpect(jsonPath("externalUserId").isNotEmpty())
                .andExpect(jsonPath("externalUserId", is("a72e814a-8721-422f-8986-e87c989c84e1")))
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("phoneNumber").isNotEmpty())
                .andExpect(jsonPath("phoneNumber", is(7.9995185386E10)))
                .andExpect(jsonPath("smsType").exists())
                .andExpect(jsonPath("smsType").isNotEmpty())
                .andExpect(jsonPath("smsType", is("VERIFICATION")))
                .andExpect(jsonPath("responceSmsId").exists())
                .andExpect(jsonPath("responceSmsId").isNotEmpty())
                .andExpect(jsonPath("responceSmsId", is(2.66082741E8)));
    }

    private VerificationSmsDto createTestDataForSave() {
        VerificationSmsDto smsDto = new VerificationSmsDto();
        smsDto.setExternalUserId("a72e814a-8721-422f-8986-e87c989c84e1");
        smsDto.setPhoneNumber(79995185386D);
        smsDto.setSmsType(VerificationSmsDto.SmsTypeEnum.VERIFICATION);
        return smsDto;
    }
}
