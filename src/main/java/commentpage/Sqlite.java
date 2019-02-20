package commentpage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Temporary database code just for testing stuff :) remove later...
 */
public class Sqlite {

    //Main method just to test stuff with the database or to create it. Don't remove.
    /*
    public static void main(String[] args) {
        Sqlite sqlite = new Sqlite();
        sqlite.createDatabase();
        sqlite.insertOne("hhhhhheeeeeeej funkar databasen?");
        sqlite.selectAll();
    }
    */


    public void createDatabase(){
        String url = "jdbc:sqlite:sqlite.db";

        String sql = "CREATE TABLE IF NOT EXISTS messages(" +
                "Id integer PRIMARY KEY," +
                "Post TEXT);";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static Connection connect() {
        String url = "jdbc:sqlite:sqlite.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void insertOne(String post){
        String sql = "INSERT INTO messages(Post) VALUES(?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, post);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> selectAll(){
        String sql = "SELECT * FROM messages";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            List<String> messages = new ArrayList<>();
            while (rs.next()) {
                messages.add(rs.getString("Post"));
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }






}
