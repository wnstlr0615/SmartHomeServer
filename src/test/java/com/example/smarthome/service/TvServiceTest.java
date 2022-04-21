package com.example.smarthome.service;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.model.TvState;
import com.example.smarthome.utils.RestTemplateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.example.smarthome.constant.ArduinoRequestUriConstant.*;
import static com.example.smarthome.constant.EntityTypeConstant.TV_CHANNEL;
import static com.example.smarthome.constant.EntityTypeConstant.TV_RESULT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TvServiceTest {
    @Mock
    RestTemplateUtils restTemplateUtils;
    @InjectMocks
    TvService tvService;

    @Test
    @DisplayName("[성공] tvOnOff 테스트 ")
    public void givenTvState_whenTvOnOff_thenSpeakerServerDtoResponse(){
        //given
        TvState tvState = TvState.TURN_OFF;

        when(restTemplateUtils.sendPostRequest(eq(TV_ONOFF_PATH), any()))
                .thenReturn(ResponseEntity.ok().body("{\"result\" :\"ok\"}"));

        //when
        SpeakerServerDto.Response response = tvService.sendTvOnOffRequest(tvState);

        //then
        assertThat(response)
                .hasFieldOrPropertyWithValue("version", "2.0")
                .hasFieldOrPropertyWithValue("resultCode", "OK")
                .hasFieldOrProperty("output");
        assertThat(response.getOutput().get(TV_RESULT)).isEqualTo(tvState.getResponseMessage());

        verify(restTemplateUtils).sendPostRequest(eq(TV_ONOFF_PATH), any());
    }

    @Test
    @DisplayName("[실패] tvOnOff 테스트 - ArduinoServerException")
    public void givenTvState_whenTvOnOff_thenArduinoServerException(){
        //given
        ArduinoServerErrorCode errorCode = ArduinoServerErrorCode.ARDUINO_SERVER_ERROR;
        TvState tvState = TvState.TURN_OFF;


        when(restTemplateUtils.sendPostRequest(eq(TV_ONOFF_PATH), any()))
                .thenThrow(new ArduinoServerException(errorCode));
        //when
        final ArduinoServerException exception = assertThrows(ArduinoServerException.class,
                () ->     tvService.sendTvOnOffRequest(tvState)
        );

        //then
        assertAll(
                () -> assertThat(exception.getErrorCode()).isEqualTo(errorCode),
                () -> assertThat(exception.getErrorCode().getDescription()).isEqualTo(errorCode.getDescription())
        );

        verify(restTemplateUtils).sendPostRequest(eq(TV_ONOFF_PATH), any());
    }

    @Test
    @DisplayName("[성공] tvChannel 테스트 ")
    public void givenTvChannel_whenTvChannel_thenSpeakerServerDtoResponse(){
        //given
        int channel = 18;
        when(restTemplateUtils.sendPostRequest(eq(TV_CHANNEL_PATH), any()))
                .thenReturn(ResponseEntity.ok().body("{\"result\" :\"ok\"}"));

        //when
        SpeakerServerDto.Response response = tvService.sendTurnChannelRequest(channel);

        //then
        assertThat(response)
                .hasFieldOrPropertyWithValue("version", "2.0")
                .hasFieldOrPropertyWithValue("resultCode", "OK")
                .hasFieldOrProperty("output");

        verify(restTemplateUtils).sendPostRequest(eq(TV_CHANNEL_PATH), any());
    }

    @Test
    @DisplayName("[실패] tvChannel 테스트 - ArduinoServerException")
    public void givenTvChannel_whenTvChannel_thenArduinoServerException(){
        //given
        ArduinoServerErrorCode errorCode = ArduinoServerErrorCode.ARDUINO_SERVER_ERROR;
        int channel = 18;


        when(restTemplateUtils.sendPostRequest(eq(TV_CHANNEL_PATH), any()))
                .thenThrow(new ArduinoServerException(errorCode));
        //when
        final ArduinoServerException exception = assertThrows(ArduinoServerException.class,
                () ->     tvService.sendTurnChannelRequest(channel)
        );

        //then
        assertAll(
                () -> assertThat(exception.getErrorCode()).isEqualTo(errorCode),
                () -> assertThat(exception.getErrorCode().getDescription()).isEqualTo(errorCode.getDescription())
        );

        verify(restTemplateUtils).sendPostRequest(eq(TV_CHANNEL_PATH), any());
    }
}