package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Controller.Api.NotificationApi;
import com.theh.moduleuser.Dto.NotificationDto;
import com.theh.moduleuser.Repository.NotificationRepository;
import com.theh.moduleuser.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController implements NotificationApi {

    private NotificationService notificationService;
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationController(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    @Override
    public List<NotificationDto> findAllNotification() {
        return notificationService.findAll();
    }

    @Override
    public List<NotificationDto> findByUtilisateur(Integer id) {
        return notificationService.findByUtilisateur(id);
    }

    @Override
    public List<NotificationDto> findByType(String type) {
        return notificationService.findByType(type);
    }
}
