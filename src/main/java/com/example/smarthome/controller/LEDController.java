package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.SpeakerResponse;
import com.example.smarthome.model.LightStateType;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.service.LEDService;
import com.example.smarthome.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.example.smarthome.constant.EntityTypeConstant.LIGHT_STATE;
import static com.example.smarthome.constant.EntityTypeConstant.ROOM_TYPE;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker")
@RequiredArgsConstructor
public class LEDController {
    private final LEDService ledService;
    private final ObjectMapper mapper;
    private final JsonUtils jsonUtils;
    @PostMapping("/answer.led")
    public SpeakerResponse ledOn(@RequestBody String json){
        ParameterDto roomParameter = jsonUtils.getParameter(json, ROOM_TYPE);
        ParameterDto lightStateParameter = jsonUtils.getParameter(json, LIGHT_STATE);

        RoomType roomType = RoomType.fromValue(roomParameter.getValue());
        LightStateType stateType = LightStateType.fromValue(lightStateParameter.getValue());

        //send Arduino server request
        log.info("아두이노 서버에 요청(임시)");
        log.info("잠시 후 {}이 {}", roomType.getResponseMessage(), stateType.getResponseMessage());

        //response
        Map<String, String> output = new HashMap();
        output.put(ROOM_TYPE, roomType.getResponseMessage());
        output.put(LIGHT_STATE, stateType.getResponseMessage());


        return SpeakerResponse.createSpeakerResponse(output);
    }
}
