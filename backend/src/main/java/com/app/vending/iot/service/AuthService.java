package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.UserRole;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.request.LoginRequest;
import com.app.vending.iot.dto.request.TokenRequest;
import com.app.vending.iot.dto.request.UserSignUpRequest;
import com.app.vending.iot.dto.response.LoginResponse;
import com.app.vending.iot.dto.response.UserResponse;
import com.app.vending.iot.entity.User;
import com.app.vending.iot.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthService {

    @NonFinal
    @Value("${key.jwt.value}")
    String KEY;

    @NonFinal
    @Value("${app.time.expiryTime}")
    int expiryTime;

    UserRepository userRepository;

    public Boolean instropect(TokenRequest request) throws ParseException, JOSEException {
        SignedJWT jwt = SignedJWT.parse(request.getToken());
        var expiryTime = jwt.getJWTClaimsSet().getExpirationTime();
        JWSVerifier jwsVerifier = new MACVerifier(KEY.getBytes());

        boolean isVerify = jwt.verify(jwsVerifier);
        boolean isTime = expiryTime.after(Date.from(Instant.now()));

        if(!isVerify || !isTime )
            throw new AppException(ErrorCode.AUTHENTICATION);

        return true;
    }

    public String generate(User user) throws JOSEException {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .issuer("VENDING")
                .subject(user.getFullName())
                .issueTime(Date.from(Instant.now()))
                .expirationTime(Date.from(Instant.now().plus(expiryTime,ChronoUnit.SECONDS)))
                .claim("scope", user.getRole())
                .build();

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        jwsObject.sign(new MACSigner(KEY.getBytes()));

        return jwsObject.serialize();
    }
}
