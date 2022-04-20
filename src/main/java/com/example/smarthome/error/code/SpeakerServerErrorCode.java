package com.example.smarthome.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpeakerServerErrorCode implements ErrorCode{
    BAD_REQUEST_NOT_INVALID_PARAMETER("파라미터가 올바르지 않습니다.", 400);
    private final String description;
    private final int statusCode;
}
