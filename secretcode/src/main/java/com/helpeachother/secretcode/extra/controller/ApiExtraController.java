package com.helpeachother.secretcode.extra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpeachother.secretcode.common.model.ResponseResult;
import com.helpeachother.secretcode.extra.model.OpenApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class ApiExtraController {

    /**
     * RestTemplate을 사용하여 공공데이터포털의 공공API를 연동하여 약국목록 가져오는 API
     */
    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> pharmacy() {
        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=%s";
        String apiKey = "iGS4ZjM37Q7ZF276W6MOVsmxQ6cW1k2j5hIKaxNGFrLz6Jk9NL7bwPdkzskhkcA%2F2oG394OFeWlYX4nnkyOZuQ%3D%3D&";

        String apiResult = "";
        try {
            URI uri = new URI(String.format(url, apiKey));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            apiResult = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);


    }
}
