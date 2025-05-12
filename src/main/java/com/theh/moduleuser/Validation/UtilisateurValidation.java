package com.theh.moduleuser.Validation;

import com.theh.moduleuser.Dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UtilisateurValidation {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    public static List<String> validate(UtilisateurDto utilisateurDto){


        List<String> errors = new ArrayList<>();

        //Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        if(utilisateurDto==null) {
            errors.add("Veuillez renseigner les informations");
        }

        if(StringUtils.hasLength(utilisateurDto.getEmail())) {
            utilisateurDto.setEmail(utilisateurDto.getEmail().toLowerCase());
        }
        if(utilisateurDto.getEmail()==null){
            errors.add("Veuillez renseigner un Email correct Sous la forme Exemple@gmail.com");
        }
        else if(!PATTERN.matcher(utilisateurDto.getEmail()).matches())
        {
            errors.add("Veuillez renseigner un Email correct Sous la forme Exemple@gmail.com");
        }

        if(!StringUtils.hasLength(utilisateurDto.getNom())) {
            errors.add("Veuillez renseigner le nom");
        }
        if(!StringUtils.hasLength(utilisateurDto.getMotDePasse())) {
            errors.add("Veuillez renseigner le mot de passe");
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())) {
            errors.add("Veuillez renseigner l'email");
        }
        if(utilisateurDto.getDateDeNaissance()==null) {
            errors.add("Veuillez renseigner la date de naissance");
        }


        return errors;
    }
}
