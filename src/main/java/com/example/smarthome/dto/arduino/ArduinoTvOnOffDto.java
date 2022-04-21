package com.example.smarthome.dto.arduino;

import com.example.smarthome.model.TvState;
import lombok.*;
import org.springframework.lang.NonNull;

public class ArduinoTvOnOffDto {
    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        private TvState tvState;
        public static ArduinoTvOnOffDto.Request createArduinoTvOnOffDtoRequest(@NonNull TvState tvState){
            return Request.builder()
                    .tvState(tvState)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Response{
        private String result;
        private String message;
        public static ArduinoTvOnOffDto.Response createArduinoTvOnOffDtoResponse(@NonNull String result, @NonNull String message){
            return Response.builder()
                    .result(result)
                    .message(message)
                    .build();

        }
    }
}
