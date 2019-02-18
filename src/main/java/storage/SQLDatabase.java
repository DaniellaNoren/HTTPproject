package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabase {
    String path = "jdbc:sqlite:server.db";

    /**
     * SQLDatabase creates SQL table.
     */
    public SQLDatabase(){
        try {
            Connection sqlConnection = DriverManager.getConnection(path);

            //Creates a SQL table called Messages with ID and Post columns
            String create_table = "CREATE TABLE IF NOT EXISTS Messages(" +
                    "ID integer PRIMARY KEY," +
                    "Post TEXT);";

            Statement stmt = sqlConnection.createStatement();
            stmt.execute(create_table);
            stmt.close();
            sqlConnection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void addPost(){
        try {
            Connection sqlConnection = DriverManager.getConnection(path);

            //Insert
            String insert_message = "INSERT INTO Messages(Post);";

            Statement stmt = sqlConnection.createStatement();
            stmt.execute(insert_message);
            stmt.close();
            sqlConnection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }





}
