package com.example.smarthome.utils;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.dto.speker.SpeakerServerDto;
import com.example.smarthome.error.code.SpeakerServerErrorCode;
import com.example.smarthome.error.exception.SpeakerServerException;
import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

import java.util.Map;

@UtilityClass
public class SpeakerServerRequestParser {
    public static String getParameterValue(@NonNull SpeakerServerDto.Request request, @NonNull String name){
        Map<String, ParameterDto> parameters = request.getAction().getParameters();
        if(!parameters.containsKey(name)){
            throw new SpeakerServerException(SpeakerServerErrorCode.BAD_REQUEST_NOT_INVALID_PARAMETER);
        }
        return parameters.get(name).getValue();
    }

}
