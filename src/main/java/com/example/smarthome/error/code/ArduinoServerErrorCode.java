package com.example.smarthome.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArduinoServerErrorCode implements ErrorCode{
    ARDUINO_SERVER_ERROR("아두이노 서버에 현재 문제가 발생하였습니다.", 500);
    private final String description;
    private final int errorCode;
}
