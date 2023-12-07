package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.NotificationDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.NotificationRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.notificationRepository=notificationRepository;
        this.utilisateurRepository=utilisateurRepository;
    }

    @Override
    public NotificationDto save(NotificationDto dto) {
        List<String> errors = validate(dto);
        if(!errors.isEmpty()) {
            //log.error("La mosque est non valide {}",dto);
            throw new InvalidEntityException("Les information entrer ne sont pas valide ", ErrorCodes.NOTIFICATION_NOT_VALID,errors);
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(dto.getUtilisateurDto().getId());

        if(utilisateur.isEmpty()){
            throw new InvalidEntityException("Utilisateur non trouver", ErrorCodes.UTILISATEUR_NOT_FOUND,errors);
        }
        return NotificationDto.fromEntity(notificationRepository.save(NotificationDto.toEntity(dto)));
    }


    @Override
    public List<NotificationDto> findAll() {
        return notificationRepository.findAll().stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> findByUtilisateur(Integer id) {
        return notificationRepository.findByUtilisateurId(id).stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> findByType(String type) {
        return notificationRepository.findByType(type).stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

    }

    public static List<String> validate(NotificationDto notificationDto){
        List<String> errors = new ArrayList<>();
        if(notificationDto==null ) {
            errors.add("veullez renseigner les données");
        }
        return errors;
    }
}
