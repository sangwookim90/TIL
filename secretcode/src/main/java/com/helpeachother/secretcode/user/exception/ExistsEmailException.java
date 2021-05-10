package com.helpeachother.secretcode.user.exception;

public class ExistsEmailException extends RuntimeException {

    public ExistsEmailException() {
        super("이미 가입된 이메일이 존재합니다.");
    }
}
