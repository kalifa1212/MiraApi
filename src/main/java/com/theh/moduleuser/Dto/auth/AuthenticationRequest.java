package com.theh.moduleuser.Dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class AuthenticationRequest {
    private String login;
    private String password;
}
