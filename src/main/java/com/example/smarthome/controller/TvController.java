package com.example.smarthome.controller;

import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.SpeakerServerErrorCode;
import com.example.smarthome.error.exception.SpeakerServerException;
import com.example.smarthome.model.TvState;
import com.example.smarthome.service.TvService;
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

import static com.example.smarthome.constant.EntityTypeConstant.TV_CHANNEL;
import static com.example.smarthome.constant.EntityTypeConstant.TV_STATE;

@Slf4j
@RestController
@RequestMapping(value = "/api/speaker", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TvController {
    private final TvService tvService;

    @PostMapping("/answer.TvOnOff")
    public SpeakerServerDto.Response tvOnOff(
            @Valid @RequestBody SpeakerServerDto.Request request,
            BindingResult error
        ) {
        validatedRequestParameter(error);
        TvState tvState = TvState.fromValue(
                SpeakerServerRequestParser.getParameterValue(request, TV_STATE)
        );
        return tvService.sendTvOnOffRequest(tvState);
    }

    @PostMapping("/answer.TvChannel")
    public SpeakerServerDto.Response tvChannel(
            @Valid @RequestBody SpeakerServerDto.Request request,
            BindingResult error
    ) {
        validatedRequestParameter(error);
        int channel = getChannel(
                SpeakerServerRequestParser.getParameterValue(request, TV_CHANNEL)
        );

        return tvService.sendTurnChannelRequest(channel);
    }

    private int getChannel(String parameterValue) {
        int channel;
        try {
            channel = Integer.parseInt(parameterValue);
        } catch (NumberFormatException e) {
            throw new SpeakerServerException(SpeakerServerErrorCode.BAD_REQUEST_NOT_INVALID_PARAMETER);
        }
        if(channel < 0 || channel >= 1000){
            throw new SpeakerServerException(SpeakerServerErrorCode.BAD_REQUEST_NOT_INVALID_PARAMETER, "채널 정보가 올바르지 않습니다.");
        }
        return channel;
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
