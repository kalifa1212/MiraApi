package com.theh.moduleuser.Dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class ChangePassWordDto {

    private String nouveauPassword;
    private String password;
    private Integer userId;
}
