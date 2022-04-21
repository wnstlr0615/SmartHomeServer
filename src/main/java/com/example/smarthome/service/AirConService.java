package com.example.smarthome.service;


import com.example.smarthome.dto.arduino.ArduinoAirConChangeTempDto;
import com.example.smarthome.dto.arduino.ArduinoAirConOnOffDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.AirState;
import com.example.smarthome.utils.RestTemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static com.example.smarthome.constant.ArduinoRequestUriConstant.AIRCON_CHANGE_TEMP_PATH;
import static com.example.smarthome.constant.ArduinoRequestUriConstant.AIRCON_ONOFF_PATH;
import static com.example.smarthome.constant.EntityTypeConstant.AIRCON_RESULT;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirConService {
    private final RestTemplateUtils restTemplateUtils;

    public SpeakerServerDto.Response sendAirConOnOffRequest(AirState airState) {
        //request body 생성
        ArduinoAirConOnOffDto.Request body = createArduinoAirConOnOffDtoRequest(airState);

        // 아두이노 서버에 요청
        sendAirConOnOffRequestToArduinoServer(body);

        //response
        return createSpeakerRserverDtoResponse(
                Map.of(AIRCON_RESULT, airState.getResponseMessage())
        );
    }

    private ArduinoAirConOnOffDto.Request createArduinoAirConOnOffDtoRequest(AirState airState) {
        return ArduinoAirConOnOffDto.Request.createArduinoAriConOnOffDtoRequest(airState);
    }

    private void sendAirConOnOffRequestToArduinoServer(ArduinoAirConOnOffDto.Request body) {
        ResponseEntity<String> responseEntity = restTemplateUtils.sendPostRequest(AIRCON_ONOFF_PATH, body);
        if(responseEntity.getStatusCode().isError()) {
            log.error("아두이노 서버 에어컨 On/Off 요청 실패");
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        }
        log.info("아두이노 서버에 에어컨 ON/OFF 요청을 완료하였습니다.");
    }

    private SpeakerServerDto.Response createSpeakerRserverDtoResponse(Map<String, String > output) {
        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

    public SpeakerServerDto.Response sendChangeTempRequest(Integer temperature) {
        //request body 생성
        ArduinoAirConChangeTempDto.Request body = createArduinoAirConChangeTempDtoRequest(temperature);

        // 아두이노 서버에 요청
        sendAirConChangeTempRequestToArduinoServer(body);

        //response
        return createSpeakerRserverDtoResponse(
                Collections.EMPTY_MAP
        );
    }

    private ArduinoAirConChangeTempDto.Request createArduinoAirConChangeTempDtoRequest(Integer temperature) {
        return ArduinoAirConChangeTempDto.Request.createArduinoAirConChangeTempDtoRequest(temperature);
    }

    private void sendAirConChangeTempRequestToArduinoServer(ArduinoAirConChangeTempDto.Request body) {
        ResponseEntity<String> responseEntity = restTemplateUtils.sendPostRequest(AIRCON_CHANGE_TEMP_PATH, body);
        if(responseEntity.getStatusCode().isError()) {
            log.error("아두이노 서버 에어컨 온도 변경 요청 실패");
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        }
        log.info("아두이노 서버에 에어컨 온도 변경 요청을 완료하였습니다.");
    }
}



