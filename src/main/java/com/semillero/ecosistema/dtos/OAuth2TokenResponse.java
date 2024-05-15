package com.semillero.ecosistema.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OAuth2TokenResponse {

    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
    private String id_token;
}
