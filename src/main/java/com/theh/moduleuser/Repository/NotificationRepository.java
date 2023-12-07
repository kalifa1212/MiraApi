package com.theh.moduleuser.Repository;


import com.theh.moduleuser.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findByUtilisateurId(Integer id);
    List<Notification> findByType(String type);
}
