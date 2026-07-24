package org.uy.sdm.notificator.modules.notification.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uy.sdm.notificator.modules.notification.model.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {

}

