package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Controller.Api.FileApi;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Services.Strategy.Documents.SaveMosquePhoto;
import com.theh.moduleuser.Services.Strategy.Documents.SavePreche;
import com.theh.moduleuser.Services.Strategy.Documents.SaveUserPhoto;
import com.theh.moduleuser.Services.Strategy.Documents.StrategyPhotoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j
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

    @Override
    public ResponseEntity getFile(Integer id,String context) {

        switch (context){
            case "user":
                UtilisateurDto utilisateur= (UtilisateurDto) strategyPhotoContext.displayPhoto(id,context);
                //log.error("test {}",utilisateur);
                Resource resource=uploadingFileU(utilisateur);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\" profile \"")
                        .body(resource);
            case "mosque":
                MosqueDto mosque=(MosqueDto) strategyPhotoContext.displayPhoto(id,context);
                Resource resourceM=uploadingFile(mosque);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\" profile \"")
                        .body(resourceM);
                case "preche":
                PredicationDto predicationDto=(PredicationDto) strategyPhotoContext.displayPhoto(id,context);
                Resource resourceP=uploadingGeneralObjetFule(predicationDto);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=\" profile \"")
                        .body(resourceP);
            default: throw new InvalidEntityException("Context inconnue pour l'enregistrement de la photo", ErrorCodes.UNKNOW_CONTEXT);
        }
    }
    public Resource uploadingFile(MosqueDto mosqueDto){
        Path path = Paths.get(mosqueDto.getPhoto());
        Resource resource = null;
        try {
            resource = (Resource) new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  resource;
    } public Resource uploadingGeneralObjetFule(PredicationDto object){
        Path path = Paths.get(object.getFichier());
        Resource resource = null;
        try {
            resource = (Resource) new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  resource;
    }
    public Resource uploadingFileU(UtilisateurDto utilisateurDto){
        //log.error("test 2 {}",utilisateurDto);
        Path path = Paths.get(utilisateurDto.getImageUrl());
        Resource resource = null;
        try {
            resource = (Resource) new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  resource;
    }

}
