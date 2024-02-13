package com.theh.moduleuser.Services.File;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Services.DocumentsService;
import com.theh.moduleuser.Services.PredicationService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {

    public static void saveFile(String Dir, String FileName, MultipartFile multipartFile) throws IOException{
        Path uploadDir = Paths.get(Dir);
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        try (InputStream inputStream=multipartFile.getInputStream()){
            Path filePath = uploadDir.resolve(FileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe) {
            throw new IOException("Impossible d'enregistrer l'image:"+FileName,ioe);
        }
    }

    public static Resource Download() {
        String url ="D://HAMIDOU/Projets/Projet_Personnel/API/test/Documents/photo/19/";
        Path path = Paths.get(url).toAbsolutePath().resolve("img.jpg");

        Resource resource = null;
        try {
            resource = (Resource) new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resource;

    }
    public static InputStream getRessourceAsStream(String path,PredicationDto predicationDto)throws FileNotFoundException {
        String filepath=path+"predication/"+predicationDto.getType()+"/"+predicationDto.getFichier();
        return  new FileInputStream(filepath);
    }
}
