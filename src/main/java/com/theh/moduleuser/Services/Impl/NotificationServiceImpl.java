package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.NotificationDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.NotificationRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return NotificationDto.fromEntity(notificationRepository.save(NotificationDto.toEntity(dto)));
    }


    @Override
    public Page<NotificationDto> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(NotificationDto::fromEntity);
    }

    @Override
    public List<NotificationDto> findById(Integer id) {
        return notificationRepository.findById(id).stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<NotificationDto> findByType(String type,Pageable pageable) {
        return notificationRepository.findByType(type,pageable).map(NotificationDto::fromEntity);
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
