package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto save(NotificationDto dto);
    List<NotificationDto> findAll();
    List<NotificationDto> findByUtilisateur(Integer id);
    List<NotificationDto> findByType(String type);
    void delete(Integer id);
}
