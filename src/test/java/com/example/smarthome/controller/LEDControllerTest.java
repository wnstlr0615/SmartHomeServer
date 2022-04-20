package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.ActionDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.service.LEDService;
import com.example.smarthome.utils.JsonUtils;
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

import static com.example.smarthome.constant.EntityTypeConstant.LIGHT_STATE;
import static com.example.smarthome.constant.EntityTypeConstant.ROOM_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LEDController.class)
class LEDControllerTest {
    @MockBean
    JsonUtils jsonUtils;
    @MockBean
    LEDService ledService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("[성공][POST] ledOnOff 테스트")
    public void givenRoomTypeAndLightState_whenLedOnOff_ThenReturnSpeakerResponseDto() throws Exception{
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto roomTypeParameter = ParameterDto.createParameterDto("ROOMTYPE", "거실등");
        ParameterDto lightStateParameter = ParameterDto.createParameterDto("LIGHT_STATE", "켜줘");
        parameters.put("ROOM_TYPE", roomTypeParameter);
        parameters.put("LIGHT_STATE", lightStateParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.led", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        Map<String, String> output = new HashMap<>();
        output.put(ROOM_TYPE, "거실등");
        output.put(LIGHT_STATE, "켜집니다.");
        when(ledService.sendRequest(any(RoomType.class), any(LightState.class)))
                .thenReturn(
                        SpeakerServerDto.Response.createSpeakerResponse(output)
                );

        //when //then
        mvc.perform(post("/api/speaker/answer.led")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                    mapper.writeValueAsBytes(request)
            )
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.resultCode").value("OK"))
            .andExpect(jsonPath("$.output.ROOM_TYPE").value("거실등"))
            .andExpect(jsonPath("$.output.LIGHT_STATE").value("켜집니다."))
    ;
        verify(ledService).sendRequest(any(RoomType.class), any(LightState.class));
    }

    @Test
    @DisplayName("[실패][POST] ledOnOff 테스트 - beanValidation Error")
    public void givenNullParameters_whenLedOnOff_ThenValidError() throws Exception{
        //given
        ActionDto actionDto = ActionDto.createActionDto("answer.led", null);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        //when //then
        mvc.perform(post("/api/speaker/answer.led")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

}