package com.example.smarthome.utils;

import com.example.smarthome.dto.ParameterDto;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JsonUtilsTest {
    @Autowired
    JsonUtils jsonUtils;

    @Test
    @Description("JSONParser 테스트")
    public void jsonParserTest() throws IOException, ParseException {
        //given
        String json = getLoadJson();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        jsonObject = (JSONObject) jsonObject.get("action");
        jsonObject = (JSONObject) jsonObject.get("parameters");

        //then
        assertThat(jsonObject.toJSONString()).isNotBlank();
    }

    private String getLoadJson() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("spker-request-sample.json");
         return IOUtils.toString(classPathResource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Test
    @Description("JsonUtils getParameter 테스트")
    public void givenParameterName_whenGetParameter_thenObject() throws IOException {
        //given
        String json = getLoadJson();

        //when
        ParameterDto parameterDto = jsonUtils.getParameter(json, "ROOMTYPE");

        //then
        assertThat(parameterDto.getType()).isEqualTo("ROOMTYPE");
        assertThat(parameterDto.getValue()).isEqualTo("거실등");
    }

}