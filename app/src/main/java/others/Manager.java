package others;

import database_connection.AuthenticationRequests;
import database_connection.DbConnection;

public class Manager {

    public final static DbConnection dbConnection = new DbConnection();
    public final static AuthenticationRequests authenticationRequests = new AuthenticationRequests();
}
