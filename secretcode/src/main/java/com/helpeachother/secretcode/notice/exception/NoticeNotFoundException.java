package com.helpeachother.secretcode.notice.exception;

public class NoticeNotFoundException extends RuntimeException {

    public NoticeNotFoundException(){
        super("공지사항의 글이 존재하지 않습니다.");
    }

    public NoticeNotFoundException(String message){
        super(message);
    }
}
