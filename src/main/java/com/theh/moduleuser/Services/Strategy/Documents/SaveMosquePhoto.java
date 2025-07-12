package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.MosqueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("mosqueStrategy")
public class SaveMosquePhoto implements StrategyPhoto{

    private MosqueService mosqueService;
    private MosqueRepository mosqueRepository;

    @Value("${project.poster}")
    String path;
    public SaveMosquePhoto(MosqueService mosqueService,MosqueRepository mosqueRepository){
        this.mosqueService=mosqueService;
        this.mosqueRepository=mosqueRepository;
    }
    @Override
    public Object savePhoto(Integer id, MultipartFile multipartFile) throws IOException {

        MosqueDto mosque = mosqueService.findById(id);
        String FileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension=FileName.substring(FileName.lastIndexOf(".")+1);
        String uploadDir=path+"predication/"+"mosque/";
        FileName=mosque.getId()+"."+extension;
        mosque.setPhoto(uploadDir+FileName);
        mosque.setImagedata(null);
        FileUpload.saveFile(uploadDir,FileName,multipartFile);
        MosqueDto retour = MosqueDto.fromEntity( mosqueRepository.save(MosqueDto.toEntity(mosque)));
        return retour;
    }

    @Override
    public Object downloadPhoto(Integer id) {
        MosqueDto mosqueDto= mosqueService.findById(id);
        return mosqueDto;
    }
}
