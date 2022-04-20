package com.example.smarthome.controller;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.SpeakerServerErrorCode;
import com.example.smarthome.error.exception.SpeakerServerException;
import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.service.LEDService;
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

import static com.example.smarthome.constant.EntityTypeConstant.LIGHT_STATE;
import static com.example.smarthome.constant.EntityTypeConstant.ROOM_TYPE;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LEDController {
    private final LEDService ledService;

    @PostMapping("/answer.led")
    public SpeakerServerDto.Response ledOnOff(
            @Valid @RequestBody SpeakerServerDto.Request request,
            BindingResult error
    ){
        validatedRequestParameter(error);
        RoomType roomType = RoomType.fromValue(
                SpeakerServerRequestParser.getParameterValue(request, ROOM_TYPE)
        );
        LightState stateType = LightState.fromValue(
                SpeakerServerRequestParser.getParameterValue(request, LIGHT_STATE)
        );
        log.info("잠시후 {}이 {}", roomType.getResponseMessage(), stateType.getResponseMessage());
        return ledService.sendRequest(roomType, stateType);
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
