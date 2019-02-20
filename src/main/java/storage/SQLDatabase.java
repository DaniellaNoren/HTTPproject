package storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase {
    private static String path = "jdbc:sqlite:sqlite.db";
    
    /**
     * SQLDatabase creates SQL table called Messages with Id and Post columns.
     */
    public SQLDatabase(){
        String create_table = "CREATE TABLE IF NOT EXISTS messages(" +
                "Id integer PRIMARY KEY," +
                "Post TEXT);";

        try (Connection conn = DriverManager.getConnection(path);
             Statement stmt = conn.createStatement()) {
            stmt.execute(create_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connects to database.
     * @return conn, which is a connection to database.
     */
    private static Connection connect() {
        //String path = "jdbc:sqlite:sqlite.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Inserts Post into Messages table if Post is not null.
     * @param post is currently a String
     */
    public static void addPost(String post){
        try {
            Connection sqlConnection = DriverManager.getConnection(path);

            String insert_message = "INSERT INTO Messages(Post TEXT NOT NULL);";

            Statement stmt = sqlConnection.createStatement();
            stmt.execute(insert_message);
            stmt.close();
            sqlConnection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Selects all posts from the Messages table.
     * @return should return messages.
     */
    //Todo: send post String into List<messages>
    public static List selectAllPost()  {
        Post post = new Post(0, null);

        try{
        Connection sqlConnection = DriverManager.getConnection(path);
        
        String select_message = "Select * FROM Messages";

        Statement stmt = sqlConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = stmt.executeQuery(select_message);

            List<String> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(resultSet.getString("Post"));
            }
            resultSet.close();
            stmt.close();
            sqlConnection.close();
            return messages;

        }catch (SQLException e){
        e.printStackTrace();
        }
        return null;

    }







}
