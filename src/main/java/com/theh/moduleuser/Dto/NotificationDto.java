package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class NotificationDto {

    private Integer id;

    private String message;

    private String type;

    private String destinateur;

    private UtilisateurDto utilisateurDto;

    public static NotificationDto fromEntity(Notification notification) {
        if (notification==null) {
            return null;
            //  throw an exception
        }

        return NotificationDto.builder()
                .id(notification.getId())
                .utilisateurDto(UtilisateurDto.fromEntity(notification.getUtilisateur()))
                .message(notification.getMessage())
                .type(notification.getType())
                .destinateur(notification.getDestinateur())
                .build();
    }

    public static Notification toEntity(NotificationDto notificationDto) {
        if (notificationDto==null) {
            return null;
            //  throw an exception
        }

        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setUtilisateur(UtilisateurDto.toEntity(notificationDto.getUtilisateurDto()));
        notification.setMessage(notificationDto.getMessage());
        notification.setDestinateur(notificationDto.getDestinateur());
        notification.setType(notificationDto.getType());
        return notification;
    }

}
