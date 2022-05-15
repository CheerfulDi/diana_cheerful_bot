package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    public enum NotificationStatus {
        SCHEDULED,

        SENT,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long chatId;

    private String notificationMessage;

    private LocalDateTime notificationDate;

    private LocalDateTime sentDate;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.SCHEDULED;

    public NotificationTask() {
    }

    public NotificationTask(String notificationMessage, LocalDateTime notificationDate) {
        this.notificationMessage = notificationMessage;
        this.notificationDate = notificationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentDate = LocalDateTime.now();
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public long getChatId() {
        return chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationTask)) return false;
        NotificationTask that = (NotificationTask) o;
        return getId() == that.getId() && getChatId() == that.getChatId() && getNotificationMessage().equals(that.getNotificationMessage()) && getNotificationDate().equals(that.getNotificationDate()) && getSentDate().equals(that.getSentDate()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getNotificationMessage(), getNotificationDate(), getSentDate(), getStatus());
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationDate=" + notificationDate +
                ", sentDate=" + sentDate +
                ", status=" + status +
                '}';
    }
}
