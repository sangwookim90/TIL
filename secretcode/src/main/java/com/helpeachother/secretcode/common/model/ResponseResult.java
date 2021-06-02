package com.helpeachother.secretcode.common.model;

import com.helpeachother.secretcode.board.entity.BoardBadReport;
import com.helpeachother.secretcode.user.model.ResponseMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseResult {

    public static ResponseEntity<?> success() {
        return success(null);
    }

    public static ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }

    public static ResponseEntity<?> fail(String message) {

        return ResponseEntity.badRequest().body(ResponseMessage.fail(message));
    }

    public static ResponseEntity<?> result(ServiceResult result) {
        if(result.isFail()) {
            return fail(result.getMessage());
        }
        return success();
    }


}
