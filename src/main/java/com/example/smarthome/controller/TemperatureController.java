package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.SpeakerResponse;
import com.example.smarthome.model.TempType;
import com.example.smarthome.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.EntityTypeConstant.TEMPERATURE;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker")
@RequiredArgsConstructor
public class TemperatureController {
    private final JsonUtils jsonUtils;

    @PostMapping("/answer.Temperature")
    public SpeakerResponse getTemperature(@RequestBody String json){
        ParameterDto parameter = jsonUtils.getParameter(json, TEMPERATURE);
        TempType tempType = TempType.fromValue(parameter.getValue());

        log.info("아두이노 서버에 온도 요청");
        log.info("Temperature : {}", tempType.getDescription());

        String tmep = "40도";
        //response
        Map<String, String> output = new HashMap();

        output.put("temSenState", tmep);
        output.put("TEMPERATURE", parameter.getValue());


        return SpeakerResponse.createSpeakerResponse(output);
    }
}
