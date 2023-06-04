package chat;

public class DataMessageCard {
    private String senderUsername;
    private String receiverUsername;
    private String senderId;
    private String receiverId;
    private String datePosted;
    private String text;
    private String id;

    public DataMessageCard(String senderUsername, String receiverUsername, String senderId, String receiverId, String datePosted, String text) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.datePosted = datePosted;
        this.text = text;
    }
    public DataMessageCard(String senderUsername, String receiverUsername, String senderId, String receiverId, String id, String datePosted, String text) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.datePosted = datePosted;
        this.text = text;
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
