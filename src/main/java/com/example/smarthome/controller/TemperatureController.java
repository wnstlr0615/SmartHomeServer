package com.example.smarthome.controller;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.SpeakerServerErrorCode;
import com.example.smarthome.error.exception.SpeakerServerException;
import com.example.smarthome.model.TempType;
import com.example.smarthome.service.TemperatureService;
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

import static com.example.smarthome.constant.EntityTypeConstant.TEMPERATURE;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker", consumes = MediaType.APPLICATION_JSON_VALUE)

@RequiredArgsConstructor
public class TemperatureController {
    private final TemperatureService temperatureService;

    /** 온도/습도/체감온도 수집 요청 */
    @PostMapping("/answer.Temperature")
    public SpeakerServerDto.Response getTemperature(
            @Valid @RequestBody SpeakerServerDto.Request request,
            BindingResult error
        ){
        validatedRequestParameter(error);
        TempType tempType = TempType.fromValue(
                SpeakerServerRequestParser.getParameterValue(request, TEMPERATURE)
        );
        return temperatureService.sendRequestToGetTempTypeData(tempType);
    }
    private void validatedRequestParameter(BindingResult error) {
        if(error.hasErrors()){
            error.getFieldErrors()
                    .forEach(fieldError ->
                            log.error("field : {}, rejectValue : {}, message : {}", fieldError.getField(), fieldError.getRejectedValue(),  fieldError.getDefaultMessage())
                    );
            throw new SpeakerServerException(SpeakerServerErrorCode.BAD_REQUEST_NOT_INVALID_PARAMETER);
        }
    }
}
