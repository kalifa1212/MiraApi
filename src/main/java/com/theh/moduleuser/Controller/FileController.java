package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Controller.Api.FileApi;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Services.Strategy.Documents.SaveMosquePhoto;
import com.theh.moduleuser.Services.Strategy.Documents.SavePreche;
import com.theh.moduleuser.Services.Strategy.Documents.SaveUserPhoto;
import com.theh.moduleuser.Services.Strategy.Documents.StrategyPhotoContext;
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
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + utilisateur.getPrenom() + "\"")
                        .body(utilisateur.getImagedata());
            case "mosque":
                Mosque mosque=(Mosque) strategyPhotoContext.displayPhoto(id,context);
                break;
            case "sermont":
                break;
            default: throw new InvalidEntityException("Context inconnue pour l'enregistrement de la photo", ErrorCodes.UNKNOW_CONTEXT);
        }
        return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
    }

}
