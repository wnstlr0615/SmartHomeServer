package com.example.smarthome.dto.arduino;

import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import lombok.*;
import org.springframework.lang.NonNull;


public class ArduinoLEDOnOffDto {
    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        private RoomType roomType;
        private LightState lightState;
        public static Request createArduinoLEDOnOffDtoRequest(RoomType roomType, LightState lightState){
            return Request.builder()
                    .roomType(roomType)
                    .lightState(lightState)
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
        public static ArduinoLEDOnOffDto.Response createArduinoLEDOnOffDtoResponse(@NonNull String result, @NonNull String message){
            return ArduinoLEDOnOffDto.Response.builder()
                    .result(result)
                    .message(message)
                    .build();

        }
    }
}
