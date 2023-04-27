package teams;

public class DataTeamPostReply {

    private final String id;
    private final String senderName;
    private final String text;
    private final String sendDate;
    private final String senderId;
    private final String teamPostId;
    private final String teamId;

    public DataTeamPostReply(String id, String senderName, String text, String sendDate, String senderId, String teamPostId, String teamId) {
        this.id = id;
        this.senderName = senderName;
        this.text = text;
        this.sendDate = sendDate;
        this.senderId = senderId;
        this.teamPostId = teamPostId;
        this.teamId = teamId;
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

    public String getTeamId() {
        return teamId;
    }
}
