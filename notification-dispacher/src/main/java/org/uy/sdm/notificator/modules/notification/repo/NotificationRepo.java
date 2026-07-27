package org.uy.sdm.notificator.modules.notification.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.uy.sdm.notificator.modules.notification.model.Notification;
import org.uy.sdm.notificator.modules.notification.model.Status;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {

	@Transactional
	@Modifying
	@Query("UPDATE Notification nf SET nf.status = :status WHERE nf.id = :notificationId")
	void updateStatus(long notificationId, Status status);
}

