package com.helpeachother.secretcode.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdate {
    @Size(max = 20, message = "연락처는 최대 20자까지 입력해야 합니다.")
    private String phone;
}
