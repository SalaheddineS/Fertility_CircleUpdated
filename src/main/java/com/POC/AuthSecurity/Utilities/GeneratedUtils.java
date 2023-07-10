package com.POC.AuthSecurity.Utilities;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GeneratedUtils {

    public String generateRandomUid(){
        return UUID.randomUUID().toString();
    }
}
