//package com.semillero.ecosistema.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SecretKeyConfig {
//
//    private final String secretKey;
//
//    public SecretKeyConfig(@Value("${SECRET_KEY}") String secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    public String getSecretKey() {
//        System.out.println(secretKey); // Verificar que el valor esté inyectado
//        return secretKey;
//    }
//}
