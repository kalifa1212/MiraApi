package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Services.File.FileService;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
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
    private FileService fileService;

    @Value("${project.poster}")
    String path;

    public SaveUserPhoto(UtilisateurService utilisateurService,FileService fileService){
        this.utilisateurService=utilisateurService;
        this.fileService=fileService;
    }
    @Override
    public Object savePhoto(Integer id, MultipartFile multipartFile) throws IOException {

        UtilisateurDto utilisateur = utilisateurService.findById(id);
        if(utilisateur.equals(null)){
            throw new EntityNotFoundException("Aucun utilisateur pour l'id entrer");
        }
        //utilisateur.setImagedata(multipartFile.getBytes());

        String FileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension=FileName.substring(FileName.lastIndexOf(".")+1);
        String uploadDir=path+"utilisateur/";
        FileName=utilisateur.getId()+"."+extension;
        utilisateur.setImageUrl(uploadDir+FileName);
        utilisateur.setImagedata(null);
        FileUpload.saveFile(uploadDir,FileName,multipartFile);
        //log.error("user saving 1  {} ",utilisateur);
        //MosqueDto retour = MosqueDto.fromEntity( mosqueRepository.save(MosqueDto.toEntity(mosque)));

        boolean update=true;
        return utilisateurService.save(utilisateur,update);
    }

    @Override
    public Object downloadPhoto(Integer id) {
        UtilisateurDto utilisateurDto = utilisateurService.findById(id);
        return utilisateurDto;
    }
}
