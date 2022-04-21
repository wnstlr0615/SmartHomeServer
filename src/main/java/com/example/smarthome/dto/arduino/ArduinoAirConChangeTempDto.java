package com.example.smarthome.dto.arduino;

import com.example.smarthome.model.AirState;
import lombok.*;
import org.springframework.lang.NonNull;

public class ArduinoAirConChangeTempDto {
    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        private Integer temperature;
        public static ArduinoAirConChangeTempDto.Request createArduinoAirConChangeTempDtoRequest(@NonNull Integer temperature){
            return Request.builder()
                    .temperature(temperature)
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
        public static ArduinoAirConChangeTempDto.Response createArduinoAirConChangeTempDtoResponse(@NonNull String result, @NonNull String message){
            return Response.builder()
                    .result(result)
                    .message(message)
                    .build();

        }
    }
}
