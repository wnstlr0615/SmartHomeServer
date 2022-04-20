package com.example.smarthome.utils;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.ActionDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.SpeakerServerErrorCode;
import com.example.smarthome.error.exception.SpeakerServerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.example.smarthome.constant.EntityTypeConstant.LIGHT_STATE;
import static com.example.smarthome.constant.EntityTypeConstant.ROOM_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SpeakerServerRequestParserTest {
    @Test
    @DisplayName("[성공] SpeakerServerParser 테스트")
    public void givenSpeakerServerRequest_whenGetParametersValue_thenValueIsCorrect() {
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto roomTypeParameter = ParameterDto.createParameterDto("ROOMTYPE", "거실등");
        ParameterDto lightStateParameter = ParameterDto.createParameterDto("LIGHT_STATE", "켜줘");
        parameters.put(ROOM_TYPE, roomTypeParameter);
        parameters.put(LIGHT_STATE, lightStateParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.led", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);

        //when
        String roomTypeValue = SpeakerServerRequestParser.getParameterValue(request, ROOM_TYPE);
        String lightStateValue = SpeakerServerRequestParser.getParameterValue(request, LIGHT_STATE);

        //then
        assertAll(
                () -> assertThat(roomTypeValue).isEqualTo("거실등"),
                () -> assertThat(lightStateValue).isEqualTo("켜줘")
            );
    }

    @Test
    @DisplayName("[실패] SpeakerServerParser 테스트 - BAD_REQUEST_NOT_INVALID_PARAMETER")
    public void givenWrongParameterName_whenGetParametersValue_thenSpeakerServerException() {
        //given
        HashMap<String, ParameterDto> parameters = new HashMap<>();
        ParameterDto roomTypeParameter = ParameterDto.createParameterDto("ROOMTYPE", "거실등");
        parameters.put(ROOM_TYPE, roomTypeParameter);
        ActionDto actionDto = ActionDto.createActionDto("answer.led", parameters);
        SpeakerServerDto.Request request = SpeakerServerDto.Request.createSpeakerServerRequest(actionDto, null);
        SpeakerServerErrorCode errorCode = SpeakerServerErrorCode.BAD_REQUEST_NOT_INVALID_PARAMETER;
        //when
        String wrongParameterName = "ROOMTYPE";

        //then
        SpeakerServerException speakerServerException = assertThrows(SpeakerServerException.class,
                () -> SpeakerServerRequestParser.getParameterValue(request, wrongParameterName)
        );
        assertThat(speakerServerException.getErrorCode()).isEqualTo(errorCode);
    }
}