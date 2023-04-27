package chat;

public class DataMessageCard {
    private String senderId;
    private String receiverId;
    private String id;
    private String datePosted;
    private String text;

    public DataMessageCard(String senderId, String receiverId, String id, String datePosted, String text) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.id = id;
        this.datePosted = datePosted;
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getId() {
        return id;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getText() {
        return text;
    }
}
