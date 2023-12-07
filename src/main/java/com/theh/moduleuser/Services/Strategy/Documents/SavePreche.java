package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.PredicationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("precheStrategy")
public class SavePreche implements StrategyPhoto<PredicationDto> {

    private PredicationService predicationService;

    public SavePreche(PredicationService predicationService){
        this.predicationService=predicationService;
    }
    @Override
    public PredicationDto savePhoto(Integer id, MultipartFile multipartFile) throws IOException {
        String Nom= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension=Nom.substring(Nom.lastIndexOf(".")+1);

        PredicationDto predicationDto = predicationService.findById(id);
        String uploadDir="Documents/File/predication/"+id+"/";
        String FileName=""+id+"."+extension;
        if(predicationDto.equals(null)){
            throw new EntityNotFoundException("Aucune predication pour l'id entrer");
        }
        //MosqueDto mosque2 = MosqueDto.fromEntity(mosque.get());
        predicationDto.setFichier(uploadDir+FileName);

        FileUpload.saveFile(uploadDir, FileName,multipartFile);
        return predicationService.save(predicationDto);
    }
}
