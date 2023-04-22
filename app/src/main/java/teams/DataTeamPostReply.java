package teams;

public class DataTeamPostReply {

    private final String id;
    private final String senderName;
    private final String text;
    private final String sendDate;
    private final String senderId;
    private final String teamPostId;

    public DataTeamPostReply(String id, String senderName, String text, String sendDate, String senderId, String teamPostId) {
        this.id = id;
        this.senderName = senderName;
        this.text = text;
        this.sendDate = sendDate;
        this.senderId = senderId;
        this.teamPostId = teamPostId;
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

    public String getTeamPostId() {
        return teamPostId;
    }
}
