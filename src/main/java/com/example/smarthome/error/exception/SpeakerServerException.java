package com.example.smarthome.error.exception;

import com.example.smarthome.error.code.ErrorCode;
import com.example.smarthome.error.code.SpeakerServerErrorCode;
import lombok.Getter;

@Getter
public class SpeakerServerException extends RuntimeException{
    private final SpeakerServerErrorCode errorCode;
    private final String errorMessage;

    public SpeakerServerException(SpeakerServerErrorCode errorCode, String errorMessage) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public SpeakerServerException(SpeakerServerErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
