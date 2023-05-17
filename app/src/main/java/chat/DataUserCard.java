package chat;

public class DataUserCard {
    private final String id;
    private final String email;
    private final String username;

    private OnUserCardOpenButtonClickListener OnUserCardOpenButtonClickListener;

    public DataUserCard(String id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setOnUserCardOpenButtonClickListener(OnUserCardOpenButtonClickListener listener) {
        OnUserCardOpenButtonClickListener = listener;
    }
}
