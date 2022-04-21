package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.ActionDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.model.TempType;
import com.example.smarthome.service.TemperatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.EntityTypeConstant.TEMPERATURE;
import static com.example.smarthome.constant.EntityTypeConstant.TEMP_TYPE_DATA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TemperatureController.class)
class TemperatureControllerTest {
    @MockBean
    TemperatureService temperatureService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("[성공][POST] ledOnOff 테스트")
    public void givenTempType_whenGetTemperature_ThenReturnSpeakerResponseDto() throws Exception{
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto tempParameter = ParameterDto.createParameterDto("TEMSEN", "온도");
        parameters.put(TEMPERATURE, tempParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.Temperature", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        Map<String, String> output = new HashMap<>();
        output.put(TEMP_TYPE_DATA, "27");
        when(temperatureService.sendRequestToGetTempTypeData(any(TempType.class)))
                .thenReturn(
                        SpeakerServerDto.Response.createSpeakerResponse(output)
                );

        //when //then
        mvc.perform(post("/api/speaker/answer.Temperature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("2.0"))
                .andExpect(jsonPath("$.resultCode").value("OK"))
                .andExpect(jsonPath("$.output.TEMP_TYPE_DATA").value("27"))
        ;
        verify(temperatureService).sendRequestToGetTempTypeData(any(TempType.class));
    }

    @Test
    @DisplayName("[실패][POST] ledOnOff 테스트 -  beanValidation Error")
    public void givenNullParameters_whenGetTemperature_ThenValidError() throws Exception{
        //given

        ActionDto actionDto = ActionDto.createActionDto("answer.Temperature", null);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);


        //when //then
        mvc.perform(post("/api/speaker/answer.Temperature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
        verify(temperatureService, never()).sendRequestToGetTempTypeData(any(TempType.class));
    }
}