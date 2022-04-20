package com.example.smarthome.dto.speker;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;


public class SpeakerServerDto {
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @Builder
    public static class Request{
        @NotBlank(message = "version 는 공백 일 수 없습니다.")
        private String version;

        @Valid
        @NotNull(message = "action은 필수입니다.")
        private ActionDto action;

        private Object context;

        public static Request createSpeakerServerRequest(ActionDto action, Object context){
            return Request.builder()
                    .version("2.0")
                    .action(action)
                    .context(context)
                    .build();

        }
    }
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @Builder
   public static class Response{
       String version;
       String resultCode;
       Map<String, String> output;

       //== 생성 메서드 ==//
       public static Response createSpeakerResponse(Map<String, String> output){
           return Response.builder()
                   .version("2.0")
                   .resultCode("OK")
                   .output(output)
                   .build();
       }
   }

}
