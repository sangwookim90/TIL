package com.helpeachother.secretcode.extra.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResult {
    private OpenApiResultResponse response;
}
