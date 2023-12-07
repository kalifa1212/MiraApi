package com.theh.moduleuser.Services.Strategy.Documents;//package com.dev.muslim.Services.Strategy.Documents;
//
//import com.dev.muslim.Exception.EntityNotFoundException;
//import com.dev.muslim.Services.File.FileUpload;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@Service("imamStrategy")
//public class SaveImamPhoto implements StrategyPhoto<ImamDto>{
//    private ImamService imamService;
//
//    public SaveImamPhoto(ImamService imamService){
//        this.imamService=imamService;
//    }
//
//    @Override
//        public ImamDto savePhoto(Integer id, MultipartFile multipartFile) throws IOException {
//
//            String Nom= StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            String extension=Nom.substring(Nom.lastIndexOf(".")+1);
//            Optional<Imam> imam = imamService.findById(id);
//            String uploadDir="Documents/Photo/Imam/"+id+"/";
//            String FileName=""+id+"."+extension;
//            if(imam.isEmpty()){
//                throw new EntityNotFoundException("Aucun imam pour l'id entrer");
//            }
//            ImamDto imam2 = ImamDto.fromEntity(imam.get());
//            imam2.setPhoto(uploadDir+FileName);
//
//            FileUpload.saveFile(uploadDir, FileName,multipartFile);
//            return imamService.save(imam2);
//        }
//}
