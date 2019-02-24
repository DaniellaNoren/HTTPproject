package plugin.webPage.messageCounter;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.webPage.WebPagePath;
import plugin.webPage.WebPagePlugin;
import storage.SQLDatabase;

import java.sql.*;

/**
 * This plugin counts the amount of comments in the Messages table and returns the result.
 */
@WebPagePath("/counter")
public class MessageCounter implements WebPagePlugin {

    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {

            String htmlDoc = htmlDoc() ;
            byte[] body = htmlDoc.getBytes();

            return new HTTPResponse().setStatus(200).setMessage("OK").setContentType("text/html").setContentLength(body.length).setBody(body);

    }

    /**
     * counter() asks the database how many rows there are in the table Messages.
     * @return returns the integer count which is the amount of rows in the table.
     */
    private int counter() {

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

    /**
     * Gives Message Counter page same page design as the non-plugin pages.
     * @return returns html code and counter().
     */
    private String htmlDoc(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" dir=\"ltr\">\n" +
                "<head>\n" +
                "    <!-- Metadata -->\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <!-- Tab title -->\n" +
                "    <title>Message Counter</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<header id=\"main-header\">\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Message Counter</h1>\n" +
                "    </div>\n" +
                "</header>\n" +
                "<!-- Navigation Bar -->\n" +
                "<nav id=\"navbar\">\n" +
                "    <div class=\"container\">\n" +
                "        <ul>\n" +
                "            <li><a href=\"index.html\">Home</a></li>\n" +
                "            <li><a href=\"animals.html\">Animals</a></li>\n" +
                "            <li><a href=\"contact.html\">Contact</a></li>\n" +
                "            <li><a href=\"URL.html\">URL</a></li>\n" +
                "            <li><a href=\"PDF.html\">PDF</a></li>\n" +
                "            <li><a href=\"message.html\">Message</a></li>\n" +
                "            <li><a href=\"plugin.html\">Plugins</a></li>\n" +
                "        </ul>\n" +
                "    </div>\n" +
                "</nav>\n" +
                "\n" +
                "<!-- Showcase Section-->\n" +
                "<section id=\"showcase\">\n" +
                "    <div class=\"container\">\n" +
                "        <h1>“This plugin shows how many comments have been submitted on the Messages page.”</h1>\n" +
                "    </div>\n" +
                "</section>\n" +
                "<div class=\"container\">\n" +
                "    <!-- Main text section-->\n" +
                "    <section id=\"main\">\n" +
                "        <h1>Message Counter</h1>\n" +
                "        <p> There are currently " +counter()+" comments in Messages!</p>\n" +
                "    </section>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<!-- Footer -->\n" +
                "<footer id=\"main-footer\">\n" +
                "    <p>Copyright &copy; 2019 IDEA.inc</p>\n" +
                "</footer>\n" +
                "</body>\n" +
                "</html>";

    }
}
