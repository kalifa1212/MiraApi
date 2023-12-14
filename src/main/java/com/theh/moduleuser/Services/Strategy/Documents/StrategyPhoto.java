package com.theh.moduleuser.Services.Strategy.Documents;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StrategyPhoto <T>{
    //TODO interface save photo pour user et preche aussi
    T savePhoto(Integer id, MultipartFile multipartFile) throws IOException;
    T downloadPhoto(Integer id);
    //TODO implementation a venir : methode upload data to corresponding context
}
