package com.example.smarthome.dto.arduino;

import com.example.smarthome.model.LightState;
import com.example.smarthome.model.RoomType;
import com.example.smarthome.model.TempType;
import lombok.*;
import org.springframework.lang.NonNull;

public class ArduinoBringTempTypeDataDto {
    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        private TempType tempType;
        public static ArduinoBringTempTypeDataDto.Request createArduinoBringTempTypeDataDtoRequest(TempType tempType){
            return Request.builder()
                    .tempType(tempType)
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
        private Integer data;
        public static ArduinoBringTempTypeDataDto.Response createArduinoBringTempTypeDataDtoResponse(@NonNull String result, @NonNull String message,  @NonNull Integer data){
            return Response.builder()
                    .result(result)
                    .message(message)
                    .data(data)
                    .build();

        }
    }
}
