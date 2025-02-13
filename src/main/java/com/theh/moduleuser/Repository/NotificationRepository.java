package com.theh.moduleuser.Repository;


import com.theh.moduleuser.Model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
   // List<Notification> findByUtilisateurId(Integer id);
    Page<Notification> findByType(String type, Pageable pageable);
}
