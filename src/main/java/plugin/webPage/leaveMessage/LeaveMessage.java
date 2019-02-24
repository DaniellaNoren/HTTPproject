package plugin.webPage.leaveMessage;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import parsing.SqlToJsonFile;
import plugin.webPage.WebPagePath;
import plugin.webPage.WebPagePlugin;
import storage.SQLDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@WebPagePath("/comment")
public class LeaveMessage implements WebPagePlugin {

    /**
     * Adds the comment from message page to the sql db and write the data to a json file
     */
    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {
        String httpBody = new String(httpRequest.getBody());
        String keyValue = null;
        try {
            keyValue = URLDecoder.decode(httpBody.substring(httpBody.indexOf("=") + 1), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SQLDatabase.addPost(keyValue);
        new SqlToJsonFile().writeJsonToFile(SQLDatabase.selectAllPost());
        return new HTTPResponse().setStatus(200).setMessage("OK");
    }
}
