package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.MosqueService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("mosqueStrategy")
public class SaveMosquePhoto implements StrategyPhoto{

    private MosqueService mosqueService;
    public SaveMosquePhoto(MosqueService mosqueService){
        this.mosqueService=mosqueService;
    }
    @Override
    public Object savePhoto(Integer id, MultipartFile multipartFile) throws IOException {
        String Nom= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension=Nom.substring(Nom.lastIndexOf(".")+1);

        MosqueDto mosque = mosqueService.findById(id);
        String uploadDir="Documents/Photo/Imam/"+id+"/";
        String FileName=""+id+"."+extension;
        if(mosque.equals(null)){
            throw new EntityNotFoundException("Aucun imam pour l'id entrer");
        }
        //MosqueDto mosque2 = MosqueDto.fromEntity(mosque.get());
        mosque.setPhoto(uploadDir+FileName);

        FileUpload.saveFile(uploadDir, FileName,multipartFile);
        Boolean update=true;
        return mosqueService.save(mosque,update);
    }
}
