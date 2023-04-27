package teams;

public class DataTeamChanelNameCard {

    private String id;
    private String name;
    private String teamId;

    public DataTeamChanelNameCard(String id, String name, String teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeamId() {
        return teamId;
    }
}
