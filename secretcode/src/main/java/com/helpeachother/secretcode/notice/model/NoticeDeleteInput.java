package com.helpeachother.secretcode.notice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class NoticeDeleteInput {
    private List<Long> idList;
}
