package teams;

public class DataTeamFile {

    private final String id;
    private final String uri;
    private final String name;
    private final String teamId;

    public DataTeamFile(String id, String uri, String name, String teamId) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.teamId = teamId;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getTeamId() {
        return teamId;
    }
}
