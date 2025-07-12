package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.RessourceMetadata;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Model.Predication;
import com.theh.moduleuser.Services.File.FileService;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.PredicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service("precheStrategy")
public class SavePreche implements StrategyPhoto<PredicationDto> {

    private PredicationService predicationService;
    @Value("${project.poster}")
    String FilePath;
    @Autowired
    private FileService fileService;

    public SavePreche(PredicationService predicationService){
        this.predicationService=predicationService;
    }
    @Override
    public PredicationDto savePhoto(Integer id, MultipartFile multipartFile) throws IOException {
        PredicationDto predi=predicationService.findById(id);
        Predication predication=PredicationDto.toEntity(predi);
        String ressourceUrl = saveVideoFile(multipartFile,id,predication.getType().toString().toLowerCase());
        Path filePath = new File(ressourceUrl).toPath();
        predication.setMimeType(getMimeType(filePath));
        predication.setRessourceUrl(ressourceUrl);
        if(predication.getIdImam()==null||predication.getIdMosque()==null){
            predication.setIdMosque(0);
            predication.setIdImam(0);
        }
        log.info("all done saving {}",predication.toString());
        return predicationService.save(PredicationDto.fromEntity(predication));
    }
    private String saveVideoFile(MultipartFile file,Integer id,String context) {
        // Code pour sauvegarder la vidéo (local, cloud, etc.)

        return fileService.saveFile(file,id.toString(),context);
    }
    public String getMimeType(Path filePath) throws IOException {
        return Files.probeContentType(filePath); // ex: "video/mp4"
    }
    public RessourceMetadata extractMetadata(MultipartFile file) {
        // Utilise JCodec ou FFmpeg pour lire la vidéo
        // Retourne durée, résolution, MIME type, taille
        RessourceMetadata metadata =new RessourceMetadata();
        return metadata;
    }
    @Override
    public PredicationDto downloadPhoto(Integer id) {

        return predicationService.findById(id);
    }
}
