package com.example.smarthome.utils;

import com.example.smarthome.dto.ParameterDto;
import com.example.smarthome.error.code.ArduinoServerErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class JsonUtils {
    private final JSONParser jsonParser;
    private final ObjectMapper mapper;
    public ParameterDto getParameter(String json, String name){
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            jsonObject = (JSONObject) jsonObject.get("action");
            jsonObject = (JSONObject) jsonObject.get("parameters");
            jsonObject = (JSONObject) jsonObject.get(name);

            String content = jsonObject.toJSONString();
            return mapper.readValue(content, ParameterDto.class);
        } catch (ParseException e) {
            log.error("Json 파싱 에러");
            log.error(e.getMessage(), e);
            throw new RuntimeException("Json 파싱에 실패 하였습니다. ");
        }catch (JsonProcessingException e){
            log.error("Json 매핑 에러");
            log.error(e.getMessage(), e);
            throw new RuntimeException("Json 매핑에 실패 하였습니다.");
        }
    }
    public <T> T fromJson(String json, Class<T> responseType)  {
        try {
            return mapper.readValue(json, responseType);
        } catch (JsonProcessingException e) {
            log.error("fail JsonUtils fromJson Error - transfer type : {}", responseType.getName());
            throw new ArduinoServerException(ArduinoServerErrorCode.ARDUINO_SERVER_RESPONSE_JSON_PARSING_ERROR);
        }
    }

}
