package com.example.smarthome.service;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.AirState;
import com.example.smarthome.utils.RestTemplateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.example.smarthome.constant.ArduinoRequestUriConstant.AIRCON_CHANGE_TEMP_PATH;
import static com.example.smarthome.constant.ArduinoRequestUriConstant.AIRCON_ONOFF_PATH;
import static com.example.smarthome.constant.EntityTypeConstant.AIRCON_RESULT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirConServiceTest {
    @Mock
    RestTemplateUtils restTemplateUtils;

    @InjectMocks
    AirConService airConService;

    @Test
    @DisplayName("[성공] AirConOnOff 테스트")
    public void givenAirConState_whenSendAirConOnOffRequest_thenSpeakerServerDtoResponse(){
        //given
        AirState airState = AirState.TURN_ON;

        when(restTemplateUtils.sendPostRequest(eq(AIRCON_ONOFF_PATH), any()))
                .thenReturn(
                        ResponseEntity.ok().body("{\"result\" :\"ok\"}")
                );
        //when
        SpeakerServerDto.Response response = airConService.sendAirConOnOffRequest(airState);

        //then
        assertThat(response)
                .hasFieldOrPropertyWithValue("version", "2.0")
                .hasFieldOrPropertyWithValue("resultCode", "OK")
                .hasFieldOrProperty("output");

        assertThat(response.getOutput().get(AIRCON_RESULT))
                .isEqualTo(airState.getResponseMessage());

        verify(restTemplateUtils).sendPostRequest(eq(AIRCON_ONOFF_PATH), any());
    }

    @Test
    @DisplayName("[실패] AirConOnOff테스트 - 아두이노 서버 오류")
    public void givenAirConState_whenSendAirConOnOffRequestButArduinoServerError_thenArduinoServerException(){
        //given
        AirState airState = AirState.TURN_ON;

        when(restTemplateUtils.sendPostRequest(eq(AIRCON_ONOFF_PATH), any()))
                .thenReturn(
                        ResponseEntity.badRequest().body("")
                );
        //when
        ArduinoServerException exception = assertThrows(ArduinoServerException.class,
                () -> airConService.sendAirConOnOffRequest(airState)
        );

        //then
        assertThat(exception.getErrorCode()).isEqualTo(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        verify(restTemplateUtils).sendPostRequest(eq(AIRCON_ONOFF_PATH), any());
    }

    @Test
    @DisplayName("[성공] AirCon 온도 변경 테스트")
    public void givenTemperature_whenSendChangeTempRequest_thenSpeakerServerDtoResponse(){
        //given
        Integer temperature = 18;

        when(restTemplateUtils.sendPostRequest(eq(AIRCON_CHANGE_TEMP_PATH), any()))
                .thenReturn(
                        ResponseEntity.ok().body("{\"result\" :\"ok\"}")
                );
        //when
        SpeakerServerDto.Response response = airConService.sendChangeTempRequest(temperature);

        //then
        assertThat(response)
                .hasFieldOrPropertyWithValue("version", "2.0")
                .hasFieldOrPropertyWithValue("resultCode", "OK")
                .hasFieldOrProperty("output");

        verify(restTemplateUtils).sendPostRequest(eq(AIRCON_CHANGE_TEMP_PATH), any());
    }

    @Test
    @DisplayName("[실패] AirConOnOff테스트 - 아두이노 서버 오류")
    public void agivenAirConState_whenSendAirConOnOffRequestButArduinoServerError_thenArduinoServerException(){
        //given
        Integer temperature = 18;

        when(restTemplateUtils.sendPostRequest(eq(AIRCON_CHANGE_TEMP_PATH), any()))
                .thenReturn(
                        ResponseEntity.badRequest().body("")
                );
        //when
        ArduinoServerException exception = assertThrows(ArduinoServerException.class,
                () -> airConService.sendChangeTempRequest(temperature)
        );

        //then
        assertThat(exception.getErrorCode())
                .isEqualTo(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        verify(restTemplateUtils).sendPostRequest(eq(AIRCON_CHANGE_TEMP_PATH), any());
    }


}