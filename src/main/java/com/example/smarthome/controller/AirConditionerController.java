package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.dto.speker.SpeakerServerDto.Response;
import com.example.smarthome.model.AirState;
import com.example.smarthome.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.EntityTypeConstant.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AirConditionerController {
    private final JsonUtils jsonUtils;

    @PostMapping("/answer.AirConditioner")
    public SpeakerServerDto.Response getTemperature(@RequestBody String json){
        ParameterDto parameter = jsonUtils.getParameter(json, AIR_STATE);
        AirState stateType = AirState.fromValue(parameter.getValue());

        log.info("아두이노 서버에 에어컨 온/오프 요청");
        log.info("잠시 후 에어컨이 {}", stateType.getResponseMessage());

        //response
        Map<String, String> output = new HashMap<>();

        output.put("result", stateType.getResponseMessage());
        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

    @PostMapping("/answer.AirConTemp")
    public SpeakerServerDto.Response setUpAirConTemp(@RequestBody String json){
        System.out.println(json);
        ParameterDto parameter = jsonUtils.getParameter(json, AIRCON_TEMP);
        //TODO 온도 범위 설정 필요
        log.info("type : {}, value : {}", parameter.getType(), parameter.getValue());

        int setTemp = getAirConTemp(parameter.getValue());
        log.info("아두이노 서버에 에어컨 온도 설정 요청");
        log.info("에어컨 온드를  {}로 설정 합니다.", setTemp);

        //response
        Map<String, String> output = new HashMap<>();
        output.put(AIRCON_TEMP, String.valueOf(setTemp));

        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

    private int getAirConTemp(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.error("Integer 변환 실패");
            throw new RuntimeException(e);
        }
    }
}
