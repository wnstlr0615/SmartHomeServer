package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.ActionDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.model.AirState;
import com.example.smarthome.service.AirConService;
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

import static com.example.smarthome.constant.EntityTypeConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AirConditionerController.class)

class AirConditionerControllerTest {
    @MockBean
    AirConService airConService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("[성공][POST] AirCon OnOff 테스트")
    public void givenAirState_whenTurnOnOffAirCon_thenSpeakerServerDtoResponse() throws Exception {
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto ariConParameter = ParameterDto.createParameterDto("AIRCONDITION", "켜줘");
        parameters.put(AIR_STATE, ariConParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.AirConditioner", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        Map<String, String> output = new HashMap<>();
        output.put(AIRCON_RESULT, "켤게요.");
        when(airConService.sendAirConOnOffRequest(any(AirState.class)))
                .thenReturn(
                        SpeakerServerDto.Response.createSpeakerResponse(output)
                );

        //when //then
        mvc.perform(post("/api/speaker/answer.AirConditioner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                    mapper.writeValueAsBytes(request)
            )
        )
            .andDo(print())
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("2.0"))
                .andExpect(jsonPath("$.resultCode").value("OK"))
                .andExpect(jsonPath("$.output.AIRCON_RESULT").value("켤게요."))
        ;
        verify(airConService).sendAirConOnOffRequest(any(AirState.class));
    }

    @Test
    @DisplayName("[성공][POST] AirCon 온도 조절 테스트")
    public void givenTemp_whenChangeAirConTemp_thenSpeakerServerDtoResponse() throws Exception {
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto ariConParameter = ParameterDto.createParameterDto("BID_QT", "18");
        parameters.put(AIRCON_TEMP, ariConParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.AirConTemp", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        Map<String, String> output = new HashMap<>();
        when(airConService.sendChangeTempRequest(anyInt()))
                .thenReturn(
                        SpeakerServerDto.Response.createSpeakerResponse(output)
                );

        //when //then
        mvc.perform(post("/api/speaker/answer.AirConTemp")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                    mapper.writeValueAsBytes(request)
            )
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.resultCode").value("OK"))
        ;
        verify(airConService).sendChangeTempRequest(anyInt());
    }

    @Test
    @DisplayName("[실패][POST] AirCon 온도 조절 테스트 - Integer 파싱 실패")
    public void givenCanNotParsingTemp_whenChangeAirConTemp_thenArduinoSpeakerException() throws Exception {
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto ariConParameter = ParameterDto.createParameterDto("BID_QT", "18도");
        parameters.put(AIRCON_TEMP, ariConParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.AirConTemp", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        //when //then
        mvc.perform(post("/api/speaker/answer.AirConTemp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
        verify(airConService, never()).sendChangeTempRequest(anyInt());
    }

    @Test
    @DisplayName("[실패][POST] AirCon 온도 조절 테스트 - 온도 범위 18 ~ 28도를 넘어서는 경우 ")
    public void givenInvalidTemp_whenChangeAirConTemp_thenArduinoSpeakerException() throws Exception {
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto ariConParameter = ParameterDto.createParameterDto("BID_QT", "17");
        parameters.put(AIRCON_TEMP, ariConParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.AirConTemp", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        //when //then
        mvc.perform(post("/api/speaker/answer.AirConTemp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
        verify(airConService, never()).sendChangeTempRequest(anyInt());
    }
}