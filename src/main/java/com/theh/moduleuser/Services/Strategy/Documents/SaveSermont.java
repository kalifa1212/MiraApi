package com.theh.moduleuser.Services.Strategy.Documents;//package com.dev.muslim.Services.Strategy.Documents;
//
//import com.dev.muslim.Exception.EntityNotFoundException;
//import com.dev.muslim.Services.File.FileUpload;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service("sermontStrategy")
//public class SaveSermont implements StrategyPhoto<SermontDto> {
//
//    private SermontService sermontService;
//    public SaveSermont(SermontService sermontService){
//        this.sermontService=sermontService;
//    }
//    @Override
//    public SermontDto savePhoto(Integer id, MultipartFile multipartFile) throws IOException {
//        String Nom= StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        String extension=Nom.substring(Nom.lastIndexOf(".")+1);
//
//        SermontDto sermontDto = sermontService.findById(id);
//        String uploadDir="Documents/File/sermont/"+id+"/";
//        String FileName=""+id+"."+extension;
//        if(sermontDto.equals(null)){
//            throw new EntityNotFoundException("Aucun sermont pour l'id entrer");
//        }
//        //MosqueDto mosque2 = MosqueDto.fromEntity(mosque.get());
//        sermontDto.setFichier(uploadDir+FileName);
//
//        FileUpload.saveFile(uploadDir, FileName,multipartFile);
//        return sermontService.save(sermontDto);
//    }
//}
