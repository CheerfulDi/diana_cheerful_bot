package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.Collection;


public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {

    @Query("FROM NotificationTask WHERE notificationDate <= CURRENT_TIMESTAMP AND status = 'SCHEDULED'")
    Collection<NotificationTask> getScheduleNotifications();

}
