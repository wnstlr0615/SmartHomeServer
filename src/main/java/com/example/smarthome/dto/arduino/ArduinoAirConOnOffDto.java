package com.example.smarthome.dto.arduino;

import com.example.smarthome.model.AirState;
import lombok.*;
import org.springframework.lang.NonNull;

public class ArduinoAirConOnOffDto {
    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        private AirState airState;
        public static ArduinoAirConOnOffDto.Request createArduinoAriConOnOffDtoRequest(@NonNull AirState airState){
            return Request.builder()
                    .airState(airState)
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
        public static ArduinoAirConOnOffDto.Response createArduinoAriConOnOffDtoResponse(@NonNull String result, @NonNull String message){
            return Response.builder()
                    .result(result)
                    .message(message)
                    .build();

        }
    }
}
