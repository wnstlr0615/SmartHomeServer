package com.example.smarthome.dto.arduino;

import com.example.smarthome.model.TvState;
import lombok.*;
import org.springframework.lang.NonNull;

public class ArduinoTvChannelDto {
    @AllArgsConstructor
    @NoArgsConstructor( access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        private Integer channel;
        public static ArduinoTvChannelDto.Request createArduinoTvChannelDtoRequest(@NonNull Integer channel){
            return Request.builder()
                    .channel(channel)
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
        public static ArduinoTvChannelDto.Response createArduinoTvChannelDtoResponse(@NonNull String result, @NonNull String message){
            return Response.builder()
                    .result(result)
                    .message(message)
                    .build();

        }
    }
}
