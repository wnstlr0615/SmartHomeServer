package com.example.smarthome.service;

import com.example.smarthome.constant.ArduinoRequestUriConstant;
import com.example.smarthome.dto.arduino.ArduinoTvChannelDto;
import com.example.smarthome.dto.arduino.ArduinoTvOnOffDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.TvState;
import com.example.smarthome.utils.RestTemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.EntityTypeConstant.TV_RESULT;

@Slf4j
@Service
@RequiredArgsConstructor
public class TvService {
    private final RestTemplateUtils restTemplateUtils;

    public SpeakerServerDto.Response sendTvOnOffRequest(TvState tvState) {
        //requestBody 생성
        ArduinoTvOnOffDto.Request body = createArduinoTvOnOffDtoRequest(tvState);

        //아두이노 서버 요청
        sendTvOnOffRequestToArduinoServer(body);

        //response 생성
        return createArduinoTvOnOffDtoResponse(tvState);
    }

    private ArduinoTvOnOffDto.Request createArduinoTvOnOffDtoRequest(TvState tvState) {
        return ArduinoTvOnOffDto.Request.createArduinoTvOnOffDtoRequest(tvState);
    }

    private void sendTvOnOffRequestToArduinoServer(ArduinoTvOnOffDto.Request body) {
        ResponseEntity<String> responseEntity = restTemplateUtils.sendPostRequest(ArduinoRequestUriConstant.TV_ONOFF_PATH, body);
        if(responseEntity.getStatusCode().isError()) {
            log.error("아두이노 서버 TV On/Off 요청 실패");
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        }
        log.info("아두이노 서버에 TV ON/OFF 요청을 완료하였습니다.");
    }

    private SpeakerServerDto.Response createArduinoTvOnOffDtoResponse(TvState tvState) {
        Map<String, String> output = new HashMap<>();
        output.put(TV_RESULT, tvState.getResponseMessage());
        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

    public SpeakerServerDto.Response sendTurnChannelRequest(int channel) {
        //requestBody 생성
        ArduinoTvChannelDto.Request body = createArduinoTvChannelDtoRequest(channel);

        //아두이노 서버에 요청
        sendTvChannelRequestToArduinoServer(body);

        return createArduinoTvChannelDtoResponse();
    }

    private ArduinoTvChannelDto.Request createArduinoTvChannelDtoRequest(int channel) {
        return ArduinoTvChannelDto.Request.createArduinoTvChannelDtoRequest(channel);
    }

    private SpeakerServerDto.Response createArduinoTvChannelDtoResponse() {
        Map<String, String> output = new HashMap<>();
        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

    private void sendTvChannelRequestToArduinoServer(ArduinoTvChannelDto.Request body) {
        ResponseEntity<String> responseEntity = restTemplateUtils.sendPostRequest(ArduinoRequestUriConstant.TV_CHANNEL_PATH, body);
        if(responseEntity.getStatusCode().isError()) {
            log.error("아두이노 서버 TV 채널 변경 요청 실패");
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        }
        log.info("아두이노 서버에 TV 채널 변경 요청을 완료하였습니다.");
    }
}
