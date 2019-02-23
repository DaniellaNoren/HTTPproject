package plugin.messageCounter;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.PluginAnnotation;
import plugin.interfaces.PageService;
import storage.SQLDatabase;

import java.sql.*;

/**
 * This plugin counts the amount of comments in the Messages table and returns the result.
 */
@PluginAnnotation ("/counter")
public class MessageCounter implements PageService {

    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {

        if (httpRequest.getQuery().equals("") || !httpRequest.getQuery().contains("=")) {
            String noQueryResponse = "There are currently " +counter()+" comments!";
            byte[] body = noQueryResponse.getBytes();

            return new HTTPResponse().setStatus(200).setMessage("OK").setContentType("text/html").setContentLength(body.length).setBody(body);

        }
        else
            System.out.println("Sorry this plugin is not operational, please contact the IDEA.inc support team.");

        return null;
    }

    /**
     * counter() asks the database how many rows there are in the table Messages.
     * @return returns the integer count which is the amount of rows in the table.
     */
    public int counter() {

        String sql = "SELECT COUNT (*) FROM Messages;";
        int count = -1;

       try {
            Connection conn = SQLDatabase.connect();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                count = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;

    }
}
