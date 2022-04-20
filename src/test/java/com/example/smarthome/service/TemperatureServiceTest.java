package com.example.smarthome.service;

import com.example.smarthome.dto.arduino.ArduinoBringTempTypeDataDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.TempType;
import com.example.smarthome.utils.JsonUtils;
import com.example.smarthome.utils.RestTemplateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.example.smarthome.constant.ArduinoRequestUriConstant.BRING_TEMP_TYPE_DATA_PATH;
import static com.example.smarthome.constant.EntityTypeConstant.TEMP_TYPE_DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceTest {
    ObjectMapper mapper = new ObjectMapper();
    JSONParser jsonParser = new JSONParser();
    @Spy
    JsonUtils jsonUtils = new JsonUtils(jsonParser, mapper);
    @Mock
    RestTemplateUtils restTemplateUtils;
    @InjectMocks
    TemperatureService temperatureService;

    @Test
    @DisplayName("[성공] sendRequest 테스트")
    public void givenTempType_whenGetTemperature_ThenReturnSpeakerResponseDto() throws JsonProcessingException {
        //given
        TempType tempType = TempType.TEMPERATURE;

        when(restTemplateUtils.sendPostRequest(eq(BRING_TEMP_TYPE_DATA_PATH), any()))
                .thenReturn(
                        ResponseEntity.ok(
                                mapper.writeValueAsString(
                                        createArduinoBringTempTypeDataDtoResponse(27)
                                )
                        )
                );

        //when
        SpeakerServerDto.Response response = temperatureService.sendRequestToGetTempTypeData(tempType);

        assertThat(response)
                .hasFieldOrPropertyWithValue("version", "2.0")
                .hasFieldOrPropertyWithValue("resultCode", "OK");
        assertThat(response.getOutput().get(TEMP_TYPE_DATA)).isEqualTo("27");

        //then
        verify(restTemplateUtils).sendPostRequest(eq(BRING_TEMP_TYPE_DATA_PATH), any());
    }

    private ArduinoBringTempTypeDataDto.Response createArduinoBringTempTypeDataDtoResponse(int data) {
        return ArduinoBringTempTypeDataDto.Response.createArduinoBringTempTypeDataDtoResponse("ok", "성공적으로 요청을 완료하였습니다.", data);
    }

    @Test
    @DisplayName("[실패] GetTmperature 테스트 - ArduinoServerException")
    public void givenTempType_whenGetTemperatureButArduinoServerDie_ThenArduinoServerException(){
        //given
        TempType tempType = TempType.TEMPERATURE;
        final ArduinoServerErrorCode errorCode = ArduinoServerErrorCode.ARDUINO_SERVER_ERROR;
        when(restTemplateUtils.sendPostRequest(eq(BRING_TEMP_TYPE_DATA_PATH), any()))
                .thenThrow(new ArduinoServerException(errorCode));

        //when
        final ArduinoServerException exception = assertThrows(ArduinoServerException.class,
            () -> temperatureService.sendRequestToGetTempTypeData(tempType)
        );
        //then
        assertAll(
            () -> assertThat(exception.getErrorCode()).isEqualTo(errorCode),
            () -> assertThat(exception.getErrorCode().getDescription()).isEqualTo(errorCode.getDescription())
        );
        verify(restTemplateUtils).sendPostRequest(eq(BRING_TEMP_TYPE_DATA_PATH), any());
    }
}