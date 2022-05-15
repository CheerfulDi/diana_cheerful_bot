package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;

import java.util.Optional;
import java.util.function.Consumer;

public interface NotificationService {

    NotificationTask schedule(NotificationTask task, Long chatId);

    Optional<NotificationTask> parse(String notificationBotMessage);

    void notifyAllScheduledTasks(Consumer<NotificationTask> notifier);


}
