package chat;

public class DataMessageCard {
    private String senderId;
    private String receiverId;
    private String datePosted;
    private String text;
    private String id;

    public DataMessageCard(String senderId, String receiverId/*, String id*/, String datePosted, String text) {
        //this.id = id;
        this.datePosted = datePosted;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.text = text;
    }
    public DataMessageCard(String senderId, String receiverId, String id, String datePosted, String text) {
        this.id = id;
        this.datePosted = datePosted;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

//    public String getId() {
//        return id;
//    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getText() {
        return text;
    }
}
