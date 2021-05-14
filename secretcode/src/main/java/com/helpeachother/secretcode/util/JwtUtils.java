package com.helpeachother.secretcode.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.helpeachother.secretcode.user.exception.PasswordNotMatchException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtils {

    public static final String KEY = "passwordkey";

    public static String getIssuer(String token) {
        String issuer = JWT.require(Algorithm.HMAC512(KEY.getBytes()))
                .build()
                .verify(token)
                .getIssuer();

        return issuer;
    }
}
