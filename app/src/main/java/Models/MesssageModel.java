package Models;

public class MesssageModel
{
    String messageId;
    String message;
    long time;
    String senderId;

    public MesssageModel(String messageId, String message, long time) {
        this.messageId = messageId;
        this.senderId=messageId;
        this.message = message;
        this.time = time;
    }

    public MesssageModel(String senderId, String message) {
        this.messageId = senderId;
        this.senderId = senderId;
        this.message = message;
    }

    public MesssageModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
