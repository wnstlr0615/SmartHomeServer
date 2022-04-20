package com.example.smarthome.service;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.utils.RestTemplateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.example.smarthome.constant.ArduinoRequestUriConstant.LED_ONOFF_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LEDServiceTest {
    @Mock
    RestTemplateUtils restTemplateUtils;
    @InjectMocks
    LEDService ledService;

    @Test
    @DisplayName("[성공] sendRequest 테스트")
    public void givenRoomTypeAndLightState_whenSendRequest_thenReturnSpeakerServerDtoResponse(){
        //given
        RoomType roomType = RoomType.MY_ROOM;
        LightState lightState = LightState.TURN_ON;

        when(restTemplateUtils.sendPostRequest(eq(LED_ONOFF_PATH), any()))
                .thenReturn(ResponseEntity.ok().body("{\"result\" :\"ok\"}"));

        //when
        SpeakerServerDto.Response response = ledService.sendRequest(roomType, lightState);

        assertThat(response)
                .hasFieldOrPropertyWithValue("version", "2.0")
                .hasFieldOrPropertyWithValue("resultCode", "OK")
                .hasFieldOrProperty("output");
        //then
        verify(restTemplateUtils).sendPostRequest(eq(LED_ONOFF_PATH), any());
    }
    @Test
    @DisplayName("[실패] sendRequest 테스트 - ArduinoServerException")
    public void givenRoomTypeAndLightState_whenSendRequest_thenArduinoServerException(){
        //given
        ArduinoServerErrorCode errorCode = ArduinoServerErrorCode.ARDUINO_SERVER_ERROR;
        RoomType roomType = RoomType.MY_ROOM;
        LightState lightState = LightState.TURN_ON;

        when(restTemplateUtils.sendPostRequest(eq(LED_ONOFF_PATH), any()))
                .thenThrow(new ArduinoServerException(errorCode))
        ;
        //when
        final ArduinoServerException exception = assertThrows(ArduinoServerException.class,
            () ->     ledService.sendRequest(roomType, lightState)
        );

        //then
        assertAll(
            () -> assertThat(exception.getErrorCode()).isEqualTo(errorCode),
            () -> assertThat(exception.getErrorCode().getDescription()).isEqualTo(errorCode.getDescription())
        );

        verify(restTemplateUtils).sendPostRequest(eq(LED_ONOFF_PATH), any());
    }
}