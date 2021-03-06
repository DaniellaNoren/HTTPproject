package plugin.storage.getStatisticsPlugin;

import java.io.File;
import java.sql.*;
import java.util.*;

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

    /**
     * Create the tables in the database and insert default values/first time data if it's the first
     * time the database is called.
     */
    private SQLiteStatistics(){
        boolean exists = new File("PluginStatistics.db").exists();

        String sql= "CREATE TABLE IF NOT EXISTS statistics(" +
                "TimeOfDay TEXT PRIMARY KEY," +
                "Counter INTEGER);";

        try (Connection conn = connect2();
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if(!exists)
            insertFirstTimeData();
    }

    /**
     * 24 values will exist from the beginning (representing every hour of the day) and the plugin will update/increment
     * the existing values rather than inserting new ones. "timeOfDay=01" represents 1AM, "13" represents 1PM and so on.
     */
    private void insertFirstTimeData(){
        String timeOfDay;

        for(int i = 0; i < 24; i++){

            timeOfDay = i < 10 ? "0" + i : "" + i;

            String sql = "INSERT INTO statistics(TimeOfDay, Counter) VALUES (?,?)";

            try (Connection conn = connect2();
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
    private static Connection connect2() {
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

        try (Connection conn = connect2();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, timeOfDay);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List getAllData()  {
        String sql = "Select * FROM statistics";

        try (Connection conn = connect2();
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){



            List<StatisticsObject> list = new ArrayList<>();
            while (rs.next()) {

                //converting the amount of requests to a percentage. read the javascript file to understand the "+0.07" part
                double requestsInPercentage = (((double)rs.getInt("Counter"))/((double)totalRequests()))+0.07;
                String rounded = String.format("%.2f", requestsInPercentage); //two decimals
                String resultReadyForJs = rounded.replace(".", "");
                resultReadyForJs = resultReadyForJs.replace(",", "");

                list.add(new StatisticsObject(rs.getString("TimeOfDay"), resultReadyForJs));
            }
            return list;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private int totalRequests(){
        String sql = "Select * FROM statistics";

        try (Connection conn = connect2();
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            int totalRequests = 0;
            while (rs.next()) {
                totalRequests += rs.getInt("Counter");
            }
            return totalRequests;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }






    /**
     * Same as getAllData() but this prints the result to the console just so it's easier to test while coding.
     * Only time this method is used is for testing.
     */
    public void printDataToConsole()  {
        String sql = "Select * FROM statistics";

        try (Connection conn = connect2();
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getString("TimeOfDay") + " : " + rs.getInt("Counter"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
