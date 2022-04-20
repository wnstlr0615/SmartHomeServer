package com.example.smarthome.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateUtils {
    private final RestTemplateBuilder restTemplateBuilder;

    @Value(value = "${arduino.host}")
    private String arduinoServerHost;

    @Value(value = "${arduino.port}")
    private String arduinoPort;

    @PostConstruct
    public void init(){
        log.info("host : {}, port : {}", arduinoServerHost, arduinoPort);
        log.info("targetURI : {} ", getTargetUri());
    }

    RestTemplate getRestTemplate(){
        String targetUri = getTargetUri();

        return restTemplateBuilder
                .rootUri(targetUri)
                .build();
    }

    private String getTargetUri() {
        return UriComponentsBuilder.fromUriString(arduinoServerHost)
                .port(arduinoPort)
                .toUriString();
    }

    public ResponseEntity<String> sendPostRequest(String path, Object body){
        RestTemplate restTemplate = getRestTemplate();
        try {
            return restTemplate.postForEntity(path, body, String.class);
        } catch (RestClientException e) {
            log.error("RestTemplate Error");
            throw new RuntimeException(e);
        }

    }
}
