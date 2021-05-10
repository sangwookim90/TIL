package com.helpeachother.secretcode.user.exception;

public class PasswordNotMathException extends RuntimeException {

    public PasswordNotMathException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
