package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.ActionDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.model.TempType;
import com.example.smarthome.model.TvState;
import com.example.smarthome.service.TvService;
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

@WebMvcTest(controllers = TvController.class)
class TvControllerTest {
    @MockBean
    TvService tvService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("[성공][POST] TvOnOff테스트")
    public void givenTvState_whenTvTurnOnOff_thenReturnSpeakerResponseDto() throws Exception{
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto tvStateParameter = ParameterDto.createParameterDto("OnOFF_STATE", "켜줘");
        parameters.put(TV_STATE, tvStateParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.TvOnOff", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        Map<String, String> output = new HashMap<>();
        output.put(TV_RESULT, "켤게요.");
        when(tvService.sendTvOnOffRequest(any(TvState.class)))
                .thenReturn(
                        SpeakerServerDto.Response.createSpeakerResponse(output)
                );
        //when //then
        mvc.perform(post("/api/speaker/answer.TvOnOff")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                    mapper.writeValueAsBytes(request)
            )
        )
            .andDo(print())
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("2.0"))
                .andExpect(jsonPath("$.resultCode").value("OK"))
                .andExpect(jsonPath("$.output.TV_RESULT").value("켤게요."))
        ;
        verify(tvService).sendTvOnOffRequest(any(TvState.class));
    }

    @Test
    @DisplayName("[성공][POST] TvChannel 테스트")
    public void givenTvChannel_whenTvChannel_thenReturnSpeakerResponseDto() throws Exception{
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto tvChannelParameter = ParameterDto.createParameterDto("BID_QT", "18");
        parameters.put(TV_CHANNEL, tvChannelParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.TvChannel", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        Map<String, String> output = new HashMap<>();
        when(tvService.sendTurnChannelRequest(anyInt()))
                .thenReturn(
                        SpeakerServerDto.Response.createSpeakerResponse(output)
                );
        //when //then
        mvc.perform(post("/api/speaker/answer.TvChannel")
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
        verify(tvService).sendTurnChannelRequest(anyInt());
    }

    @Test
    @DisplayName("[실패][POST] TvChannel 테스트 - NumberFormatException 발생 ")
    public void givenNotParserIntTvChannel_whenTvChannel_thenSpeakerServerError() throws Exception{
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto tvChannelParameter = ParameterDto.createParameterDto("BID_QT", "18번");
        parameters.put(TV_CHANNEL, tvChannelParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.TvChannel", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        //when //then
        mvc.perform(post("/api/speaker/answer.TvChannel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
        verify(tvService, never()).sendTurnChannelRequest(anyInt());
    }

    @Test
    @DisplayName("[실패][POST] TvChannel 테스트 - 채널 값이 0 ~ 1000을 넘어 가는 경우")
    public void givenNotValidTvChannel_whenTvChannel_thenSpeakerServerError() throws Exception{
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto tvChannelParameter = ParameterDto.createParameterDto("BID_QT", "1001");
        parameters.put(TV_CHANNEL, tvChannelParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.TvChannel", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        //when //then
        mvc.perform(post("/api/speaker/answer.TvChannel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsBytes(request)
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
        verify(tvService, never()).sendTurnChannelRequest(anyInt());
    }
}