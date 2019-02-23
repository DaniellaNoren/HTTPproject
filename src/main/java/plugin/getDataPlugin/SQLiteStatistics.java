package plugin.getDataPlugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteStatistics {

    private static SQLiteStatistics instance;

    /**
     * Singleton, retrieve or create instance.
     */
    public static SQLiteStatistics getInstance() {
        if(instance == null){
            instance = new SQLiteStatistics();
        }
        return instance;
    }

    private SQLiteStatistics(){
        String sql= "CREATE TABLE IF NOT EXISTS statistics(" +
                "TimeOfDay TEXT PRIMARY KEY," +
                "Counter INTEGER);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //it can't currently create firsttimedata twice due to the primary key contraint but it still
        // would be better to wrap this line around some "if database don't exist" if case or something later
        insertFirstTimeData();
    }

    /**
     * 24 values will exist from the beginning (representing every hour of the day) and the plugin will update/increment
     * the existing values rather than inserting new ones. "timeOfDay=1" represents 1AM, "13" represents 1PM and so on.
     */
    private void insertFirstTimeData(){
        String timeOfDay;

        for(int i = 0; i < 24; i++){

            timeOfDay = "" + i;
            String sql = "INSERT INTO statistics(TimeOfDay, Counter) VALUES (?,?)";

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, timeOfDay);
                pstmt.setInt(2, 0);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    /**
     * Get the connection to the database
     */
    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:PluginStatistics.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public void updateCounter(String timeOfDay){

        String sql = "UPDATE statistics SET Counter = Counter + 1 WHERE TimeOfDay = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, timeOfDay);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getAllData()  {
        String sql = "Select * FROM statistics";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            List<String> statistics = new ArrayList<>();
            while (rs.next()) {
                System.out.println(rs.getString("TimeOfDay") + " : " + rs.getInt("Counter"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
