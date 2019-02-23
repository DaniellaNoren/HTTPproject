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
    public static Connection connect() {
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

        String insert_message = "INSERT INTO messages(Post) VALUES(?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insert_message)) {
            pstmt.setString(1, post);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects all posts from the Messages table.
     * @return should return messages.
     */
    public static List selectAllPost()  {
        String select_message = "Select * FROM Messages";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(select_message)){

            List<String> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(resultSet.getString("Post"));
            }
            return messages;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }







}
