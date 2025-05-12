package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Controller.Api.FileApi;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Context;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Predication;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Services.PredicationService;
import com.theh.moduleuser.Services.Strategy.Documents.SaveMosquePhoto;
import com.theh.moduleuser.Services.Strategy.Documents.SavePreche;
import com.theh.moduleuser.Services.Strategy.Documents.SaveUserPhoto;
import com.theh.moduleuser.Services.Strategy.Documents.StrategyPhotoContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j
public class FileController implements FileApi {
    private StrategyPhotoContext strategyPhotoContext;
    private PredicationService predicationService;

    @Autowired
    public FileController(StrategyPhotoContext strategyPhotoContext,
                          PredicationService predicationService){
        this.strategyPhotoContext=strategyPhotoContext;
        this.predicationService=predicationService;
    }

    @Override
    public Object savePhoto(Context context, Integer id, MultipartFile multipartFile) throws IOException {
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
                File ressourceFile = new File(predicationDto.getRessourceUrl());

                return ResponseEntity.ok()
                        //.header(HttpHeaders.CONTENT_TYPE,predicationDto.getMimeType())
                        .header(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=\" profile \"")
                        .body(resourceP);
            default: throw new InvalidEntityException("Context inconnue pour l'enregistrement de la photo", ErrorCodes.UNKNOW_CONTEXT);
        }
    }

    @Override
    public ResponseEntity<Resource> streamRessource(Integer predicationId, Context context, String rangeHeader) throws IOException {
        PredicationDto predicationDto=predicationService.findById(predicationId);

        //        Video video = videoService.findVideo(videoId);
        File ressourceFile = new File(predicationDto.getRessourceUrl());
        long fileSize = ressourceFile.length();
        String mimeType = predicationDto.getMimeType();
//
//        // 2. Valeurs par défaut
        long fileLength = ressourceFile.length();
        long start = 0;
        long end = fileLength - 1;
//
//        // 3. Parse du header Range (si présent)
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            try {
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1 && !ranges[1].isEmpty()) {
                    end = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
            }
        }

//        // 4. Ajustement si end dépasse la taille du fichier
        end = Math.min(end, fileLength - 1);
        long contentLength = end - start + 1;

        // 5. Lire uniquement le segment demandé
        InputStream inputStream = new BufferedInputStream(new FileInputStream(ressourceFile));
        inputStream.skip(start);
        InputStream limitedStream = new LimitedInputStream(inputStream, contentLength);

//        // 6. Headers HTTP pour le streaming
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, mimeType);
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
        headers.set(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", start, end, fileLength));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline"); // Pour éviter le téléchargement

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(new InputStreamResource(limitedStream));
        //return null;
    }
    public static class LimitedInputStream extends FilterInputStream {
        private long remaining;

        public LimitedInputStream(InputStream in, long limit) {
            super(in);
            this.remaining = limit;
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
