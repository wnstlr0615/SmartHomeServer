package com.example.smarthome.error.exception;

import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ArduinoServerException extends RuntimeException{
    private final ArduinoServerErrorCode errorCode;
    private final String errorMessage;

    public ArduinoServerException(ArduinoServerErrorCode errorCode, String errorMessage) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ArduinoServerException(ArduinoServerErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
