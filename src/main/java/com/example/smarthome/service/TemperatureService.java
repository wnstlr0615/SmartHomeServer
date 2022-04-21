package com.example.smarthome.service;

import com.example.smarthome.dto.arduino.ArduinoBringTempTypeDataDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.model.TempType;
import com.example.smarthome.utils.JsonUtils;
import com.example.smarthome.utils.RestTemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.ArduinoRequestUriConstant.BRING_TEMP_TYPE_DATA_PATH;
import static com.example.smarthome.constant.EntityTypeConstant.TEMP_TYPE_DATA;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureService {
    private final RestTemplateUtils restTemplateUtils;
    private final JsonUtils jsonUtils;

    /** 온도/습도/체감온도 수집 요청 */
    public SpeakerServerDto.Response sendRequestToGetTempTypeData(TempType tempType) {
        //requestBody 생성
        ArduinoBringTempTypeDataDto.Request body = createArduinoBringTempTypeDataDtoRequest(tempType);
        
        //아두이노 서버에 요청
        Integer tempTypeData = sendTemperatureRequestToArduinoServer(body);

        //response 생성
        return createSpeakerServerDtoResponse(tempTypeData);
    }

    private ArduinoBringTempTypeDataDto.Request createArduinoBringTempTypeDataDtoRequest(TempType tempType) {
        return ArduinoBringTempTypeDataDto.Request.createArduinoBringTempTypeDataDtoRequest(tempType);
    }

    private Integer sendTemperatureRequestToArduinoServer(ArduinoBringTempTypeDataDto.Request body) {
        ResponseEntity<String> responseEntity = restTemplateUtils.sendPostRequest(BRING_TEMP_TYPE_DATA_PATH, body);
        if(responseEntity.getStatusCode().isError()) {
            log.error("아두이노 서버에 온도 수집 요청 실패");
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_ERROR);
        }
        log.info("아두이노 서버에 온도 수집 요청을 완료하였습니다.");
        ArduinoBringTempTypeDataDto.Response response = jsonUtils.fromJson(responseEntity.getBody(), ArduinoBringTempTypeDataDto.Response.class);
        log.info("responseData : {}", response.getData());
        return response.getData();
    }

    private SpeakerServerDto.Response createSpeakerServerDtoResponse(Integer tempTypeData) {
        Map<String, String> output = new HashMap<>();
        output.put(TEMP_TYPE_DATA, String.valueOf(tempTypeData));
        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }
}
