package com.example.smarthome.controller;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.exception.SpeakerServerException;
import com.example.smarthome.model.AirState;
import com.example.smarthome.service.AirConService;
import com.example.smarthome.utils.SpeakerServerRequestParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.smarthome.constant.EntityTypeConstant.AIRCON_TEMP;
import static com.example.smarthome.constant.EntityTypeConstant.AIR_STATE;
import static com.example.smarthome.error.code.SpeakerServerErrorCode.BAD_REQUEST_NOT_INVALID_PARAMETER;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AirConditionerController {
    private final AirConService airConService;

    @PostMapping("/answer.AirConditioner")
    public SpeakerServerDto.Response turnOnOffAirCon(
            @Valid @RequestBody SpeakerServerDto.Request request,
            BindingResult error
    ){
        validatedRequestParameter(error);
        AirState airState = AirState.fromValue(
                SpeakerServerRequestParser.getParameterValue(request, AIR_STATE)
        );
        return airConService.sendAirConOnOffRequest(airState);
    }

    @PostMapping("/answer.AirConTemp")
    public SpeakerServerDto.Response changeAirConTemp(
            @Valid @RequestBody SpeakerServerDto.Request request,
            BindingResult error
    ){
        validatedRequestParameter(error);
        Integer temperature = getTemperature(
                SpeakerServerRequestParser.getParameterValue(request, AIRCON_TEMP)
        );
        return airConService.sendChangeTempRequest(temperature);
    }

    private Integer getTemperature(String strTemp) {
        int temp;
        try {
            temp = (Integer.parseInt(strTemp));
        } catch (NumberFormatException e) {
            log.error("Integer parsing error - rejectValue : {}", strTemp);
            throw new SpeakerServerException(BAD_REQUEST_NOT_INVALID_PARAMETER);
        }
        if(temp < 18 || temp > 28 ){
            throw new SpeakerServerException(BAD_REQUEST_NOT_INVALID_PARAMETER, "에어컨 설정 온도가 18 ~ 28도 범위를 벗어 납니다.");
        }
        return temp;
    }

    private void validatedRequestParameter(BindingResult error) {
        if(error.hasErrors()){
            error.getFieldErrors()
                    .forEach(fieldError ->
                            log.error("field : {}, rejectValue : {}, message : {}", fieldError.getField(), fieldError.getRejectedValue(),  fieldError.getDefaultMessage())
                    );
            throw new SpeakerServerException(BAD_REQUEST_NOT_INVALID_PARAMETER);
        }
    }
}
