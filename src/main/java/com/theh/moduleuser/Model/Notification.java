package com.theh.moduleuser.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="Notification")
@EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor
public class Notification extends AbstractEntity{

    private static final long serialVersionUID = 1L;

    @Column(name="message")
    private String message;

    @Column(name="type")
    private TypeNotification type;

    @Column(name="isRead")
    private boolean isRead;

    @Column(name="dateTime")
    private LocalDateTime dateTime;

}
