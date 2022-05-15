package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final String START_CMD = "/start";

    private final String GREETINGS_TEXT = "Hello, dear friend!";

    private final String INVALID_NOTIFICATION_OR_CMD = "Invalid command or notification, please, start again";

    private final TelegramBot telegramBot;
    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void notifyScheduledTask() {
        notificationService.notifyAllScheduledTasks(this::sendMessage);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            if (message.text().startsWith(START_CMD)) {
                logger.info(START_CMD + " command has been received");
                sendMessage(extractChatId(message), GREETINGS_TEXT);
            } else {
                notificationService.parse(message.text()).ifPresentOrElse(
                        task -> scheduleNotification(extractChatId(message), task),
                        () -> sendMessage(extractChatId(message), INVALID_NOTIFICATION_OR_CMD)
                );
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    private void scheduleNotification(Long chatId, NotificationTask task) {
        notificationService.schedule(task, chatId);
    }

    private void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }

    private void sendMessage(NotificationTask task) {
        sendMessage(task.getChatId(), task.getNotificationMessage());
    }

    private long extractChatId(Message message) {
        return message.chat().id();
    }

}
