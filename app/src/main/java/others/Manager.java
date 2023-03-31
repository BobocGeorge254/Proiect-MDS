package others;

import database_connection.DbConnection;

public class Manager {

    private final static DbConnection dbConnection = new DbConnection();

    public static DbConnection getDbConnection() {
        return dbConnection;
    }
}
