package com.theh.moduleuser.Services.Strategy.Documents;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        String uploadDir="Documents/Photo/Imam/"+id+"/";
        String FileName=""+id+"."+extension;
        if(utilisateur.equals(null)){
            throw new EntityNotFoundException("Aucun imam pour l'id entrer");
        }

        utilisateur.setImageUrl(uploadDir+FileName);

        FileUpload.saveFile(uploadDir, FileName,multipartFile);
        boolean update=true;
        return utilisateurService.save(utilisateur,update);
    }
}
