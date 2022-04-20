package com.example.smarthome.controller;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.model.TvState;
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

import static com.example.smarthome.constant.EntityTypeConstant.TV_CHANNEL;
import static com.example.smarthome.constant.EntityTypeConstant.TV_STATE;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TvController {
    private final JsonUtils jsonUtils;

    @PostMapping("/answer.TvOnOff")
    public SpeakerServerDto.Response tvOnOff(@RequestBody String json) {
        ParameterDto parameter = jsonUtils.getParameter(json, TV_STATE);
        TvState tvState = TvState.fromValue(parameter.getValue());
        log.info("티비를 {}", tvState.getResponseMessage());

        Map<String, String> output = new HashMap<>();
        output.put("TV_RESULT", tvState.getResponseMessage());

        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

    @PostMapping("/answer.TvChannel")
    public SpeakerServerDto.Response tvChannel(@RequestBody String json) {
        ParameterDto parameter = jsonUtils.getParameter(json, TV_CHANNEL);

        log.info("{}번 틀게요", parameter.getValue());

        Map<String, String> output = new HashMap<>();

        return SpeakerServerDto.Response.createSpeakerResponse(output);
    }

}
