package com.helpeachother.secretcode.notice.exception;

public class AlreadyDeletedException extends RuntimeException {

    public AlreadyDeletedException(){
        super("이미 삭제된 글입니다.");
    }

    public AlreadyDeletedException(String message){
        super(message);
    }
}
