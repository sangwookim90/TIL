package com.helpeachother.secretcode.common.model;

import org.springframework.http.ResponseEntity;

public class ResponseResult {


    public static ResponseEntity<?> success() {
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<?> fail(String message) {
        return ResponseEntity.badRequest().body(message);
    }

}
