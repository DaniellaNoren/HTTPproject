package storage;

import java.sql.*;

public class SQLDatabase {
    private static String path = "jdbc:sqlite:sqlite.db";

    /* used this to create the database
    public static void main(String[] args) {
        SQLDatabase db = new SQLDatabase();
    }*/

    /**
     * SQLDatabase creates SQL table called Messages with ID and Post columns.
     */
    public SQLDatabase(){
        try {
            Connection sqlConnection = DriverManager.getConnection(path);

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
     * @return should return post.
     */
    //Todo: send post String into List<messages>
    public Post selectAllPost()  {
        Post post = new Post(0, null);
  
        try{
        Connection sqlConnection = DriverManager.getConnection(path);
        
        String select_message = "Select * FROM Messages";

        Statement stmt = sqlConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = stmt.executeQuery(select_message);

        if (resultSet.next()) {
            post = new Post(resultSet.getInt("ID"),resultSet.getString("Post"));
        }
        resultSet.close();
        stmt.close();
        sqlConnection.close();

        }catch (SQLException e){
        e.printStackTrace();
        }
        return post;
    }







}
