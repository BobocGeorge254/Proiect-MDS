package chat;

public class DataUserCard {
    private final String id;
    private final String email;
    private final String username;
    private final String password ;

    private OnUserCardOpenButtonClickListener OnUserCardOpenButtonClickListener;

    public DataUserCard(String id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password ;
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

    public String getPassword() {
        return password ;
    }


    public void setOnUserCardOpenButtonClickListener(OnUserCardOpenButtonClickListener listener) {
        OnUserCardOpenButtonClickListener = listener;
    }
}
