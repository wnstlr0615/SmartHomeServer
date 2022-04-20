package com.example.smarthome.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class SpeakerResponse {
    String version;
    String resultCode;
    Map<String, String> output;
    
    //== 생성 메서드 ==//
    public static SpeakerResponse createSpeakerResponse(Map<String, String> output){
        return SpeakerResponse.builder()
                .version("2.0")
                .resultCode("OK")
                .output(output)
                .build();
    }

}
