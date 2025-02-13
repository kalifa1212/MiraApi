package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Controller.Api.NotificationApi;
import com.theh.moduleuser.Dto.NotificationDto;
import com.theh.moduleuser.Repository.NotificationRepository;
import com.theh.moduleuser.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NotificationDto> save(NotificationDto dto) {
        return ResponseEntity.ok(notificationService.save(dto));
    }

    @Override
    public Page<NotificationDto> findAllNotification(String sortColumn, int page, int taille, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
        return notificationService.findAll(paging);
    }

    @Override
    public List<NotificationDto> findById(Integer id) {
        return notificationService.findById(id);
    }

    @Override
    public Page<NotificationDto> findByType(String type,String sortColumn, int page, int taille, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
        return notificationService.findByType(type,paging);
    }
}
