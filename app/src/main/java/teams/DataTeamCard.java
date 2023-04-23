package teams;

public class DataTeamCard {

    private final String id;
    private final String name;
    private final String description;
    private final String photoUri;

    public DataTeamCard(String id, String name, String description, String photoUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUri = photoUri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUri() {
        return photoUri;
    }
}
