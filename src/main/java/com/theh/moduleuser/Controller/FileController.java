package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Controller.Api.FileApi;
import com.theh.moduleuser.Services.Strategy.Documents.StrategyPhotoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController implements FileApi {
    private StrategyPhotoContext strategyPhotoContext;

    @Autowired
    public FileController(StrategyPhotoContext strategyPhotoContext){
        this.strategyPhotoContext=strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile multipartFile) throws IOException {
        return strategyPhotoContext.savePhoto(context,id,multipartFile);
    }

    @Override
    public Object uploadDataBase(String context, MultipartFile multipartFile) throws IOException {
        return null;
    }

}
