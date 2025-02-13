package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Notification;
import com.theh.moduleuser.Model.TypeNotification;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class NotificationDto {

    private Integer id;

    private String message;
    private TypeNotification type;
    private boolean isRead;
    private LocalDateTime dateTime;

    public static NotificationDto fromEntity(Notification notification) {
        if (notification==null) {
            return null;
            //  throw an exception
        }

        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .type(notification.getType())
                .isRead(notification.isRead())
                .dateTime(notification.getDateTime())
                .type(notification.getType())
                .build();
    }

    public static Notification toEntity(NotificationDto notificationDto) {
        if (notificationDto==null) {
            return null;
            //  throw an exception
        }

        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setMessage(notificationDto.getMessage());
        notification.setType(notificationDto.getType());
        notification.setRead(notificationDto.isRead());
        notification.setDateTime(notificationDto.getDateTime());
        notification.setType(notificationDto.getType());
        return notification;
    }

}
