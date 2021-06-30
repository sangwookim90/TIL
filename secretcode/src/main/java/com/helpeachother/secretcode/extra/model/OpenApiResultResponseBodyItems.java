package com.helpeachother.secretcode.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResultResponseBodyItems {
    List<OpenApiResultResponseBodyItem> item;
}
