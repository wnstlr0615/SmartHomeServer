package com.example.smarthome.service;

import com.example.smarthome.constant.ArduinoRequestUriConstant;
import com.example.smarthome.dto.arduino.ArduinoLEDOnOffDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.utils.RestTemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.EntityTypeConstant.LIGHT_STATE;
import static com.example.smarthome.constant.EntityTypeConstant.ROOM_TYPE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LEDService {
    private final RestTemplateUtils restTemplateUtils;

    /** LED OnOff 요청 */
    public SpeakerServerDto.Response sendRequest(RoomType roomType, LightState stateType) {
        //body 생성
        ArduinoLEDOnOffDto.Request body = createArduinoLedOnOffDtoRequest(roomType, stateType);
        
        //아두이노 서버에 요청
        sendLedOnOffRequestToArduinoServer(body);

        //response 생성
        return createSpeakerServerDtoResponse(roomType, stateType);
    }

    private ArduinoLEDOnOffDto.Request createArduinoLedOnOffDtoRequest(RoomType roomType, LightState stateType) {
        return ArduinoLEDOnOffDto.Request.createArduinoLEDOnOffDtoRequest(roomType, stateType);
    }

    private void sendLedOnOffRequestToArduinoServer(ArduinoLEDOnOffDto.Request body) {
        ResponseEntity<String> responseEntity = restTemplateUtils.sendPostRequest(ArduinoRequestUriConstant.LED_ONOFF_PATH, body);
        if(responseEntity.getStatusCode().isError()) {
            log.error("아두이노 서버 LED On/Off 요청 실패");
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        }
        log.info("아두이노 서버에 LED ON/OFF 요청을 완료하였습니다.");
    }

    private SpeakerServerDto.Response createSpeakerServerDtoResponse(RoomType roomType, LightState stateType) {
        Map<String, String> output = new HashMap<>();
        output.put(ROOM_TYPE, roomType.getResponseMessage());
        output.put(LIGHT_STATE, stateType.getResponseMessage());
        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }
}
