package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service("userStrategy")
public class SaveUserPhoto implements StrategyPhoto{

    private UtilisateurService utilisateurService;

    public SaveUserPhoto(UtilisateurService utilisateurService){
        this.utilisateurService=utilisateurService;
    }
    @Override
    public Object savePhoto(Integer id, MultipartFile multipartFile) throws IOException {

        String Nom= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension=Nom.substring(Nom.lastIndexOf(".")+1);
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String uploadDir="Documents/Photo/User/"+id+"/";
        String FileName=""+id+"."+extension;

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(uploadDir)
                .path(FileName)
                .toUriString();

        if(utilisateur.equals(null)){
            throw new EntityNotFoundException("Aucun utilisateur pour l'id entrer");
        }
        //
        FileUpload.saveFile(uploadDir, FileName,multipartFile);
        Path path = Paths.get(uploadDir+FileName);
        Resource resource = null;
        try {
            resource = (Resource) new UrlResource(path.toUri());
            utilisateur.setImageUrl(resource.getURL().toString());
            log.error("display {}",resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //
        //utilisateur.setImageUrl(uploadDir+FileName);

        utilisateur.setImagedata(multipartFile.getBytes());
        //FileUpload.saveFile(uploadDir, FileName,multipartFile);
        boolean update=true;
        return utilisateurService.save(utilisateur,update);
    }

    @Override
    public Object downloadPhoto(Integer id) {
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        Path path = Paths.get(utilisateur.getImageUrl());
        Resource resource = null;
        try {
            resource = (Resource) new UrlResource(path.toUri());
            log.error("displau {}",resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }
}
