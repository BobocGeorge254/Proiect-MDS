package teams;

public class DataTeamPost {

    private final String id;
    private final String senderName;
    private final String text;
    private final String sendDate;
    private final String senderId;

    public DataTeamPost(String id, String senderName, String text, String sendDate, String senderId) {
        this.id = id;
        this.senderName = senderName;
        this.text = text;
        this.sendDate = sendDate;
        this.senderId = senderId;
    }

    public String getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getText() {
        return text;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getSenderId() {
        return senderId;
    }
}
